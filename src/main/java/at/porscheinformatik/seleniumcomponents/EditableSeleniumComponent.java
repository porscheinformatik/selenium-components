package at.porscheinformatik.seleniumcomponents;

import static org.hamcrest.Matchers.*;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * A {@link SeleniumComponent} that is editable.
 *
 * @author ham
 */
public interface EditableSeleniumComponent extends ClickableSeleniumComponent, DeactivateableSeleniumComponent
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
     * Clears the component. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for the component to
     * become available.
     */
    default void clear()
    {
        LOG.interaction("Clearing %s", describe());

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
        LOG.interaction("Sending \"%s\" to %s", String.join("", keysToSend), describe());

        // It could take some time to input the data. So we should wait longer than the short timeout
        SeleniumAsserts.assertThatLater(() -> {
            if (isClickable())
            {
                WebElement element = element();

                for (CharSequence current : keysToSend)
                {
                    element.sendKeys(current);
                }

                return true;
            }

            return false;
        }, is(true));
    }

}
