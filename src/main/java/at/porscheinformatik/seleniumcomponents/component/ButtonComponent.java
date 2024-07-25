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
public class ButtonComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{
    public static ButtonComponent within(SeleniumComponent parent)
    {
        return new ButtonComponent(parent, selectByTagName("button"));
    }

    public static ButtonComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new ButtonComponent(parent, selectByTestId("button", testId));
    }

    public static ButtonComponent byText(SeleniumComponent parent, String partialText)
    {
        return new ButtonComponent(parent, selectByText("button", partialText));
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String)} instead
     */
    @Deprecated(forRemoval = true)
    public static ButtonComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey)
    {
        return new ButtonComponent(parent, selectBySeleniumKey("button", seleniumKey));
    }

    public static ButtonComponent byLabel(SeleniumComponent parent, String label)
    {
        String xpath = String.format(".//button[contains(text(),'%s')]", label);

        return new ButtonComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public ButtonComponent(SeleniumComponent parent)
    {
        super(parent, selectByTagName("button"));
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String)} instead
     */
    @Deprecated(forRemoval = true)
    public ButtonComponent(SeleniumComponent parent, String seleniumKey)
    {
        this(parent, selectByTestIdOrSeleniumKey("button", seleniumKey));
    }

    public ButtonComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getLabel()
    {
        return getText();
    }
}
