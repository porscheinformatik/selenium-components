package at.porscheinformatik.seleniumcomponents;

import static at.porscheinformatik.seleniumcomponents.SeleniumUtils.*;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

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

    public static boolean exists(AbstractSeleniumComponent component)
    {
        try
        {
            return SeleniumActions.retryOnStaleAndReturn(() -> {
                SeleniumComponent parent = component.parent();

                if (parent != null)
                {
                    return !component.selector().findAll(parent).isEmpty();
                }

                return component.element(0) != null;
            }, 2);
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    public static boolean exists(double timeoutInSeconds, SeleniumComponent component)
    {
        return optional(() -> keepTrying(timeoutInSeconds, () -> exists(component) ? true : null)).orElse(false);
    }

    public static void waitUntilExists(double timeoutInSeconds, SeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, () -> exists(component));
    }

    public static String getTagName(SeleniumComponent component)
    {
        return SeleniumActions.retryOnStaleAndReturn(() -> {
            return component.element().getTagName();
        }, 2);
    }

    public static String getAttribute(SeleniumComponent component, String name)
    {
        return SeleniumActions.retryOnStaleAndReturn(() -> {
            return component.element().getAttribute(name);
        }, 2);
    }

    public static boolean isClickable(SeleniumComponent component)
    {
        try
        {
            return SeleniumActions.retryOnStaleAndReturn(() -> {
                WebElement element = component.element();

                return element.isDisplayed() && element.isEnabled();
            }, 2);
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    public static boolean isClickable(double timeoutInSeconds, SeleniumComponent component)
    {
        return optional(() -> keepTrying(timeoutInSeconds, () -> isClickable(component) ? true : null)).orElse(false);
    }

    public static void waitUntilClickable(double timeoutInSeconds, SeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, () -> isClickable(component));
    }

    public static void click(double timeoutInSeconds, SeleniumComponent component)
    {
        waitUntilClickable(timeoutInSeconds, component);
        component.element().click();
    }

    public static boolean isVisible(SeleniumComponent component)
    {
        try
        {
            return SeleniumActions.retryOnStaleAndReturn(() -> {
                WebElement element = component.element();

                return element.isDisplayed();
            }, 2);
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    public static boolean isVisible(double timeoutInSeconds, SeleniumComponent component)
    {
        return optional(() -> keepTrying(timeoutInSeconds, () -> isVisible(component) ? true : null)).orElse(false);
    }

    public static void waitUntilVisible(double timeoutInSeconds, SeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, () -> isVisible(component));
    }

    public static void waitUntilInvisible(double timeoutInSeconds, SeleniumComponent component)
    {
        waitUntil(timeoutInSeconds, () -> !isVisible(component));
    }

    public static String getText(SeleniumComponent component)
    {
        return SeleniumActions.retryOnStaleAndReturn(() -> {
            WebElement element = component.element();

            return element.getText().trim();
        }, 2);
    }

    public static String getText(double timeoutInSeconds, SeleniumComponent component)
    {
        return optional(() -> keepTrying(timeoutInSeconds, () -> {
            String text = getText(component);

            return isEmpty(text) ? null : text;
        })).orElse(null);
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
     * Sometimes it is possible that we want to perform a operation on an element. But some javascript is currently
     * messing around with the DOM. Then we have a stale element and an exception is thrown. We can retry to perform the
     * operation on the element again and it should work.
     *
     * @param operation the operation to perform
     * @param numberOfAttempts the number of times to retry
     * @param <RETURN_TYPE> type of return value
     * @return the operations result
     */
    public static <RETURN_TYPE> RETURN_TYPE retryOnStaleAndReturn(Supplier<RETURN_TYPE> operation, int numberOfAttempts)
    {
        if (numberOfAttempts <= 0)
        {
            throw new IllegalArgumentException("At least one attempt should be made");
        }

        int attempts = 1;

        while (attempts <= numberOfAttempts)
        {
            try
            {
                return operation.get();
            }
            catch (StaleElementReferenceException e)
            {
                if (attempts == numberOfAttempts)
                {
                    throw e;
                }
            }

            attempts++;
        }

        throw new RuntimeException("Method does not exit successfully");
    }

    /**
     * Sometimes it is possible that we want to perform a operation on an element. But some javascript is currently
     * messing around with the DOM. Then we have a stale element and an exception is thrown. We can retry to perform the
     * operation on the element again and it should work.
     *
     * @param operation the operation to perform
     * @param numberOfAttempts the number of times to retry
     */
    public static void retryOnStale(Runnable operation, int numberOfAttempts)
    {
        int attempts = 1;

        while (attempts <= numberOfAttempts)
        {

            try
            {
                operation.run();
            }
            catch (StaleElementReferenceException e)
            {
                if (attempts == numberOfAttempts)
                {
                    throw e;
                }

                try
                {
                    Thread.sleep(5);
                }
                catch (InterruptedException e1)
                {
                    // Nothing to do here
                }
            }

            attempts++;
        }
    }

}
