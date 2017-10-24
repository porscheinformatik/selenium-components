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

    public CHILD_TYPE find(Predicate<CHILD_TYPE> predicate)
    {
        return findAll().find(predicate);
    }

    public SeleniumComponentList<CHILD_TYPE> findAll()
    {
        List<CHILD_TYPE> elements = childSelector
            .findAll(parent.element())
            .stream()
            .map(element -> childFactory.apply(parent, WebElementSelector.selectElement(element)))
            .collect(Collectors.toList());

        return new SeleniumComponentList<>(elements);
    }

}
