package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * An input field.
 *
 * @author cet
 */
public class TextAreaComponent extends InputComponent
{
    public static InputComponent within(SeleniumComponent parent)
    {
        return new InputComponent(parent, WebElementSelector.selectByTagName("textarea"));
    }

    public static InputComponent byName(SeleniumComponent parent, String name)
    {
        return new InputComponent(parent, WebElementSelector.selectByName(name));
    }

    public static InputComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new InputComponent(parent, WebElementSelector.selectByTestId(testId));
    }

    public static InputComponent byLabel(SeleniumComponent parent, String label)
    {
        return new InputComponent(parent, WebElementSelector.selectByXPath(
            String.format(".//textarea[@id=//label[contains(text(),'%s')]/@for]", label)));
    }

    public TextAreaComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("textarea"));
    }

    public TextAreaComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

}
