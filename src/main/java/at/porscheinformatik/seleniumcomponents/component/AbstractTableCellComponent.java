package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumException;
import at.porscheinformatik.seleniumcomponents.SeleniumUtils;
import at.porscheinformatik.seleniumcomponents.Utils;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A component representing a TD or TH element.
 *
 * @author ham
 */
public abstract class AbstractTableCellComponent extends AbstractSeleniumComponent
{

    public AbstractTableCellComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public int getColSpan()
    {
        String colspan = getAttribute("colspan");

        if (Utils.isEmpty(colspan))
        {
            return 1;
        }

        try
        {
            return Integer.parseInt(colspan);
        }
        catch (NumberFormatException e)
        {
            throw new SeleniumException("Failed to parse colspan: " + colspan, e);
        }
    }

    public String getText()
    {
        return SeleniumUtils.getText(this);
    }
}
