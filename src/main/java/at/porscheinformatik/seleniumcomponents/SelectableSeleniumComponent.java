package at.porscheinformatik.seleniumcomponents;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * A {@link SeleniumComponent} that can be selected
 *
 * @author ham
 */
public interface SelectableSeleniumComponent extends SeleniumComponent
{

    /**
     * Returns true if the component is selected. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for
     * the component to become available.
     *
     * @return true if selected
     */
    default boolean isSelected()
    {
        try
        {
            return SeleniumUtils.retryOnStale(() -> {
                WebElement element = element();

                return element.isSelected();
            });
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

}
