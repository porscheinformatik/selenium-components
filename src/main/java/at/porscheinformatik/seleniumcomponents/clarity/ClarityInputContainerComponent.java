package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import org.openqa.selenium.Keys;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.InputComponent;

public class ClarityInputContainerComponent extends ClarityFormControlContainer
{
    public static ClarityInputContainerComponent within(SeleniumComponent parent)
    {
        return new ClarityInputContainerComponent(parent, selectByTagName("clr-input-container"));
    }

    public static ClarityInputContainerComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new ClarityInputContainerComponent(parent, selectByTestId("clr-input-container", testId));
    }

    public static ClarityInputContainerComponent byText(SeleniumComponent parent, String partialText)
    {
        return new ClarityInputContainerComponent(parent, selectByText("clr-input-container", partialText));
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String)} instead
     */
    @Deprecated(forRemoval = true)
    public static ClarityInputContainerComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey)
    {
        return new ClarityInputContainerComponent(parent, selectBySeleniumKey("clr-input-container", seleniumKey));
    }

    public static ClarityInputContainerComponent byTestIdOfInput(SeleniumComponent parent, String testId)
    {
        String xpath = String.format(".//clr-input-container[.//input[@data-testid='%s']]", testId);

        return new ClarityInputContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    /**
     * @deprecated Use {@link #byTestIdOfInput(SeleniumComponent, String)} instead
     */
    @Deprecated(forRemoval = true)
    public static ClarityInputContainerComponent bySelenumKeyOfInput(SeleniumComponent parent, String seleniumKey)
    {
        String xpath = String.format(".//clr-input-container[.//input[@selenium-key='%s']]", seleniumKey);

        return new ClarityInputContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityInputContainerComponent byFormControlName(SeleniumComponent parent, String formControlName)
    {
        String xpath = String.format(".//clr-input-container[.//input[@formcontrolname='%s']]", formControlName);

        return new ClarityInputContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityInputContainerComponent byLabel(SeleniumComponent parent, String label)
    {
        String xpath = String.format(
            ".//clr-input-container[.//label[contains(@class, 'clr-control-label') and contains(.,'%s')]]", label);

        return new ClarityInputContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    private final InputComponent input = new InputComponent(this);

    public ClarityInputContainerComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getValue()
    {
        return input.getValue();
    }

    public void setValue(String value)
    {
        input.enter(value);
    }

    public void appendValue(String value)
    {
        input.sendKeys(value);
    }

    public void clear()
    {
        // Mark the whole input text and delete it
        input.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.BACK_SPACE);
    }

    /**
     * Use this method to check whether or not you can write a new value to the input control.
     *
     * @return true if the input control has been disabled, and true otherwise
     */
    public boolean isDisabled()
    {
        return input.isDisabled();
    }

}
