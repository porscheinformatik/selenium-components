package at.porscheinformatik.seleniumcomponents;

import java.util.function.Predicate;

/**
 * An abstract base implementation of a {@link SeleniumContainer}.
 *
 * @author ham
 * @param <AnyChildComponent> the type of the child components
 */
public abstract class AbstractSeleniumContainer<AnyChildComponent extends SeleniumComponent>
    extends AbstractSeleniumComponent implements SeleniumContainer<AnyChildComponent>
{

    private final SeleniumComponentListFactory<AnyChildComponent> childListFactory;

    protected WebElementSelector childSelector;
    protected SeleniumComponentFactory<AnyChildComponent> childFactory;

    protected AbstractSeleniumContainer(SeleniumComponent parent, WebElementSelector selector,
        WebElementSelector childSelector, SeleniumComponentFactory<AnyChildComponent> childFactory)
    {
        super(parent, selector);

        this.childSelector = childSelector;
        this.childFactory = childFactory;

        childListFactory = SeleniumComponentListFactory.of(this, childSelector, childFactory);
    }

    @Override
    public SeleniumComponentList<AnyChildComponent> findAllChilds()
    {
        return childListFactory.findAll();
    }

    protected AnyChildComponent findChild(Predicate<AnyChildComponent> predicate)
    {
        return childListFactory.find(predicate);
    }
}
