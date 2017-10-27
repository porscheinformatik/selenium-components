package at.porscheinformatik.seleniumcomponents;

import java.util.Objects;

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
     * Returns the {@link WebElementSelector} of this component.
     *
     * @return the selector
     */
    WebElementSelector selector();

    /**
     * Returns a description of this component, usually as name and selector tuple.
     *
     * @return a description of this component
     */
    default String describe()
    {
        String description = String.format("%s[%s]", SeleniumUtils.toClassName(getClass()), selector());
        SeleniumComponent parent = parent();

        if (parent != null)
        {
            description = String.format("%s->%s", parent.describe(), description);
        }

        return description;
    }

}
