package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ClickableSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author cet
 */
public class OptionComponent extends AbstractSeleniumComponent implements ClickableSeleniumComponent
{

    public OptionComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public boolean isSelected()
    {
        return element().isSelected();
    }

    public String getValue()
    {
        return getAttribute("value");
    }

    /**
     * @return Angular specific value
     * @deprecated 1. this value is not available if compiled for productive environments and 2. this is Angular
     *             specific while the OptionComponent must remain HTML specific.
     */
    @Deprecated
    public String getNgSelectValue()
    {
        return getAttribute("ng-reflect-ng-value");
    }

    public String getLabel()
    {
        return getText();
    }

}
