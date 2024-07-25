/**
 *
 */
package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author Daniel Furtlehner
 */
public class RadioComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{
    public static CheckboxComponent within(SeleniumComponent parent)
    {
        return new CheckboxComponent(parent, selectByCss("input[type=\"radio\"]"));
    }

    public static CheckboxComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new CheckboxComponent(parent, selectByTestId("input[type=\"radio\"]", testId));
    }

    public static CheckboxComponent byText(SeleniumComponent parent, String partialText)
    {
        return new CheckboxComponent(parent, selectByText("input[type=\"radio\"]", partialText));
    }

    public static CheckboxComponent byLabel(SeleniumComponent parent, String partialText)
    {
        String xpath = String.format(".//label[contains(., '%s')]/input[@type='radio']", partialText);

        return new CheckboxComponent(parent, selectByXPath(xpath));
    }

    public static CheckboxComponent byValue(SeleniumComponent parent, String value)
    {
        String xpath = String.format(".//input[@type='checkbox' and @value='%s']", value);

        return new CheckboxComponent(parent, selectByXPath(xpath));
    }

    /**
     * Creates the component.
     *
     * @param parent the parent
     * @param selector the selector for the element
     */
    public RadioComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public RadioComponent(SeleniumComponent parent)
    {
        this(parent, selectByCss("input[type=\"radio\"]"));
    }
}
