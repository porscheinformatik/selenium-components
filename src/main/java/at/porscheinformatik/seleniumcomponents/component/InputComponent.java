package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.EditableSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * An input field.
 *
 * @author cet
 */
public class InputComponent extends AbstractSeleniumComponent implements EditableSeleniumComponent
{

    public static InputComponent byName(SeleniumComponent parent, String name)
    {
        return new InputComponent(parent, WebElementSelector.selectByName(name));
    }

    public static InputComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey)
    {
        return new InputComponent(parent, WebElementSelector.selectBySeleniumKey(seleniumKey));
    }

    public InputComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("input"));
    }

    public InputComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    /**
     * Clears the input field an types all specified values.
     *
     * @param values one or more values to enter
     */
    public void enter(CharSequence... values)
    {
        clear();
        sendKeys(values);
    }

    public String getValue()
    {
        return getAttribute("value");
    }

    public String getPlaceholder()
    {
        return getAttribute("placeholder");
    }

}
