package at.porscheinformatik.seleniumcomponents;

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
     * Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for the component to become clickable and clicks
     * it.
     */
    default void click()
    {
        LOG.interaction("Clicking on %s", describe());

        SeleniumAsserts.assertIsClickable(SeleniumGlobals.getShortTimeoutInSeconds(), this);
        SeleniumUtils.retryOnStale(() -> element().click());
    }

}
