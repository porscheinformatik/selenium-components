package at.porscheinformatik.seleniumcomponents;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * A {@link SeleniumComponent} that is editable.
 *
 * @author ham
 */
public interface EditableSeleniumComponent extends ClickableSeleniumComponent
{

    /**
     * Returns true if the component is editable. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for
     * the component to become available.
     *
     * @return true if editable
     */
    default boolean isEditable()
    {
        try
        {
            return SeleniumUtils.retryOnStale(() -> {
                WebElement element = element();

                return element.isDisplayed() && element.isEnabled();
            });
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * Returns true if the component is enabled. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for
     * the component to become available.
     *
     * @return true if enabled
     */
    default boolean isEnabled()
    {
        try
        {
            return SeleniumUtils.retryOnStale(() -> {
                WebElement element = element();

                return element.isEnabled();
            });
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * Clears the component. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for the component to
     * become available.
     */
    default void clear()
    {
        SeleniumAsserts.assertIsEditable(SeleniumGlobals.getShortTimeoutInSeconds(), this);
        SeleniumUtils.retryOnStale(() -> element().clear());
    }

    /**
     * Sends the key sequences to the component. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for
     * the component to become available.
     *
     * @param keysToSend the keys to send (multiple)
     */
    default void sendKeys(CharSequence... keysToSend)
    {
        SeleniumAsserts.assertIsClickable(SeleniumGlobals.getShortTimeoutInSeconds(), this);
        SeleniumUtils.retryOnStale(() -> {
            WebElement element = element();

            for (CharSequence current : keysToSend)
            {
                element.sendKeys(current);
            }
        });
    }

}
