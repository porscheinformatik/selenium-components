package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumUtils;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.ButtonComponent;

public class ClarityTabLinkComponent extends AbstractSeleniumComponent
{

    private final ButtonComponent button =
        new ButtonComponent(this, WebElementSelector.selectByCss("button[role='tab']"));

    public ClarityTabLinkComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getLabel()
    {
        return button.getLabel();
    }

    public void click()
    {
        button.click();
    }

    public void activate()
    {
        if (!isActive())
        {
            click();
        }
    }

    public boolean isActive()
    {
        return SeleniumUtils.containsClassName(button, "active");
    }

}
