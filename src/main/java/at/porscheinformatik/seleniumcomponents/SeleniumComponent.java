package at.porscheinformatik.seleniumcomponents;

/**
 * The base class for components accessible by the Selenium tests. A {@link SeleniumComponent} usually represents a node
 * in the DOM.
 *
 * @author ham
 */
public interface SeleniumComponent extends WebElementContainer
{

    /**
     * Returns the parent of the component.
     *
     * @return the parent, may be null
     */
    SeleniumComponent parent();

    /**
     * Returns the root of the parent. If the component is a root, it must implement this method and return itself.
     *
     * @return the top-most parent, never null
     * @deprecated use {@link SeleniumComponentUtils#root(SeleniumComponent)}
     */
    @Deprecated
    default SeleniumComponent root()
    {
        return SeleniumComponentUtils.root(this);
    }

    /**
     * Returns the {@link SeleniumContext} of the parent. If this component is root, it must implement this method and
     * return a valid context.
     *
     * @return the context, never null
     */
    default SeleniumContext context()
    {
        return parent().context();
    }

    /**
     * Returns a description of this component, usually as selector and content tuple.
     *
     * @return a description of this component
     */
    String describe();

    /**
     * Returns true if there is a descendant
     *
     * @param selector the selector
     * @return true if one component was found
     * @deprecated it is considered a bad habit to directly access the elements
     */
    @Deprecated
    default boolean containsDescendant(WebElementSelector selector)
    {
        return !selector.findAll(this).isEmpty();
    }

}
