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
