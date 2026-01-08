package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author Daniel Furtlehner
 */
public class ButtonComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent {

    private static final String TAG_NAME = "button";

    public static ButtonComponent within(SeleniumComponent parent) {
        return new ButtonComponent(parent, WebElementSelector.selectByTagName(TAG_NAME));
    }

    public static ButtonComponent byTestId(SeleniumComponent parent, String testId) {
        return new ButtonComponent(parent, WebElementSelector.selectByTestId(TAG_NAME, testId));
    }

    public static ButtonComponent byText(SeleniumComponent parent, String partialText) {
        return new ButtonComponent(parent, WebElementSelector.selectByText(TAG_NAME, partialText));
    }

    public static ButtonComponent byClassName(SeleniumComponent parent, String className) {
        return new ButtonComponent(parent, WebElementSelector.selectByClassName(TAG_NAME, className));
    }

    public static ButtonComponent byLabel(SeleniumComponent parent, String label) {
        return new ButtonComponent(parent, WebElementSelector.selectByText(TAG_NAME, label));
    }

    public static ButtonComponent byType(SeleniumComponent parent, String type) {
        return new ButtonComponent(parent, WebElementSelector.selectByAttribute(TAG_NAME, "type", type));
    }

    public ButtonComponent(SeleniumComponent parent) {
        super(parent, WebElementSelector.selectByTagName(TAG_NAME));
    }

    public ButtonComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public String getLabel() {
        return getText();
    }
}
