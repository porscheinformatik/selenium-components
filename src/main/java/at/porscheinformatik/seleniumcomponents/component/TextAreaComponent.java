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
    public static TextAreaComponent within(SeleniumComponent parent)
    {
        return new TextAreaComponent(parent, WebElementSelector.selectByTagName("textarea"));
    }

    public static TextAreaComponent byName(SeleniumComponent parent, String name)
    {
        return new TextAreaComponent(parent, WebElementSelector.selectByName(name));
    }

    public static TextAreaComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new TextAreaComponent(parent, WebElementSelector.selectByTestId(testId));
    }

    public static TextAreaComponent byLabel(SeleniumComponent parent, String label)
    {
        return new TextAreaComponent(parent,
            WebElementSelector.selectByXPath(String.format(".//textarea[@id=//label[contains(.,'%s')]/@for]", label)));
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
