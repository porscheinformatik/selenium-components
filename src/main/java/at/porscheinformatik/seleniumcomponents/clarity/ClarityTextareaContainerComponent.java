package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import org.openqa.selenium.Keys;

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
    @Deprecated(forRemoval = true)
    public static ClarityTextareaContainerComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey)
    {
        return new ClarityTextareaContainerComponent(parent,
            selectBySeleniumKey("clr-textarea-container", seleniumKey));
    }

    public static ClarityTextareaContainerComponent byTestIdOfTextarea(SeleniumComponent parent, String testId)
    {
        String xpath = String.format(".//clr-textarea-container[.//textarea[@data-testid='%s']]", testId);

        return new ClarityTextareaContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    /**
     * @deprecated Use {@link #byTestIdOfTextarea(SeleniumComponent, String)} instead
     */
    @Deprecated(forRemoval = true)
    public static ClarityTextareaContainerComponent bySelenumKeyOfTextarea(SeleniumComponent parent, String seleniumKey)
    {
        String xpath = String.format(".//clr-textarea-container[.//textarea[@selenium-key='%s']]", seleniumKey);

        return new ClarityTextareaContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityTextareaContainerComponent byFormControlName(SeleniumComponent parent, String formControlName)
    {
        String xpath = String.format(".//clr-textarea-container[.//textarea[@formcontrolname='%s']]", formControlName);

        return new ClarityTextareaContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityTextareaContainerComponent byLabel(SeleniumComponent parent, String label)
    {
        String xpath = String.format(
            ".//clr-textarea-container[.//label[contains(@class, 'clr-control-label') and contains(.,'%s')]]", label);

        return new ClarityTextareaContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public final TextAreaComponent textarea = new TextAreaComponent(this);

    public ClarityTextareaContainerComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getValue()
    {
        return textarea.getValue();
    }

    public void setValue(String value)
    {
        textarea.enter(value);
    }

    public void appendValue(String value)
    {
        textarea.sendKeys(value);
    }

    public void clear()
    {
        // Mark the whole textarea text and delete it
        textarea.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.BACK_SPACE);
    }

    /**
     * Use this method to check whether or not you can write a new value to the textarea control.
     *
     * @return true if the textarea control has been disabled, and true otherwise
     */
    public boolean isDisabled()
    {
        return textarea.isDisabled();
    }

}
