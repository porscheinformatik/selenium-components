package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A component representing a TH element.
 *
 * @author ham
 */
public class ThComponent extends AbstractTableCellComponent
{

    public ThComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("th"));
    }

    public ThComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

}
