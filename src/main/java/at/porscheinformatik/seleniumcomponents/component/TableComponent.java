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

    private final TheadComponent headComponent = new TheadComponent(this);
    private final TbodyComponent bodyComponent = new TbodyComponent(this);
    private final TfootComponent footComponent = new TfootComponent(this);

    public TableComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public TheadComponent getHeadComponent()
    {
        return headComponent;
    }

    public TbodyComponent getBodyComponent()
    {
        return bodyComponent;
    }

    public TfootComponent getFootComponent()
    {
        return footComponent;
    }

}
