package at.porscheinformatik.seleniumcomponents;

import java.util.Objects;

import org.openqa.selenium.WebElement;

/**
 * The base class for components accessible by the Selenium tests. A {@link SeleniumComponent} usually describes a node
 * in the DOM.
 *
 * @author ham
 */
public interface SeleniumComponent extends WebElementContainer
{

    /**
     * Returns the parent of this component.
     *
     * @return the parent, may be null if the component is root (like a page).
     */
    SeleniumComponent parent();

    /**
     * Returns the {@link SeleniumContext} of this component, which is, by default, the context of the parent. If this
     * component is root, it must implement this method and return a valid context.
     *
     * @return the context, never null
     */
    default SeleniumContext context()
    {
        SeleniumComponent parent = parent();

        Objects.requireNonNull(parent, () -> String.format(
            "The parent is null which means, that this component is root. Implement this method by supplying a valid context"));

        return Objects.requireNonNull(parent.context(),
            () -> String.format("The context of the component defined by %s is null", parent.getClass()));
    }

    /**
     * Returns true if a {@link WebElement} described by this component is ready and available (it must no be visible,
     * though). This method has no timeout, it does not wait for the component to become existent.
     *
     * @return true if the component exists
     */
    boolean isReady();

    /**
     * Returns a description of this component, usually as name and selector tuple.
     *
     * @return a description of this component
     */
    String describe();

}
