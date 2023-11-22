package at.porscheinformatik.seleniumcomponents;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.openqa.selenium.StaleElementReferenceException;

/**
 * Some utilities for testing.
 *
 * @author ham
 */
public final class SeleniumUtils
{
    private static final SeleniumLogger LOG = new SeleniumLogger(SeleniumUtils.class);

    private static final AtomicInteger POOL_THREAD_ID = new AtomicInteger(1);
    private static final ExecutorService POOL_THREAD_POOL = Executors.newCachedThreadPool(runnable -> {
        Thread thread = new Thread(runnable,
            Utils.toClassName(SeleniumUtils.class) + " Pool Thread #" + POOL_THREAD_ID.getAndIncrement());

        thread.setDaemon(true);

        return thread;
    });

    private SeleniumUtils()
    {
        super();
    }

    /**
     * Returns the topmost {@link SeleniumComponent} following the chain of parents of the specified component or the
     * component itself, if it has no parent component.
     *
     * @param component the component
     * @return the root component
     */
    public static SeleniumComponent root(SeleniumComponent component)
    {
        while (component.parent() != null)
        {
            component = component.parent();
        }

        return component;
    }

    /**
     * Searches all parents using the specified predicate
     *
     * @param component the component to start at
     * @param predicate the predicate
     * @return the component, null if not found
     */
    public static SeleniumComponent findParent(SeleniumComponent component, Predicate<SeleniumComponent> predicate)
    {
        while (component != null)
        {
            if (predicate.test(component))
            {
                return component;
            }

            component = component.parent();
        }

        return null;
    }

    /**
     * Returns the parent of the specified type or null if not found
     *
     * @param <T> the type
     * @param component the component where to start
     * @param type the type of the parent
     * @return the component
     */
    @SuppressWarnings("unchecked")
    public static <T extends SeleniumComponent> T findParentByType(SeleniumComponent component, Class<T> type)
    {
        return (T) findParent(component, parent -> type.equals(parent.getClass()));
    }

    /**
     * Returns the tag name of the component. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for the
     * component to become available.
     *
     * @param component the component
     * @return the tag name
     */
    public static String getTagName(SeleniumComponent component)
    {
        return retryOnStale(() -> component.element().getTagName());
    }

    /**
     * Returns the attribute with the specified name. Retries if stale.
     *
     * @param component the component
     * @param name the name of the attribute
     * @return the value
     */
    public static String getAttribute(SeleniumComponent component, String name)
    {
        return retryOnStale(() -> component.element().getAttribute(name));
    }

    /**
     * Returns value of the attribute "class". Retries if stale.
     *
     * @param component the component
     * @return the value
     */
    public static String getClassAttribute(SeleniumComponent component)
    {
        return getAttribute(component, "class");
    }

