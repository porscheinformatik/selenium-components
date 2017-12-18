package at.porscheinformatik.seleniumcomponents;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * A {@link SeleniumComponent} that is visible
 * 
 * @author ham
 */
public interface VisibleSeleniumComponent extends SeleniumComponent
{

    /**
     * Returns true if a {@link WebElement} described by this component is visible. By default it checks, if the element
     * is visible. This method has no timeout, it does not wait for the component to become existent.
     *
     * @return true if the component is visible
     */
    default boolean isVisible()
    {
        try
        {
            return SeleniumUtils.retryOnStale(() -> {
                WebElement element = element();

                return element.isDisplayed();
            });
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    default void waitUntilVisible(double timeoutInSeconds)
    {
        SeleniumAsserts.assertIsVisible(timeoutInSeconds, this);
    }

}
