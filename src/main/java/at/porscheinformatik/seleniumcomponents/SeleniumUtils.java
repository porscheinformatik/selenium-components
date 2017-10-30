package at.porscheinformatik.seleniumcomponents;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Some utilities for testing.
 *
 * @author ham
 */
public final class SeleniumUtils
{

    private static final AtomicInteger TEST_THREAD_ID = new AtomicInteger(1);
    private static final ExecutorService TEST_THREAD_POOL = Executors.newCachedThreadPool(runnable -> {
        Thread thread = new Thread(runnable,
            SeleniumUtils.toClassName(SeleniumUtils.class) + " Pool Thread #" + TEST_THREAD_ID.getAndIncrement());

        thread.setDaemon(true);

        return thread;
    });

    private SeleniumUtils()
    {
        super();
    }

    /**
     * Null-safe empty and blank check for strings.
     *
     * @param s the string
     * @return true if empty
     */
    public static boolean isEmpty(String s)
    {
        return s == null || s.trim().length() == 0;
    }

    /**
     * Null-safe empty function for arrays.
     *
     * @param <Any> the type of array items
     * @param array the array
     * @return true if null or empty
     */
    public static <Any> boolean isEmpty(Any[] array)
    {
        return array == null || Array.getLength(array) <= 0;
    }

    /**
     * Null-safe empty function for collections.
     *
     * @param collection the collection
     * @return true if null or empty
     */
    public static boolean isEmpty(Collection<?> collection)
    {
        return collection == null || collection.isEmpty();
    }

    /**
     * Null-safe empty function for maps.
     *
     * @param map the maps
     * @return true if null or empty
     */
    public static boolean isEmpty(Map<?, ?> map)
    {
        return map == null || map.isEmpty();
    }

    /**
     * Calls the {@link Callable}. If the call returns null, the optional is empty. If the call throws a
     * {@link SeleniumTimeoutException} or a {@link SeleniumInterruptedException}, the optional is empty. If the call
     * throws another exception this exception will be wrappen in a {@link SeleniumException}.
     *
     * @param <Any> any type
     * @param callable the {@link Callable}
     * @return the optional result
     * @throws SeleniumException on occasion
     */
    public static <Any> Optional<Any> optional(Callable<Any> callable) throws SeleniumException
    {
        try
        {
            return Optional.ofNullable(callable.call());
        }
        catch (SeleniumTimeoutException | SeleniumInterruptedException e)
        {
            return Optional.empty();
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new SeleniumException(String.format("Call failed: %s", describeCallLine()), e);
        }
    }

    /**
     * Waits some seconds. The seconds will be scaled by the {@value #timeMultiplier}.
     *
     * @param seconds the seconds to wait
     */
    public static void waitForSeconds(double seconds)
    {
        try
        {
            Thread.sleep((long) (scaleTime(seconds) * 1000));
        }
        catch (InterruptedException e)
        {
            throw new SeleniumException("WaitForSeconds got interrupted", e);
        }
    }

    /**
     * Waits until the check function does not throw an exception and returns true. If the timeout is &lt;= 0 or NaN, it
     * checks it at least once. The timeout will be scaled by the {@value #timeMultiplier}. The check function will be
     * called four times a second. If the check throws an exception at the end the exception will be wrapped by a
     * {@link SeleniumException} and gets thrown this way.
     *
     * @param timeoutInSeconds the timeout in seconds
     * @param check the check
     * @throws SeleniumException if the {@link Callable} fails horribly
     * @throws SeleniumInterruptedException on process interruption
     * @throws SeleniumTimeoutException on timeout
     */
    public static void waitUntil(double timeoutInSeconds, Supplier<Boolean> check) throws SeleniumException
    {
        keepTrying(timeoutInSeconds, () -> check.get() ? true : null);
    }

    /**
     * Keeps calling the {@link Callable} until it does not throw an exception, returns an {@link Optional} with a value
     * or another non-null value. If the timeout &lt;= 0 or NaN, it calls the {@link Callable} at least once and the
     * timeout will be ignored. The timeout will be scaled by the {@value #timeMultiplier}. If the call throws an
     * exception at the end the exception will be wrapped by a {@link SeleniumException} and gets thrown this way. The
     * {@link Callable} will be called four times a second.
     *
     * @param <Any> the expected return type
     * @param timeoutInSeconds the timeout (will be scaled by the {@value #timeMultiplier})
     * @param callable the {@link Callable}
     * @return the result
     * @throws SeleniumException if the {@link Callable} fails horribly
     * @throws SeleniumInterruptedException on process interruption
     * @throws SeleniumTimeoutException on timeout
     */
    public static <Any> Any keepTrying(double timeoutInSeconds, Callable<Any> callable) throws SeleniumException
    {
        return keepTrying(timeoutInSeconds, callable, 0.25);
    }

