package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.selectByTagName;
import static at.porscheinformatik.seleniumcomponents.WebElementSelector.selectByTestId;
import static at.porscheinformatik.seleniumcomponents.WebElementSelector.selectByText;

import org.openqa.selenium.Keys;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.InputComponent;

public class ClarityNumberInputContainerComponent extends ClarityFormControlContainer
{
    private static final String CLR_NUMBER_INPUT_CONTAINER = "clr-number-input-container";

    public static ClarityNumberInputContainerComponent within(SeleniumComponent parent)
    {
        return new ClarityNumberInputContainerComponent(parent, selectByTagName(CLR_NUMBER_INPUT_CONTAINER));
    }

    public static ClarityNumberInputContainerComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new ClarityNumberInputContainerComponent(parent, selectByTestId(CLR_NUMBER_INPUT_CONTAINER, testId));
    }

    public static ClarityNumberInputContainerComponent byText(SeleniumComponent parent, String partialText)
    {
        return new ClarityNumberInputContainerComponent(parent, selectByText(CLR_NUMBER_INPUT_CONTAINER, partialText));
    }

    public static ClarityNumberInputContainerComponent byTestIdOfInput(SeleniumComponent parent, String testId)
    {
        String xpath = String.format(".//clr-number-input-container[.//input[@data-testid='%s']]", testId);

        return new ClarityNumberInputContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityNumberInputContainerComponent byFormControlName(SeleniumComponent parent, String formControlName)
    {
        String xpath = String.format(".//clr-number-input-container[.//input[@formcontrolname='%s']]", formControlName);

        return new ClarityNumberInputContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityNumberInputContainerComponent byLabel(SeleniumComponent parent, String label)
    {
        String xpath = String.format(
            ".//clr-number-input-container[.//label[contains(@class, 'clr-control-label') and contains(.,'%s')]]", label);

        return new ClarityNumberInputContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    private final InputComponent input = new InputComponent(this);

    public ClarityNumberInputContainerComponent(SeleniumComponent parent, WebElementSelector selector)
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
