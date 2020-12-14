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

    public String getLabel()
    {
        return getText();
    }

}
