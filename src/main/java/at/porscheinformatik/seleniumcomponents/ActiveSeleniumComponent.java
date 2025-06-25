package at.porscheinformatik.seleniumcomponents;

import static org.hamcrest.Matchers.*;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * A {@link SeleniumComponent} that is clickable and may be enabled, ediable or selected.
 *
 * @author ham
 */
public interface ActiveSeleniumComponent extends SeleniumComponent
{
    /**
     * Returns true if a {@link WebElement} described by this component is visible. By default it checks, if the element
     * is visible and has a size where both x and y dimensions are greater than 0. The latter has to be checked to
     * prevent an "ElementNotInteractableException: element not interactable: element has zero size" when trying to
     * interact with the element.<br>
     * <br>
     * This method has no timeout, it does not wait for the component to become existent.
     *
     * @return true if the component is visible and has a size greater than 0
     */
    @Override
    default boolean isVisible()
    {
        try
        {
            return SeleniumUtils.retryOnStale(() -> {
                if(!element().isDisplayed()) {
                    return false;
                }

                Dimension size = element().getSize();
                return size.getWidth() > 0 && size.getHeight() > 0;
            });
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * Returns true if the component is enabled.
     *
     * @return true if enabled
     */
    default boolean isEnabled()
    {
        try
        {
            return SeleniumUtils.retryOnStale(() -> element().isEnabled());
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * Waits until the component becomes enabled.
     */
    default void waitUntilEnabled()
    {
        assertEnabledSoon(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Waits until the component becomes enabled.
     */
    default void assertEnabledSoon()
    {
        assertEnabledSoon(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Waits until the component becomes enabled.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilEnabled(double timeoutInSeconds)
    {
        assertEnabledSoon(timeoutInSeconds);
    }

    /**
     * Waits until the component becomes enabled.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void assertEnabledSoon(double timeoutInSeconds)
    {
        SeleniumAsserts.assertThatSoon(timeoutInSeconds, "Component becomes enabled: " + describe(), () -> this,
            SeleniumMatchers.isEnabled());
    }

    /**
     * Returns true if the component is selectable and selected.
     *
     * @return true if selected
     */
    default boolean isSelected()
    {
        try
        {
            return SeleniumUtils.retryOnStale(() -> element().isSelected());
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * Calls the appropriate method
     *
     * @param selected true for {@link #select()}, false for {@link #unselect()}
     */
    default void setSelected(boolean selected)
    {
        if (selected)
        {
            select();
        }
        else
        {
            unselect();
        }
    }

    /**
     * Waits until the component becomes selected.
     */
    default void waitUntilSelected()
    {
        assertSelectedSoon(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Waits until the component becomes selected.
     */
    default void assertSelectedSoon()
    {
        assertSelectedSoon(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Waits until the component becomes selected.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilSelected(double timeoutInSeconds)
    {
        assertSelectedSoon(timeoutInSeconds);
    }

    /**
     * Waits until the component becomes selected.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void assertSelectedSoon(double timeoutInSeconds)
    {
        SeleniumAsserts.assertThatSoon(timeoutInSeconds, "Component becomes selected: " + describe(), () -> this,
            SeleniumMatchers.isSelected());
    }

    /**
     * Returns true if the component is clickable (displayed and enabled)
     *
     * @return true if clickable
     */
    default boolean isClickable()
    {
        return isVisible() && isEnabled();
    }

    /**
     * Waits until the component becomes clickable.
     */
    default void waitUntilClickable()
    {
        assertClickableSoon(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Waits until the component becomes clickable.
     */
    default void assertClickableSoon()
    {
        assertClickableSoon(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Waits until the component becomes clickable.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilClickable(double timeoutInSeconds)
    {
        assertClickableSoon(timeoutInSeconds);
    }

    /**
     * Waits until the component becomes clickable.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void assertClickableSoon(double timeoutInSeconds)
    {
        SeleniumAsserts.assertThatSoon(timeoutInSeconds, "Component becomes clickable: " + describe(), () -> this,
            SeleniumMatchers.isClickable());
    }

    /**
     * Returns true if the component is editable (displayed and enabled)
     *
     * @return true if editable
     */
    default boolean isEditable()
    {
        return isVisible() && isEnabled();
    }

    /**
     * Waits until the component becomes editable.
     */
    default void waitUntilEditable()
    {
        assertEditableSoon(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Waits until the component becomes editable.
     */
    default void assertEditableSoon()
    {
        assertEditableSoon(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Waits until the component becomes editable.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilEditable(double timeoutInSeconds)
    {
        assertEditableSoon(timeoutInSeconds);
    }

    /**
     * Waits until the component becomes editable.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void assertEditableSoon(double timeoutInSeconds)
    {
        SeleniumAsserts.assertThatSoon(timeoutInSeconds, "Component becomes editable: " + describe(), () -> this,
            SeleniumMatchers.isEditable());
    }

    /**
     * Returns true if the component is not enabled.
     *
     * @return true if enabled
     */
    default boolean isDisabled()
    {
        return !isEnabled();
    }

    /**
     * Waits until the component becomes disabled.
     */
    default void waitUntilDisabled()
    {
        waitUntilDisabled(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Waits until the component becomes disabled.
     */
    default void assertDisabledSoon()
    {
        assertDisabledSoon(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Waits until the component becomes disabled.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilDisabled(double timeoutInSeconds)
    {
        assertDisabledSoon(timeoutInSeconds);
    }

    /**
     * Waits until the component becomes disabled.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void assertDisabledSoon(double timeoutInSeconds)
    {
        SeleniumAsserts.assertThatSoon(timeoutInSeconds, "Component becomes disabled: " + describe(), () -> this,
            SeleniumMatchers.isDisabled());
    }

    /**
     * @return true, if the center of the component is within the current viewport of the browser.
     */
    default boolean isInViewport()
    {
        return (Boolean) ((JavascriptExecutor) environment().getDriver()).executeScript("""
            var elem = arguments[0], box = elem.getBoundingClientRect(), cx = box.left + box.width / 2, cy = box.top + box.height / 2, e = document.elementFromPoint(cx, cy);
            for (; e; e = e.parentElement) if (e === elem) return true;
            return false;""", element());
    }

    /**
     * Waits until the center of the component is within the current viewport of the browser.
     */
    default void waitUntilInViewport()
    {
        assertInViewportSoon(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Waits until the center of the component is within the current viewport of the browser.
     */
    default void assertInViewportSoon()
    {
        assertInViewportSoon(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Waits until the center of the component is within the current viewport of the browser.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilInViewport(double timeoutInSeconds)
    {
        assertInViewportSoon(timeoutInSeconds);
    }

    /**
     * Waits until the center of the component is within the current viewport of the browser.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void assertInViewportSoon(double timeoutInSeconds)
    {
        SeleniumAsserts.assertThatSoon(timeoutInSeconds, "Component is in viewport: " + describe(), () -> this,
            SeleniumMatchers.isInViewport());
    }

    /**
     * Waits more than twice the short timeout for the component to become clickable and clicks it. We use a bit more
     * than the default short timeout because it is possible, that selecting a element needs a few seconds to complete.
     * Depending on the Component Hierarchy used.
     */
    default void click()
    {
        click(SeleniumGlobals.getShortTimeoutInSeconds() * 2.5);
    }

    /**
     * Waits the given seconds for the component to become clickable and clicks it.
     *
     * @param timeoutInSeconds the amount of time to wait until the operation fails
     */
    default void click(double timeoutInSeconds)
    {
        LOG.interaction("Clicking on %s", describe());

        waitUntilClickable(timeoutInSeconds);

        element().click();
    }

    /**
     * Moves the viewport to display the component.
     */
    default void scrollIntoView()
    {
        scrollIntoView(SeleniumGlobals.getShortTimeoutInSeconds());
    }

    /**
     * Moves the viewport to display the component.
     *
     * @param timeoutInSeconds the amount of time to wait until the operation fails
     */
    default void scrollIntoView(double timeoutInSeconds)
    {
        LOG.interaction("Scrolling %s into view", describe());

        ((JavascriptExecutor) environment().getDriver()).executeScript("arguments[0].scrollIntoViewIfNeeded()",
            element());

        waitUntilInViewport(timeoutInSeconds);
    }

    /**
     * Clears the component. Waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds for the component to
     * become available.
     */
    default void clear()
    {
        String description = LOG.interaction("Clearing %s", describe());

        SeleniumAsserts.assertThatSoon(description, () -> this, SeleniumMatchers.isEditable());
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
        String description =
            LOG.interaction("Sending \"%s\" to %s", Utils.escapeJava(String.join("", keysToSend)), describe());

        // It could take some time to input the data. So we should wait longer than the short timeout
        SeleniumAsserts.assertThatLater(description, () -> {
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

    /**
     * Sets the selected state for this component by clicking it, if not already selected.
     */
    default void select()
    {
        if (!isSelected())
        {
            LOG.interaction("Selecting %s", describe());

            click();
        }
    }

    /**
     * Unsets the selected state of this component by clicking it, if currently selected.
     */
    default void unselect()
    {
        if (isSelected())
        {
            LOG.interaction("Deselecting %s", describe());

            click();
        }
    }
}
