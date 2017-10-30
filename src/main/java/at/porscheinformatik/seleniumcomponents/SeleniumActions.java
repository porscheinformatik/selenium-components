package at.porscheinformatik.seleniumcomponents;

import static at.porscheinformatik.seleniumcomponents.SeleniumUtils.*;

import java.util.concurrent.Callable;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

/**
 * Utilities for {@link SeleniumComponent}s
 *
 * @author ham
 */
public final class SeleniumActions
{

    private SeleniumActions()
    {
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
     * Returns true if the method {@link SeleniumComponent#isReady()} returns true.
     *
     * @param component the component
     * @return true if ready
     */
    public static boolean isReady(SeleniumComponent component)
    {
        return component.isReady();
    }

    /**
     * Waits until the component becomes ready.
     *
     * @param timeoutInSeconds the timeout in seconds
     * @param component the component
     */
    public static void waitUntilReady(double timeoutInSeconds, SeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, () -> isReady(component));
    }

    /**
     * Returns true if the component is clickable. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for
     * the component to become available.
     *
     * @param component the component
     * @return true if clickable
     */
    public static boolean isClickable(SeleniumComponent component)
    {
        try
        {
            return SeleniumActions.retryOnStale(() -> {
                WebElement element = component.element();

                return element.isDisplayed() && element.isEnabled();
            });
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * Waits for the specified amount of seconds for the component to become clickable.
     *
     * @param timeoutInSeconds the timeout in seconds
     * @param component the component
     */
    public static void waitUntilClickable(double timeoutInSeconds, SeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, () -> isClickable(component));
    }

    /**
     * Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for the component to become clickable and clicks
     * it.
     *
     * @param component the component
     */
    public static void click(SeleniumComponent component)
    {
        waitUntilClickable(SeleniumGlobals.getShortTimeoutInSeconds(), component);
        component.element().click();
    }

    /**
     * Returns true if the component is visible. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for
     * the component to become available.
     *
     * @param component the component
     * @return true if visible
     */
    public static boolean isVisible(SeleniumComponent component)
    {
        try
        {
            return SeleniumActions.retryOnStale(() -> {
                WebElement element = component.element();

                return element.isDisplayed();
            });
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * Waits for the specified amount of seconds for the component to become visible.
     *
     * @param timeoutInSeconds the timeout in seconds
     * @param component the component
     */
    public static void waitUntilVisible(double timeoutInSeconds, SeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, () -> isVisible(component));
    }

    /**
     * Waits for the specified amount of seconds for the component to become invisible.
     *
     * @param timeoutInSeconds the timeout in seconds
     * @param component the component
     */
    public static void waitUntilInvisible(double timeoutInSeconds, SeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, () -> !isVisible(component));
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
        return SeleniumActions.retryOnStale(() -> component.element().getTagName());
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
        return SeleniumActions.retryOnStale(() -> component.element().getAttribute(name));
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
        return SeleniumActions.retryOnStale(() -> component.element().getText().trim());
    }

    /**
     * Clears the component. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for the component to
     * become available.
     *
     * @param component the component
     */
    public static void clear(SeleniumComponent component)
    {
        SeleniumActions.retryOnStale(() -> component.element().clear());
    }

    /**
     * Sends the key sequences to the component. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for
     * the component to become available.
     *
     * @param component the component
     * @param keysToSend the keys to send (multiple)
     */
    public static void sendKeys(SeleniumComponent component, CharSequence... keysToSend)
    {
        SeleniumActions.retryOnStale(() -> {
            WebElement element = component.element();

            for (CharSequence current : keysToSend)
            {
                element.sendKeys(current);
            }
        });
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
        return !selector.findAll(component).isEmpty();
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
                if (attempts-- <= 0)
                {
                    throw e;
                }
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
            }
            catch (StaleElementReferenceException e)
            {
                if (attempts-- <= 0)
                {
                    throw e;
                }

                SeleniumUtils.waitForSeconds(0.1);
            }
            catch (Exception e)
            {
                throw new SeleniumException(String.format("Call failed: %s", describeCallLine()), e);
            }
        }
    }

}