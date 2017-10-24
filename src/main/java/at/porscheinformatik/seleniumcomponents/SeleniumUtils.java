package at.porscheinformatik.seleniumcomponents;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
 * Some utilities for testing
 *
 * @author ham
 */
public final class SeleniumUtils
{

    private static final Pattern IGNORE_PATTERN = Pattern.compile("^"
        + SeleniumUtils.class.getName().substring(0, SeleniumUtils.class.getName().lastIndexOf(".")).replace(".", "\\.")
        + ".*");

    private static final AtomicInteger TEST_THREAD_ID = new AtomicInteger(1);
    private static final ExecutorService TEST_THREAD_POOL = Executors.newCachedThreadPool(runnable -> {
        Thread thread = new Thread(runnable, "Selenium Utils Pool Thread #" + TEST_THREAD_ID.getAndIncrement());

        thread.setDaemon(true);

        return thread;
    });

    private static double timeoutMultiplier = 1;

    private SeleniumUtils()
    {
        super();
    }

    public static double getTimeoutMultiplier()
    {
        return timeoutMultiplier;
    }

    public static void setTimeoutMultiplier(double timeoutMultiplier)
    {
        SeleniumUtils.timeoutMultiplier = timeoutMultiplier;
    }

    /**
     * Nullsafe empty and blank check for strings
     *
     * @param s the string
     * @return true if empty
     */
    public static boolean isEmpty(String s)
    {
        return s == null || s.trim().length() == 0;
    }

    /**
     * Nullsafe empty function for arrays
     *
     * @param <Any> the type of array items
     * @param array the array
     * @return true if null or empty
     */
    public static <Any> boolean isEmpty(Any[] array)
    {
        return (array == null) || (Array.getLength(array) <= 0);
    }

    /**
     * Nullsafe empty function for collections
     *
     * @param collection the collection
     * @return true if null or empty
     */
    public static boolean isEmpty(Collection<?> collection)
    {
        return (collection == null) || (collection.isEmpty());
    }

    /**
     * Waits some seconds
     *
     * @param seconds seconds
     */
    public static void waitForSeconds(double seconds)
    {
        try
        {
            Thread.sleep((long) (scaleTimeout(seconds) * 1000));
        }
        catch (InterruptedException e)
        {
            throw new SeleniumTestException("WaitForSeconds got interrupted", e);
        }
    }

    /**
     * Waits until the check does not throw an exception and returns true. Does this four times per second. Throws a
     * {@link SeleniumTestException} on timeout or exception. If the timeout is &lt;= 0, it checks it at least once.
     *
     * @param timeoutInSeconds the timeout
     * @param check the check
     * @throws SeleniumTestException wrapper for exceptions
     */
    public static void waitUntil(double timeoutInSeconds, Supplier<Boolean> check) throws SeleniumTestException
    {
        keepTrying(timeoutInSeconds, () -> check.get() ? true : null).orElseThrow(() -> new SeleniumTestException(
            String.format("WaitUntil timed out after %,.1fs: %s", timeoutInSeconds, describeCallLine(IGNORE_PATTERN))));
    }

    /**
     * Keeps calling the callable until it does not throw an exception and returns a non-null value. Does this four
     * times per second. If the timeout &lt;= 0, it calls the callable at least once.
     *
     * @param <Any> the expected return type
     * @param timeoutInSeconds the timeout
     * @param callable the callable
     * @return the optional result
     * @throws SeleniumTestException wrapper for exceptions
     */
    public static <Any> Optional<Any> keepTrying(double timeoutInSeconds, Callable<Any> callable)
        throws SeleniumTestException
    {
        return keepTrying(timeoutInSeconds, callable, 0.25);
    }

    /**
     * Keeps calling the callable until it does not throw an exception and returns a non-null value. If the timeout
     * &lt;= 0, it calls the callable at least once.
     *
     * @param <Any> the expected return type
     * @param timeoutInSeconds the timeout
     * @param callable the callable
     * @param delayInSeconds the delay between calls
     * @return the optional result
     * @throws SeleniumTestException if the callable fails horribly
     */
    public static <Any> Optional<Any> keepTrying(double timeoutInSeconds, Callable<Any> callable, double delayInSeconds)
    {
        if (timeoutInSeconds <= 0 || Double.isNaN(timeoutInSeconds))
        {
            try
            {
                return Optional.ofNullable(callable.call());
            }
            catch (SeleniumTestException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                throw new SeleniumTestException(String.format("Call failed: %s", describeCallLine(IGNORE_PATTERN)), e);
            }
        }

        final double scaledTimeout = scaleTimeout(timeoutInSeconds);

        return callWithTimeout(scaledTimeout, () -> {
            Any result = null;
            long startMillis = System.currentTimeMillis();
            long endMillis = (long) (startMillis + (scaledTimeout * 1000));

            while (true)
            {
                try
                {
                    result = callable.call();
                }
                catch (SeleniumTestException e)
                {
                    if (System.currentTimeMillis() > endMillis)
                    {
                        throw e;
                    }
                }
                catch (Exception e)
                {
                    if (System.currentTimeMillis() > endMillis)
                    {
                        throw new SeleniumTestException(
                            String.format("Call failed: %s", describeCallLine(IGNORE_PATTERN)), e);
                    }
                }

                if (result != null)
                {
                    return result;
                }

                long currentMillis = System.currentTimeMillis();

                if (currentMillis > endMillis)
                {
                    throw new TimeoutException();
                }

                long delay = Math.min((long) (delayInSeconds * 1000), endMillis - currentMillis);

                if (delay > 0)
                {
                    Thread.sleep(delay);
                }
            }
        });
    }

