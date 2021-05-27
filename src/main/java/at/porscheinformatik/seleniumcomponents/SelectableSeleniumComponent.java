package at.porscheinformatik.seleniumcomponents;

import org.openqa.selenium.NoSuchElementException;

/**
 * A {@link SeleniumComponent} that can be selected
 *
 * @author ham
 */
public interface SelectableSeleniumComponent extends ClickableSeleniumComponent
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
            return SeleniumUtils.retryOnStale(() -> element().isSelected());
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * Sets the selected state for this component if not already selected.
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
     * Unsets the selected state of this component if currently selected.
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
