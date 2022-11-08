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

    public static ClarityInputContainerComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey)
    {
        return new ClarityInputContainerComponent(parent, selectBySeleniumKey("clr-input-container", seleniumKey));
    }

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
        String xpath = String
            .format(".//clr-input-container[.//label[contains(@class, 'clr-control-label') and contains(text(),'%s')]]",
                label);

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
}
