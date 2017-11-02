package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A default table element, that may contain a thead, a tbody and a tfoot
 *
 * @author ham
 *
 */
public class TableComponent extends AbstractSeleniumComponent
{

    private final TableContentComponent headComponent = new TableContentComponent(this, WebElementSelector.selectByTagName("thead"));
    private final TableContentComponent bodyComponent = new TableContentComponent(this, WebElementSelector.selectByTagName("tbody"));
    private final TableContentComponent footComponent = new TableContentComponent(this, WebElementSelector.selectByTagName("tfoot"));

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
