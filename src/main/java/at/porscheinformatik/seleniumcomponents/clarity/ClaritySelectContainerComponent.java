package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;
import at.porscheinformatik.seleniumcomponents.component.SelectComponent;

public class ClaritySelectContainerComponent extends AbstractSeleniumComponent
{
    public static ClaritySelectContainerComponent within(SeleniumComponent parent)
    {
        return new ClaritySelectContainerComponent(parent, WebElementSelector.selectByTagName("clr-select-container"));
    }

    public static ClaritySelectContainerComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey)
    {
        return new ClaritySelectContainerComponent(parent,
            WebElementSelector.selectBySeleniumKey("clr-select-container", seleniumKey));
    }

    public final HtmlComponent label = new HtmlComponent(this, WebElementSelector.selectByTagName("label"));
    public final SelectComponent select = new SelectComponent(this);

    public ClaritySelectContainerComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getLabel()
    {
        return label.getText();
    }

    public String getSelectedLabel()
    {
        return select.getSelectedOption().getLabel();
    }

    public String getSelectedValue()
    {
        return select.getSelectedOption().getValue();
    }

    public void selectByValue(String value)
    {
        select.selectByValue(value);
    }

    public void selectByValueContains(String value)
    {
        select.selectByValueContains(value);
    }
}
