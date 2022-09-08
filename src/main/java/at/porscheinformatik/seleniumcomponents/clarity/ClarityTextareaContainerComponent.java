package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.TextAreaComponent;

public class ClarityTextareaContainerComponent extends ClarityFormControlContainer
{
    public final TextAreaComponent textarea = new TextAreaComponent(this);

    // ---

    public static ClarityTextareaContainerComponent within(SeleniumComponent parent)
    {
        return new ClarityTextareaContainerComponent(parent, selectByTagName("clr-textarea-container"));
    }

    public static ClarityTextareaContainerComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey)
    {
        return new ClarityTextareaContainerComponent(parent,
            selectBySeleniumKey("clr-textarea-container", seleniumKey));
    }

    // ---

    public ClarityTextareaContainerComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }
}
