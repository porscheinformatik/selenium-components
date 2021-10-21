package at.porscheinformatik.seleniumcomponents;

import static org.hamcrest.Matchers.*;

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
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilEnabled(double timeoutInSeconds)
    {
        SeleniumAsserts
            .assertThatSoon(timeoutInSeconds, "Component becomes enabled: " + describe(), () -> this,
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
     * Waits until the component becomes selected.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilSelected(double timeoutInSeconds)
    {
        SeleniumAsserts
            .assertThatSoon(timeoutInSeconds, "Component becomes selected: " + describe(), () -> this,
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
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilClickable(double timeoutInSeconds)
    {
        SeleniumAsserts
            .assertThatSoon(timeoutInSeconds, "Component becomes clickable: " + describe(), () -> this,
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
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilEditable(double timeoutInSeconds)
    {
        SeleniumAsserts
            .assertThatSoon(timeoutInSeconds, "Component becomes editable: " + describe(), () -> this,
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
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilDisabled(double timeoutInSeconds)
    {
        SeleniumAsserts
            .assertThatSoon(timeoutInSeconds, "Component becomes disabled: " + describe(), () -> this,
                SeleniumMatchers.isDisabled());
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
        String description = LOG.interaction("Clicking on %s", describe());

        SeleniumAsserts.assertThatSoon(timeoutInSeconds, description, () -> this, SeleniumMatchers.isClickable());

        element().click();
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

}
