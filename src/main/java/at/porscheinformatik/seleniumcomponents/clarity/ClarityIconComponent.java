package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

public class ClarityIconComponent extends AbstractSeleniumComponent
{
    public static ClarityIconComponent withShape(SeleniumComponent parent, String shape)
    {
        return new ClarityIconComponent(parent, selectByAttributeContains("cds-icon", "shape", shape));
    }

    // ---

    public ClarityIconComponent(SeleniumComponent parent)
    {
        this(parent, selectByTagName("cds-icon"));
    }

    public ClarityIconComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    // ---

    public String getShape()
    {
        return this.getAttribute("shape");
    }
}
