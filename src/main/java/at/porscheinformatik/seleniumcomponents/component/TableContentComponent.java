package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumContainer;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * Represents table content like a TBODY element with {@link TableRowComponent}s as children.
 *
 * @author ham
 */
public class TableContentComponent extends AbstractSeleniumContainer<TableRowComponent>
{

    public TableContentComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        this(parent, selector, WebElementSelector.selectByTagName("tr"), TableRowComponent::new);
    }

    public TableContentComponent(SeleniumComponent parent, WebElementSelector selector,
        WebElementSelector childSelector, SeleniumComponentFactory<TableRowComponent> childFactory)
    {
        super(parent, selector, childSelector, childFactory);
    }

}
