package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.TextAreaComponent;

public class ClarityTextareaContainerComponent extends ClarityFormControlContainer
{

    public static ClarityTextareaContainerComponent within(SeleniumComponent parent)
    {
        return new ClarityTextareaContainerComponent(parent, selectByTagName("clr-textarea-container"));
    }

    public static ClarityTextareaContainerComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new ClarityTextareaContainerComponent(parent, selectByTestId("clr-textarea-container", testId));
    }

    public static ClarityTextareaContainerComponent byText(SeleniumComponent parent, String partialText)
    {
        return new ClarityTextareaContainerComponent(parent, selectByText("clr-textarea-container", partialText));
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String)} instead
     */
    @Deprecated
    public static ClarityTextareaContainerComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey)
    {
        return new ClarityTextareaContainerComponent(parent,
            selectBySeleniumKey("clr-textarea-container", seleniumKey));
    }

    public final TextAreaComponent textarea = new TextAreaComponent(this);

    public ClarityTextareaContainerComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }
}
