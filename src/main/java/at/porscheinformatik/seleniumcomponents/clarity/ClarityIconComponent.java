package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

public class ClarityIconComponent extends AbstractSeleniumComponent
{
    public static ClarityIconComponent in(SeleniumComponent parent)
    {
        return new ClarityIconComponent(parent, WebElementSelector.selectByTagName("cds-icon"));
    }

    public static ClarityIconComponent withShape(SeleniumComponent parent, String shape)
    {
        return new ClarityIconComponent(parent,
            WebElementSelector.selectByAttributeContains("cds-icon", "shape", shape));
    }

    public ClarityIconComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }
}
