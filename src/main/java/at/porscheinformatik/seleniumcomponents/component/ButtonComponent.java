
package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author Daniel Furtlehner
 */
public class ButtonComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent {

    public static ButtonComponent within(SeleniumComponent parent) {
        return new ButtonComponent(parent, WebElementSelector.selectByTagName("button"));
    }

    public static ButtonComponent byTestId(SeleniumComponent parent, String testId) {
        return new ButtonComponent(parent, WebElementSelector.selectByTestId("button", testId));
    }

    public static ButtonComponent byText(SeleniumComponent parent, String partialText) {
        return new ButtonComponent(parent, WebElementSelector.selectByText("button", partialText));
    }

    public static ButtonComponent byClassName(SeleniumComponent parent, String className) {
        return new ButtonComponent(parent, WebElementSelector.selectByClassName("button", className));
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String)} instead
     */
    @Deprecated(forRemoval = true)
    public static ButtonComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey) {
        return new ButtonComponent(parent, WebElementSelector.selectBySeleniumKey("button", seleniumKey));
    }

    public static ButtonComponent byLabel(SeleniumComponent parent, String label) {
        return new ButtonComponent(parent, WebElementSelector.selectByText("button", label));
    }

    public static ButtonComponent byType(SeleniumComponent parent, String type) {
        return new ButtonComponent(parent, WebElementSelector.selectByAttribute("button", "type", type));
    }

    public ButtonComponent(SeleniumComponent parent) {
        super(parent, WebElementSelector.selectByTagName("button"));
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String)} instead
     */
    @Deprecated(forRemoval = true)
    public ButtonComponent(SeleniumComponent parent, String seleniumKey) {
        this(parent, WebElementSelector.selectByTestIdOrSeleniumKey("button", seleniumKey));
    }

    public ButtonComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public String getLabel() {
        return getText();
    }
}
