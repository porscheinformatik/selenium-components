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
     * is visible. This method has no timeout, it does not wait for the component to become existent. <br>
     * <br>
     * It it NO good idea, to check for invisibility. If the component is not visible, Selenium always waits for some
     * time. This causes tests to run slowly and timeouts to fail.
     *
     * @return true if the component is visible
     */
    default boolean isVisible()
    {
        try
        {
            return SeleniumUtils.retryOnStale(() -> {
                WebElement element = element();
                boolean visible = element.isDisplayed();

                if (!visible && LOG.isDebugEnabled())
                {
                    LOG.debug(String.format("[S] Element not visible: %s", describe()));
                }

                return visible;
            });
        }
        catch (NoSuchElementException e)
        {
            if (LOG.isDebugEnabled())
            {
                LOG.debug(String.format("[S] Element not found: %s", describe()));
            }

            return false;
        }
    }

    default void waitUntilVisible(double timeoutInSeconds)
    {
        SeleniumAsserts.assertIsVisible(timeoutInSeconds, this);
    }

}