    /**
     * Returns true if the component has the specified class attribute.
     *
     * @param component the component
     * @param className the className
     * @return the true if the class attribute contains the specified string (ignores case)
     */
    public static boolean containsClassName(SeleniumComponent component, String className)
    {
        String attribute = getClassAttribute(component);

        if (attribute == null)
        {
            return false;
        }

        String[] classNames = attribute.split(" ");

        for (String currentClassName : classNames)
        {
            if (Objects.equals(currentClassName, className))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the text of the component. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for the
     * component to become available.
     *
     * @param component the component
     * @return the text
     */
    public static String getText(SeleniumComponent component)
    {
        return retryOnStale(() -> component.element().getText().trim());
    }

    /**
     * Returns true if there is a descendant.
     *
     * @param component the component, that should contain the descendant
     * @param selector the selector
     * @return true if one component was found
     */
    public static boolean containsDescendant(SeleniumComponent component, WebElementSelector selector)
    {
        return !selector.findAll(component.searchContext()).isEmpty();
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
            throw new SeleniumException(LOG.hintAt("Call failed"), e);
        }
    }

    /**
     * Waits some seconds. The seconds will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()}.
     *
     * @param seconds the seconds to wait
     */
    public static void waitForSeconds(double seconds)
    {
        try
        {
            double scaledSeconds = scaleTime(seconds);

            LOG.trace("Waiting for %,.3f seconds ...", scaledSeconds);

            Thread.sleep((long) (scaledSeconds * 1000));
        }
        catch (InterruptedException e)
        {
            throw new SeleniumException("WaitForSeconds got interrupted", e);
        }
    }

    /**
     * Waits until the specified time millis are reached. This wait is not influenced by time scaling!
     *
     * @param timeMillis the time millis
     */
    protected static void waitUntil(long timeMillis)
    {
        long waitForMillis = timeMillis - System.currentTimeMillis();

        if (waitForMillis <= 0)
        {
            return;
        }

        try
        {
            LOG.trace("Waiting for %,.3f seconds ...", waitForMillis / 1000d);

            Thread.sleep(waitForMillis);
        }
        catch (InterruptedException e)
        {
            throw new SeleniumException("WaitUntil got interrupted", e);
        }
    }

    /**
     * Waits until the check function does not throw an exception and returns true. Uses
     * {@link SeleniumGlobals#getShortTimeoutInSeconds()} as default timeout. The timeout will be scaled by the
     * {@link SeleniumGlobals#getTimeMultiplier()}. The check function will be called multiple times during the timeout.
     * If the check throws an exception at the end the exception will be wrapped by a {@link SeleniumException} and gets
     * thrown this way.
     *
     * @param check the check
     * @throws SeleniumException if the {@link Callable} fails horribly
     * @throws SeleniumInterruptedException on process interruption
     * @throws SeleniumTimeoutException on timeout
     */
    public static void waitUntil(Supplier<Boolean> check) throws SeleniumException
    {
        keepTrying(SeleniumGlobals.getShortTimeoutInSeconds(), () -> check.get() ? true : null);
    }

    /**
     * Waits until the check function does not throw an exception and returns true. If the timeout is &lt;= 0 or NaN, it
     * checks it at least once. The timeout will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()}. The check
     * function will be called multiple times during the timeout. If the check throws an exception at the end the
     * exception will be wrapped by a {@link SeleniumException} and gets thrown this way.
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
     * Keeps calling the {@link Runnable} until it does not throw an exception. Uses
     * {@link SeleniumGlobals#getShortTimeoutInSeconds()} as default timeout. The timeout will be scaled by the
     * {@link SeleniumGlobals#getTimeMultiplier()}. If the call throws an exception at the end the exception will be
     * wrapped by a {@link SeleniumException} and gets thrown this way. The {@link Callable} will be called multiple
     * times during the timeout.
     *
     * @param runnable the {@link Runnable}
     * @throws SeleniumException if the {@link Runnable} fails horribly
     * @throws SeleniumInterruptedException on process interruption
     * @throws SeleniumTimeoutException on timeout
     */
    public static void retryOnFail(Runnable runnable) throws SeleniumException
    {
        retryOnFail(SeleniumGlobals.getShortTimeoutInSeconds(), runnable, 0.1);
    }

    /**
     * Keeps calling the {@link Runnable} until it does not throw an exception. If the timeout &lt;= 0 or NaN, it calls
     * the {@link Callable} at least once and the timeout will be ignored. The timeout will be scaled by the
     * {@link SeleniumGlobals#getTimeMultiplier()}. If the call throws an exception at the end the exception will be
     * wrapped by a {@link SeleniumException} and gets thrown this way. The {@link Runnable} will be called multiple
     * times during the timeout.
     *
     * @param timeoutInSeconds the timeout (will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()})
     * @param runnable the {@link Runnable}
     * @throws SeleniumException if the {@link Runnable} fails horribly
     * @throws SeleniumInterruptedException on process interruption
     * @throws SeleniumTimeoutException on timeout
     */
    public static void retryOnFail(double timeoutInSeconds, Runnable runnable) throws SeleniumException
    {
        retryOnFail(timeoutInSeconds, runnable, 0.1);
    }

    /**
     * Keeps calling the {@link Runnable} until it does not throw an exception. If the timeout &lt;= 0 or NaN, it calls
     * the {@link Runnable} at least once and the timeout will be ignored. The timeout will be scaled by the
     * {@link SeleniumGlobals#getTimeMultiplier()}. If the call throws an exception at the end the exception will be
     * wrapped by a {@link SeleniumException} and gets thrown this way.
     *
     * @param timeoutInSeconds the timeout (will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()})
     * @param runnable the {@link Runnable}
     * @param delayInSeconds the initial delay between calls
     * @throws SeleniumFailException if the call fails to produce a value in time
     */
    public static void retryOnFail(double timeoutInSeconds, Runnable runnable, double delayInSeconds)
        throws SeleniumFailException
    {
        keepTrying(timeoutInSeconds, () -> {
            runnable.run();

            return true;
        }, delayInSeconds);
    }

    /**
     * Keeps calling the {@link Callable} until it does not throw an exception, returns an {@link Optional} with a value
     * or another non-null value. Uses {@link SeleniumGlobals#getShortTimeoutInSeconds()} as default timeout. The
     * timeout will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()}. If the call throws an exception at the
     * end the exception will be wrapped by a {@link SeleniumException} and gets thrown this way. The {@link Callable}
     * will be called multiple times during the timeout.
     *
     * @param <Any> the expected return type
     * @param callable the {@link Callable}
     * @return the result
     * @throws SeleniumException if the {@link Callable} fails horribly
     * @throws SeleniumInterruptedException on process interruption
     * @throws SeleniumTimeoutException on timeout
     */
    public static <Any> Any keepTrying(Callable<Any> callable) throws SeleniumException
    {
        return keepTrying(SeleniumGlobals.getShortTimeoutInSeconds(), callable, 0.1);
    }

    /**
     * Keeps calling the {@link Callable} until it does not throw an exception, returns an {@link Optional} with a value
     * or another non-null value. If the timeout &lt;= 0 or NaN, it calls the {@link Callable} at least once and the
     * timeout will be ignored. The timeout will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()}. If the
     * call throws an exception at the end the exception will be wrapped by a {@link SeleniumException} and gets thrown
     * this way. The {@link Callable} will be called multiple times during the timeout.
     *
     * @param <Any> the expected return type
     * @param timeoutInSeconds the timeout (will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()})
     * @param callable the {@link Callable}
     * @return the result
     * @throws SeleniumException if the {@link Callable} fails horribly
     * @throws SeleniumInterruptedException on process interruption
     * @throws SeleniumTimeoutException on timeout
     */
    public static <Any> Any keepTrying(double timeoutInSeconds, Callable<Any> callable) throws SeleniumException
    {
        return keepTrying(timeoutInSeconds, callable, 0.1);
    }

    /**
     * Keeps calling the {@link Callable} until it does not throw an exception, returns an {@link Optional} with a value
     * or another non-null value. If the timeout &lt;= 0 or NaN, it calls the {@link Callable} at least once and the
     * timeout will be ignored. The timeout will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()}. If the
     * call throws an exception at the end the exception will be wrapped by a {@link SeleniumException} and gets thrown
     * this way.
     *
     * @param <Any> the expected return type
     * @param timeoutInSeconds the timeout (will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()})
     * @param callable the {@link Callable}
     * @param delayInSeconds the initial delay between calls
     * @return the result or the call
     * @throws SeleniumFailException if the call fails to produce a value in time
     */
    public static <Any> Any keepTrying(double timeoutInSeconds, Callable<Any> callable, double delayInSeconds)
        throws SeleniumFailException
    {
        double scaledTimeoutInSeconds = scaleTimeout(timeoutInSeconds);
        double maxDelayInSeconds = scaledTimeoutInSeconds / 20;

        if ((long) (scaledTimeoutInSeconds * 1000) <= 0)
        {
            return tryOnce(callable);
        }

        try
        {
            Any result = null;
            long endMillis = (long) (System.currentTimeMillis() + scaledTimeoutInSeconds * 1000);

            while (true)
            {
                long nextTickMillis = (long) (System.currentTimeMillis() + delayInSeconds * 1000);

                // slowly increase the delay to reduce resource usage
                delayInSeconds = Math.min(delayInSeconds * 1.2, maxDelayInSeconds);

                try
                {
                    result = callable.call();
                }
                catch (Throwable e)
                {
                    if (System.currentTimeMillis() > endMillis)
                    {
                        throw new SeleniumFailException(LOG.hintAt("Keep trying failed"), e);
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
                    throw new SeleniumFailException(
                        LOG.hintAt("Keep trying timed out (%,.1f seconds)", scaledTimeoutInSeconds));
                }

                waitUntil(nextTickMillis);
            }
        }
        catch (SeleniumFailException e)
        {
            throw e;
        }
        catch (Throwable e)
        {
            throw new SeleniumFailException(LOG.hintAt("Keep trying failed"), e);
        }
    }

    private static <Any> Any tryOnce(Callable<Any> callable)
    {
        Any result;

        try
        {
            result = callable.call();
        }
        catch (Exception e)
        {
            throw new SeleniumFailException(LOG.hintAt("Try once failed"), e);
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

        throw new SeleniumFailException(LOG.hintAt("Try once failed"));
    }

    /**
     * Calls the {@link Callable} once. The timeout will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()}.
     * If the timeout is &lt;= 0 or NaN, the timeout will be ignored.
     *
     * @param <Any> the expected return type
     * @param timeoutInSeconds the timeout (will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()})
     * @param callable the {@link Callable}
     * @return the result of the call
     * @throws SeleniumException wrapper for exceptions
     * @throws SeleniumInterruptedException on process interruption
     * @throws SeleniumTimeoutException on timeout
     */
    public static <Any> Any callWithTimeout(double timeoutInSeconds, Callable<Any> callable)
        throws SeleniumException, SeleniumInterruptedException, SeleniumTimeoutException
    {
        double scaledTimeoutInSeconds = scaleTimeout(timeoutInSeconds);

        Future<Any> future = POOL_THREAD_POOL.submit(ThreadUtils.persistCallLine(callable));

        try

        {
            if (Double.isNaN(scaledTimeoutInSeconds)
                || Double.isInfinite(scaledTimeoutInSeconds)
                || (long) (scaledTimeoutInSeconds * 1000) <= 0)
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

            throw new SeleniumException(LOG.hintAt("Call failed in callWithTimeout()"), cause);
        }
        catch (InterruptedException e)
        {
            throw new SeleniumInterruptedException(
                String.format("Call interrupted at %s", ThreadUtils.describeCallLine()), e);
        }
        catch (TimeoutException e)
        {
            throw new SeleniumTimeoutException(LOG.hintAt("Call timed out (%,.1f seconds)", scaledTimeoutInSeconds), e);
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
        Callable<Any> parallelTask = ThreadUtils.persistCallLine(callable);

        return POOL_THREAD_POOL.submit(() -> {
            Any result = null;

            try
            {
                result = parallelTask.call();
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
     * {@link SeleniumGlobals#getTimeMultiplier()}.
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

                futures.add(POOL_THREAD_POOL.submit(worker));
            }

            List<Any> results = new ArrayList<>();

            for (Future<Any> future : futures)
            {
                results.add(future.get());
            }

            return results;
        });
    }

    private static double scaleTime(double time)
    {
        return time * SeleniumGlobals.getTimeMultiplier();
    }

    private static double scaleTimeout(double timeout)
    {
        if (SeleniumGlobals.isDebug())
        {
            return Double.POSITIVE_INFINITY;
        }

        return timeout * SeleniumGlobals.getTimeMultiplier();
    }

    /**
     * Sometimes it is possible that we want to perform a operation on an element. But some JavaScript is currently
     * messing around with the DOM. Then we have a stale element and an exception is thrown. We can retry to perform the
     * operation on the element again and it should work.
     *
     * @param <Any> type of return value
     * @param callable the operation to perform
     * @return the operations result
     */
    public static <Any> Any retryOnStale(Callable<Any> callable)
    {
        int attempts = 3;

        while (true)
        {
            long nextTickMillis = System.currentTimeMillis() + 100;

            try
            {
                return callable.call();
            }
            catch (StaleElementReferenceException e)
            {
                if (!SeleniumGlobals.isDebug())
                {
                    attempts--;
                }

                if (attempts <= 0)
                {
                    throw e;
                }

                LOG.trace("Element is stale, retrying ...");

                waitUntil(nextTickMillis);
            }
            catch (RuntimeException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                throw new SeleniumException(LOG.hintAt("Call failed in retryOnStale()"), e);
            }
        }
    }

    /**
     * Sometimes it is possible that we want to perform a operation on an element. But some JavaScript is currently
     * messing around with the DOM. Then we have a stale element and an exception is thrown. We can retry to perform the
     * operation on the element again and it should work.
     *
     * @param runnable the operation to perform
     */
    public static void retryOnStale(Runnable runnable)
    {
        int attempts = 3;

        while (true)
        {
            long nextTickMillis = System.currentTimeMillis() + 100;

            try
            {
                runnable.run();

                return;
            }
            catch (StaleElementReferenceException e)
            {
                if (!SeleniumGlobals.isDebug())
                {
                    attempts--;
                }

                if (attempts <= 0)
                {
                    throw e;
                }

                LOG.trace("Element is stale, retrying ...");

                waitUntil(nextTickMillis);
            }
            catch (RuntimeException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                throw new SeleniumException(LOG.hintAt("Call failed in retryOnStale()"), e);
            }
        }
    }

}