    /**
     * Keeps calling the {@link Callable} until it does not throw an exception, returns an {@link Optional} with a value
     * or another non-null value. If the timeout &lt;= 0 or NaN, it calls the {@link Callable} at least once and the
     * timeout will be ignored. The timeout will be scaled by the {@value #timeMultiplier}. If the call throws an
     * exception at the end the exception will be wrapped by a {@link SeleniumException} and gets thrown this way.
     *
     * @param <Any> the expected return type
     * @param timeoutInSeconds the timeout (will be scaled by the {@value #timeMultiplier})
     * @param callable the {@link Callable}
     * @param delayInSeconds the delay between calls (will be scaled by the {@value #timeMultiplier})
     * @return the result or the call
     * @throws SeleniumFailException if the call fails to produce a value in time
     */
    public static <Any> Any keepTrying(double timeoutInSeconds, Callable<Any> callable, double delayInSeconds)
        throws SeleniumFailException
    {
        double scaledTimeoutInSeconds = scaleTime(timeoutInSeconds);

        if (Double.isNaN(scaledTimeoutInSeconds) || (long) (scaledTimeoutInSeconds * 1000) <= 0)
        {
            Any result;

            try
            {
                result = callWithTimeout(timeoutInSeconds, callable);
            }
            catch (Exception e)
            {
                throw new SeleniumFailException(String.format("Keep trying failed: %s", describeCallLine()), e);
            }

            if (result instanceof Optional)
            {
                if (((Optional<?>) result).isPresent())
                {
                    return result;
                }
            }
            else if (result != null)
            {
                return result;
            }

            throw new SeleniumFailException(String.format("Keep trying failed: %s", describeCallLine()));
        }

        try
        {
            return callWithTimeout(timeoutInSeconds, () -> {
                Any result = null;
                long startMillis = System.currentTimeMillis();
                long endMillis = (long) (startMillis + scaledTimeoutInSeconds * 1000);

                while (true)
                {
                    try
                    {
                        result = callable.call();
                    }
                    catch (Exception e)
                    {
                        if (System.currentTimeMillis() > endMillis)
                        {
                            throw new SeleniumFailException(String.format("Keep trying failed: %s", describeCallLine()),
                                e);
                        }
                    }

                    if (result instanceof Optional)
                    {
                        if (((Optional<?>) result).isPresent())
                        {
                            return result;
                        }
                    }
                    else if (result != null)
                    {
                        return result;
                    }

                    long currentMillis = System.currentTimeMillis();

                    if (currentMillis > endMillis)
                    {
                        throw new SeleniumFailException(String.format("Keep trying timed out (%,.1f seconds): %s",
                            scaledTimeoutInSeconds, describeCallLine()));
                    }

                    waitForSeconds(delayInSeconds);
                }
            });
        }
        catch (SeleniumFailException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new SeleniumFailException(String.format("Keep trying failed: %s", describeCallLine()), e);
        }
    }

    /**
     * Calls the {@link Callable} once. The timeout will be scaled by the {@value #timeMultiplier}. If the timeout is <=
     * 0 or NaN, the timeout will be ignored.
     *
     * @param <Any> the expected return type
     * @param timeoutInSeconds the timeout (will be scaled by the {@value #timeMultiplier})
     * @param callable the {@link Callable}
     * @return the result of the call
     * @throws SeleniumException wrapper for exceptions
     * @throws SeleniumInterruptedException on process interruption
     * @throws SeleniumTimeoutException on timeout
     */
    public static <Any> Any callWithTimeout(double timeoutInSeconds, Callable<Any> callable)
        throws SeleniumException, SeleniumInterruptedException, SeleniumTimeoutException
    {
        double scaledTimeoutInSeconds = scaleTime(timeoutInSeconds);

        Future<Any> future = TEST_THREAD_POOL.submit(callable);

        try
        {
            if (Double.isNaN(scaledTimeoutInSeconds) || (long) (scaledTimeoutInSeconds * 1000) <= 0)
            {
                return future.get();
            }

            return future.get((long) (scaledTimeoutInSeconds * 1000), TimeUnit.MILLISECONDS);
        }
        catch (ExecutionException e)
        {
            Throwable cause = e.getCause() != null ? e.getCause() : e;

            if (cause instanceof SeleniumException)
            {
                throw (SeleniumException) cause;
            }

            throw new SeleniumException(String.format("Call failed: %s", describeCallLine()), cause);
        }
        catch (InterruptedException e)
        {
            throw new SeleniumInterruptedException(String.format("Call interrupted: %s", describeCallLine()), e);
        }
        catch (TimeoutException e)
        {
            throw new SeleniumTimeoutException(
                String.format("Call timed out (%,.1f seconds): %s", scaledTimeoutInSeconds, describeCallLine()), e);
        }
    }

    /**
     * Executes a task in the background.
     *
     * @param <Any> the expected result type
     * @param callable the {@link Callable}
     * @return the future for the {@link Callable}
     */
    public static <Any> Future<Any> meanwhile(Callable<Any> callable)
    {
        return meanwhile(callable, null, null);
    }