    /**
     * Calls the callable once. Returns an empty optional on timeout.
     *
     * @param <Any> the expected return type
     * @param timeoutInSeconds the timeout
     * @param callable the callable
     * @return the optional result
     * @throws SeleniumTestException wrapper for exceptions
     */
    public static <Any> Optional<Any> callWithTimeout(double timeoutInSeconds, Callable<Any> callable)
        throws SeleniumTestException
    {
        Future<Any> future = TEST_THREAD_POOL.submit(callable);

        try
        {
            return Optional.of(future.get((long) (timeoutInSeconds * 1000), TimeUnit.MILLISECONDS));
        }
        catch (ExecutionException e)
        {
            throw new SeleniumTestException(String.format("Call failed: %s", describeCallLine(IGNORE_PATTERN)), e);
        }
        catch (InterruptedException | TimeoutException e)
        {
            return Optional.empty();
        }
    }

    /**
     * Executes a task in the background.
     *
     * @param <Any> the expected result type
     * @param callable the callable
     * @return the future for the callable
     */
    public static <Any> Future<Any> meanwhile(Callable<Any> callable)
    {
        return meanwhile(callable, null, null);
    }

    /**
     * Executes a task in the background.
     *
     * @param <Any> the expected result type
     * @param callable the callable
     * @param successCallback the callback on success, may be null
     * @return the future for the callable
     */
    public static <Any> Future<Any> meanwhile(Callable<Any> callable, Consumer<Any> successCallback)
    {
        return meanwhile(callable, successCallback, null);
    }

    /**
     * Executes a task in the background.
     *
     * @param <Any> the expected result type
     * @param callable the callable
     * @param successCallback the callback on success, may be null
     * @param errorCallback the callback on failure, may be null
     * @return the future for the callable
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
     * Calls the callable "iterationCount" times. Uses "threadCount" threads for this task. The total timeout is
     * estimated by the iteration count and the thread count.
     *
     * @param <Any> the expected return type
     * @param iterationCount the iterationCount
     * @param threadCount the threadCount
     * @param timeoutPerCallableInSeconds the timeout for one (!) call
     * @param callable the callable
     * @return a list of results
     */
    public static <Any> List<Any> parallel(int iterationCount, int threadCount, double timeoutPerCallableInSeconds,
        Callable<Any> callable)
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
        double totalTimeoutInSeconds = (timeoutPerCallableInSeconds * iterationCount) / threadCount;

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
                    throw new SeleniumTestException("Adding callables to thread pool got interrupted");
                }

                futures.add(TEST_THREAD_POOL.submit(worker));
            }

            List<Any> results = new ArrayList<>();

            for (Future<Any> future : futures)
            {
                results.add(future.get());
            }

            return results;
        }).get();
    }

    private static double scaleTimeout(double timeoutInSeconds)
    {
        return timeoutInSeconds * timeoutMultiplier;
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

    public static String describeCall(Pattern ignorePattern)
    {
        return toCall(findCallElement(ignorePattern));
    }

    public static String describeCallLine(Pattern ignorePattern)
    {
        return toCallLine(findCallElement(ignorePattern));
    }

    public static String toCallLine(StackTraceElement element)
    {
        return (element != null) ? "(" + element.getFileName() + ":" + element.getLineNumber() + ")" : "(?:?)";
    }

    public static String toCallMethod(StackTraceElement element)
    {
        return (element != null) ? element.getClassName() + "." + element.getMethodName() : "<#UNKNOWN>";
    }

    public static String toCall(StackTraceElement element)
    {
        return toCallMethod(element) + " " + toCallLine(element);
    }

    public static StackTraceElement findCallElement(Pattern ignorePattern)
    {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for (StackTraceElement element : stackTrace)
        {
            String methodName = element.getClassName() + "." + element.getMethodName();

            if (methodName.startsWith(SeleniumUtils.class.getName()))
            {
                // skip myself
                continue;
            }

            if (ignorePattern != null && ignorePattern.matcher(methodName).matches())
            {
                continue;
            }

            return element;
        }

        return null;
    }

}
