package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumContainer;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * Represents a TR element
 *
 * @author ham
 */
public class TableRowComponent extends AbstractSeleniumContainer<TableCellComponent> {

    public TableRowComponent(SeleniumComponent parent) {
        this(parent, WebElementSelector.selectByTagName("tr"));
    }

    public TableRowComponent(SeleniumComponent parent, WebElementSelector selector) {
        this(parent, selector, WebElementSelector.selectByCss("th, td"), TableCellComponent::new);
    }

    public TableRowComponent(
        SeleniumComponent parent,
        WebElementSelector selector,
        WebElementSelector childSelector,
        SeleniumComponentFactory<TableCellComponent> childFactory
    ) {
        super(parent, selector, childSelector, childFactory);
    }
}
