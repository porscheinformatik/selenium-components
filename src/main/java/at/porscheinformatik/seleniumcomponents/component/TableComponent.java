package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A default table element, that may contain a thead, a tbody and a tfoot
 *
 * @author ham
 * @author Daniel Furtlehner
 */
public class TableComponent extends AbstractSeleniumComponent
{

    private final TableContentComponent headComponent = new TableContentComponent(this, selectByTagName("thead"));
    private final TableContentComponent bodyComponent = new TableContentComponent(this, selectByTagName("tbody"));
    private final TableContentComponent footComponent = new TableContentComponent(this, selectByTagName("tfoot"));

    public TableComponent(SeleniumComponent parent)
    {
        super(parent, selectByTagName("table"));
    }

    public TableComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public TableContentComponent getHeadComponent()
    {
        return headComponent;
    }

    public TableContentComponent getBodyComponent()
    {
        return bodyComponent;
    }

    public TableContentComponent getFootComponent()
    {
        return footComponent;
    }

}
