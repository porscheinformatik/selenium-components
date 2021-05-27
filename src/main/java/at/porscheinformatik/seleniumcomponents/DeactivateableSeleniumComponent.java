package at.porscheinformatik.seleniumcomponents;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * A {@link SeleniumComponent} that may be disabled.
 *
 * @author ham
 */
public interface DeactivateableSeleniumComponent extends SeleniumComponent
{
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
     * Returns true if the component is enabled. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for
     * the component to become available.
     *
     * @return true if enabled
     */
    default boolean isDisabled()
    {
        try
        {
            return SeleniumUtils.retryOnStale(() -> {
                WebElement element = element();

                return !element.isEnabled();
            });
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }
}
