package at.porscheinformatik.seleniumcomponents;

import java.util.ArrayList;
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

import org.openqa.selenium.StaleElementReferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Some utilities for testing.
 *
 * @author ham
 */
public final class SeleniumUtils
{

    private static final Logger LOG = LoggerFactory.getLogger(SeleniumUtils.class);

    private static final ThreadLocal<Integer> ALREADY_TRYING = new ThreadLocal<>();

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
     * Waits until the component becomes ready.
     *
     * @param timeoutInSeconds the timeout in seconds
     * @param component the component
     */
    public static void waitUntilReady(double timeoutInSeconds, SeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, () -> component.isReady());
    }

    /**
     * Waits for the specified amount of seconds for the component to become clickable.
     *
     * @param timeoutInSeconds the timeout in seconds
     * @param component the component
     */
    public static void waitUntilClickable(double timeoutInSeconds, ClickableSeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, () -> component.isClickable());
    }

    /**
     * Waits for the specified amount of seconds for the component to become visible.
     *
     * @param timeoutInSeconds the timeout in seconds
     * @param component the component
     */
    public static void waitUntilVisible(double timeoutInSeconds, VisibleSeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, () -> component.isVisible());
    }

    /**
     * Waits for the specified amount of seconds for the component to become invisible.
     *
     * @param timeoutInSeconds the timeout in seconds
     * @param component the component
     */
    public static void waitUntilInvisible(double timeoutInSeconds, VisibleSeleniumComponent component)
    {
        SeleniumGlobals.ignoreDebug(() -> waitUntil(timeoutInSeconds, () -> !component.isVisible()));
    }

    /**
     * Waits for the specified amount of seconds for the component to become enabled.
     *
     * @param timeoutInSeconds the timeout in seconds
     * @param component the component
     */
    public static void waitUntilEnabled(double timeoutInSeconds, EditableSeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, component::isEnabled);
    }

    /**
     * Waits for the specified amount of seconds for the component to become selected.
     *
     * @param timeoutInSeconds the timeout in seconds
     * @param component the component
     */
    public static void waitUntilSelected(double timeoutInSeconds, SelectableSeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, component::isSelected);
    }

    /**
     * Waits for the specified amount of seconds for the component to become editable.
     *
     * @param timeoutInSeconds the timeout in seconds
     * @param component the component
     */
    public static void waitUntilEditable(double timeoutInSeconds, EditableSeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, component::isEditable);
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
     * Returns the attribute with the specified name. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds
     * for the component to become available.
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
            String message = String.format("Call failed at %s", ThreadUtils.describeCallLine());

            if (LOG.isDebugEnabled())
            {
                LOG.debug("[S] " + message);
            }

            throw new SeleniumException(message, e);
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

            if (LOG.isTraceEnabled())
            {
                LOG.trace(String.format("[S] Waiting for %,.3f seconds ...", scaledSeconds));
            }

            Thread.sleep((long) (scaledSeconds * 1000));
        }
        catch (InterruptedException e)
        {
            throw new SeleniumException("WaitForSeconds got interrupted", e);
        }
    }

    /**
     * Waits until the check function does not throw an exception and returns true. If the timeout is &lt;= 0 or NaN, it
     * checks it at least once. The timeout will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()}. The check
     * function will be called four times a second. If the check throws an exception at the end the exception will be
     * wrapped by a {@link SeleniumException} and gets thrown this way.
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
     * timeout will be ignored. The timeout will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()}. If the
     * call throws an exception at the end the exception will be wrapped by a {@link SeleniumException} and gets thrown
     * this way. The {@link Callable} will be called four times a second.
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
        return keepTrying(timeoutInSeconds, callable, 0.25);
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
     * @param delayInSeconds the delay between calls (will be scaled by the {@link SeleniumGlobals#getTimeMultiplier()})
     * @return the result or the call
     * @throws SeleniumFailException if the call fails to produce a value in time
     */
    public static <Any> Any keepTrying(double timeoutInSeconds, Callable<Any> callable, double delayInSeconds)
        throws SeleniumFailException
    {
        Integer alreadyTrying = ALREADY_TRYING.get();

        if (alreadyTrying != null)
        {
            return tryOnce(callable);
        }

        ALREADY_TRYING.set(alreadyTrying != null ? alreadyTrying.intValue() + 1 : 1);

        try
        {
            double scaledTimeoutInSeconds = scaleTimeout(timeoutInSeconds);

            if ((long) (scaledTimeoutInSeconds * 1000) <= 0)
            {
                return tryOnce(callable);
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
                                String message =
                                    String.format("Keep trying failed at %s", ThreadUtils.describeCallLine());

                                if (LOG.isDebugEnabled())
                                {
                                    LOG.debug("[S] " + message);
                                }

                                throw new SeleniumFailException(message, e);
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
                            String message = String.format("Keep trying timed out (%,.1f seconds) at %s",
                                scaledTimeoutInSeconds, ThreadUtils.describeCallLine());

                            if (LOG.isDebugEnabled())
                            {
                                LOG.debug("[S] " + message);
                            }

                            throw new SeleniumFailException(message);
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
                String message = String.format("Keep trying failed at %s", ThreadUtils.describeCallLine());

                if (LOG.isDebugEnabled())
                {
                    LOG.debug("[S] " + message);
                }

                throw new SeleniumFailException(message, e);
            }
        }
        finally
        {
            ALREADY_TRYING.set(alreadyTrying);
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
            String message = String.format("Try once failed at %s", ThreadUtils.describeCallLine());

            if (LOG.isDebugEnabled())
            {
                LOG.debug("[S] " + message);
            }

            throw new SeleniumFailException(message, e);
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

        String message = String.format("Try once failed at %s", ThreadUtils.describeCallLine());

        if (LOG.isDebugEnabled())
        {
            LOG.debug("[S] " + message);
        }

        throw new SeleniumFailException(message);
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

            String message = String.format("Call failed in callWithTimeout() at %s", ThreadUtils.describeCallLine());

            if (LOG.isDebugEnabled())
            {
                LOG.debug("[S] " + message);
            }

            throw new SeleniumException(message, cause);
        }
        catch (InterruptedException e)
        {
            throw new SeleniumInterruptedException(
                String.format("Call interrupted at %s", ThreadUtils.describeCallLine()), e);
        }
        catch (TimeoutException e)
        {
            String message = String.format("Call timed out (%,.1f seconds) at %s", scaledTimeoutInSeconds,
                ThreadUtils.describeCallLine());

            if (LOG.isDebugEnabled())
            {
                LOG.debug("[S] " + message);
            }

            throw new SeleniumTimeoutException(message, e);
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

                waitForSeconds(0.1);
            }
            catch (RuntimeException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                String message = String.format("Call failed in retryOnStale() at %s", ThreadUtils.describeCallLine());

                if (LOG.isDebugEnabled())
                {
                    LOG.debug("[S] " + message);
                }

                throw new SeleniumException(message, e);
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

                waitForSeconds(0.1);
            }
            catch (RuntimeException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                String message = String.format("Call failed in retryOnStale() at %s", ThreadUtils.describeCallLine());

                if (LOG.isDebugEnabled())
                {
                    LOG.debug("[S] " + message);
                }

                throw new SeleniumException(message, e);
            }
        }
    }

}
