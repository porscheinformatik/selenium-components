package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * Represents a TR element
 *
 * @author ham
 */
public class TrComponent extends AbstractSeleniumComponent
{

    private final SeleniumComponentListFactory<TdComponent> cells =
        SeleniumComponentListFactory.of(this, WebElementSelector.selectByCss("th, td"), TdComponent::new);

    public TrComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("tr"));
    }

    public TrComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public SeleniumComponentList<TdComponent> getCells()
    {
        return cells.findAll();
    }
}
