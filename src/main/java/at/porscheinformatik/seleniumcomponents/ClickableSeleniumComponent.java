package at.porscheinformatik.seleniumcomponents;

import static org.hamcrest.Matchers.*;

import org.openqa.selenium.WebElement;

/**
 * A {@link SeleniumComponent} that is clickable.
 *
 * @author ham
 */
public interface ClickableSeleniumComponent extends VisibleSeleniumComponent
{

    /**
     * Returns true if the component is clickable. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for
     * the component to become available.
     *
     * @return true if clickable
     */
    default boolean isClickable()
    {
        try
        {
            return SeleniumUtils.retryOnStale(() -> {
                WebElement element = element();

                return element.isDisplayed() && element.isEnabled();
            });
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * Waits 5 seconds for the component to become clickable and clicks it. We use a bit more than the default short
     * timeout because it is possible, that selecting a element needs a few seconds to complete. Depending on the
     * Component Hirarchy used.
     */
    default void click()
    {
        click(5);
    }

    /**
     * Waits the given seconds for the component to become clickable and clicks it.
     * 
     * @param timeoutInSeconds the amount of time to wait until the operation fails
     */
    default void click(long timeoutInSeconds)
    {
        LOG.interaction("Clicking on %s", describe());

        SeleniumAsserts.assertThatSoon(timeoutInSeconds, () -> {
            if (this.isClickable())
            {
                element().click();

                return true;
            }

            return false;
        }, is(true));
    }

}