    /**
     * Executes a task in the background.
     *
     * @param <Any> the expected result type
     * @param callable the {@link Callable}
     * @param successCallback the callback on success, may be null
     * @return the future for the {@link Callable}
     */
    public static <Any> Future<Any> meanwhile(Callable<Any> callable, Consumer<Any> successCallback)
    {
        return meanwhile(callable, successCallback, null);
    }

    /**
     * Executes a task in the background.
     *
     * @param <Any> the expected result type
     * @param callable the {@link Callable}
     * @param successCallback the callback on success, may be null
     * @param errorCallback the callback on failure, may be null
     * @return the future for the {@link Callable}
     */
    public static <Any> Future<Any> meanwhile(Callable<Any> callable, Consumer<Any> successCallback,
        Consumer<Exception> errorCallback)
    {
        return TEST_THREAD_POOL.submit(() -> {
            Any result = null;

            try
            {
                result = callable.call();
            }
            catch (Exception e)
            {
                if (errorCallback != null)
                {
                    errorCallback.accept(e);
                }

                throw e;
            }

            if (successCallback != null)
            {
                successCallback.accept(result);
            }

            return result;
        });
    }

    /**
     * Calls the {@link Callable} "iterationCount" times. Uses "threadCount" threads for this task. The total timeout is
     * estimated by the iteration count and the thread count. The timeout will be scaled by the
     * {@value #timeMultiplier}.
     *
     * @param <Any> the expected return type
     * @param iterationCount the iterationCount
     * @param threadCount the threadCount
     * @param timeoutPerCallableInSeconds the timeout for one (!) call
     * @param callable the {@link Callable}
     * @return a list of results
     * @throws SeleniumException wrapper for exceptions
     * @throws SeleniumInterruptedException on process interruption
     * @throws SeleniumTimeoutException on timeout
     */
    public static <Any> List<Any> parallel(int iterationCount, int threadCount, double timeoutPerCallableInSeconds,
        Callable<Any> callable) throws SeleniumException, SeleniumInterruptedException, SeleniumTimeoutException
    {
        Semaphore semaphore = new Semaphore(threadCount);
        Callable<Any> worker = () -> {
            try
            {
                return callable.call();
            }
            finally
            {
                semaphore.release();
            }
        };

        // increase the timeout for each callable if multiple callables are executed in parallel
        timeoutPerCallableInSeconds = timeoutPerCallableInSeconds * (1 + Math.log10(threadCount));

        // the total timeout shrinks with the thread count
        double totalTimeoutInSeconds = timeoutPerCallableInSeconds * iterationCount / threadCount;

        return callWithTimeout(totalTimeoutInSeconds, () -> {
            List<Future<Any>> futures = new ArrayList<>();

            for (int i = 0; i < iterationCount; i += 1)
            {
                try
                {
                    // make sure, that not too many callables are executed parallel
                    semaphore.acquire();
                }
                catch (InterruptedException e)
                {
                    throw new SeleniumInterruptedException("Adding callables to thread pool got interrupted");
                }

                futures.add(TEST_THREAD_POOL.submit(worker));
            }

            List<Any> results = new ArrayList<>();

            for (Future<Any> future : futures)
            {
                results.add(future.get());
            }

            return results;
        });
    }

    private static double scaleTime(double timeout)
    {
        return timeout * SeleniumGlobals.getTimeMultiplier();
    }

    /**
     * Returns the name of the class in a short and readable form.
     *
     * @param type the class, may be null
     * @return the name
     */
    public static String toClassName(Class<?> type)
    {
        if (type == null)
        {
            return "?";
        }

        String name = "";

        while (type.isArray())
        {
            name = "[]" + name;
            type = type.getComponentType();
        }

        if (type.isPrimitive())
        {
            name = type.getName() + name;
        }
        else
        {
            name = getShortName(type.getName()) + name;
        }

        return name;
    }

    private static String getShortName(String currentName)
    {
        int beginIndex = currentName.lastIndexOf('.');

        if (beginIndex >= 0)
        {
            currentName = currentName.substring(beginIndex + 1);
        }

        return currentName;
    }

    public static String repeat(String s, int length)
    {
        StringBuilder builder = new StringBuilder();

        while (builder.length() < length)
        {
            builder.append(s);
        }

        return builder.substring(0, length);
    }

    public static String describeCallLine()
    {
        return toCallLine(findCallElement(SeleniumGlobals.getIgnorableCallElements()));
    }

    private static String toCallLine(StackTraceElement element)
    {
        return element != null ? "(" + element.getFileName() + ":" + element.getLineNumber() + ")" : "(?:?)";
    }

    private static StackTraceElement findCallElement(List<Pattern> ignorableCallElements)
    {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        outerLoop: for (StackTraceElement element : stackTrace)
        {
            String methodName = element.getClassName() + "." + element.getMethodName();

            if (methodName.startsWith(SeleniumUtils.class.getName()))
            {
                // skip myself
                continue;
            }

            for (Pattern pattern : ignorableCallElements)
            {
                if (pattern.matcher(methodName).matches())
                {
                    continue outerLoop;
                }
            }

            return element;
        }

        return null;
    }

}
