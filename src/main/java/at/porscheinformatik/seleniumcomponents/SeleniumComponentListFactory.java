package at.porscheinformatik.seleniumcomponents;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Creates {@link SeleniumComponentList}s
 *
 * @author ham
 * @param <CHILD_TYPE> the type of the child components
 */
public class SeleniumComponentListFactory<CHILD_TYPE extends SeleniumComponent>
{

    /**
     * Creates a {@link SeleniumComponentListFactory}, that uses the specified component as template.
     *
     * @param <CHILD_TYPE> the type of the child
     * @param template the template
     * @return a list factory
     */
    public static <CHILD_TYPE extends SeleniumComponent & SeleniumComponentTemplate<CHILD_TYPE>> SeleniumComponentListFactory<CHILD_TYPE> of(
        CHILD_TYPE template)
    {
        return of(template.parent(), template.selector(), template::create);
    }

    /**
     * Creates a {@link SeleniumComponentListFactory}, that uses the specified component as template.
     *
     * @param <CHILD_TYPE> the type of the child
     * @param parent the parent
     * @param template the template
     * @return a list factory
     */
    public static <CHILD_TYPE extends SeleniumComponent> SeleniumComponentListFactory<CHILD_TYPE> of(
        SeleniumComponent parent, SeleniumComponentTemplate<CHILD_TYPE> template)
    {
        return of(parent, template.selector(), template::create);
    }

    /**
     * Creates a {@link SeleniumComponentListFactory}, that uses the specified selector and factory to create the child
     * instances.
     *
     * @param <CHILD_TYPE> the type of the child components
     * @param parent the parent
     * @param childSelector the selector for the children
     * @param childFactory the factory for the children
     * @return the factory
     */
    public static <CHILD_TYPE extends SeleniumComponent> SeleniumComponentListFactory<CHILD_TYPE> of(
        SeleniumComponent parent, WebElementSelector childSelector, SeleniumComponentFactory<CHILD_TYPE> childFactory)
    {
        return new SeleniumComponentListFactory<>(parent, childSelector, childFactory);
    }

    private final SeleniumComponent parent;
    private final WebElementSelector childSelector;
    private final SeleniumComponentFactory<CHILD_TYPE> childFactory;

    /**
     * Creates a new factory
     *
     * @param parent the parent component
     * @param childSelector the selector for the child components
     * @param childFactory the factory for the child components
     */
    public SeleniumComponentListFactory(SeleniumComponent parent, WebElementSelector childSelector,
        SeleniumComponentFactory<CHILD_TYPE> childFactory)
    {
        super();

        this.parent = parent;
        this.childSelector = childSelector;
        this.childFactory = childFactory;
    }

    /**
     * Returns the first child, that matches the predicate. This method is just a wrapper that uses the
     * {@link #findAll()} and {@link SeleniumComponentList#find(Predicate)} method. Please prefer to cache the result of
     * {@link #findAll()} instead of calling {{@link #find(Predicate)} multiple times.
     *
     * @param predicate the predicate
     * @return the child, null if not found
     */
    public CHILD_TYPE find(Predicate<CHILD_TYPE> predicate)
    {
        return findAll().find(predicate);
    }

    /**
     * Returns a list of all components that match the selector.
     *
     * @return the list, never null
     */
    public SeleniumComponentList<CHILD_TYPE> findAll()
    {
        List<CHILD_TYPE> elements = childSelector
            .findAll(parent.element())
            .stream()
            .map(element -> childFactory.create(parent,
                WebElementSelector.selectElement(childSelector.toString(), element)))
            .collect(Collectors.toList());

        return new SeleniumComponentList<>(elements);
    }

}
