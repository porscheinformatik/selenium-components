package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A component representing a TD element.
 *
 * @author ham
 */
public class TdComponent extends AbstractTableCellComponent
{

    public TdComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("td"));
    }

    public TdComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

}
