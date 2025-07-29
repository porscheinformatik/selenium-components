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
public class CheckboxComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent {

    public static CheckboxComponent within(SeleniumComponent parent) {
        return new CheckboxComponent(parent, selectByCss("input[type=\"checkbox\"]"));
    }

    public static CheckboxComponent byTestId(SeleniumComponent parent, String testId) {
        return new CheckboxComponent(parent, selectByTestId("input[type=\"checkbox\"]", testId));
    }

    public static CheckboxComponent byText(SeleniumComponent parent, String partialText) {
        return new CheckboxComponent(parent, selectByText("input[type=\"checkbox\"]", partialText));
    }

    public static CheckboxComponent byLabel(SeleniumComponent parent, String partialText) {
        String xpath = String.format(".//label[contains(., '%s')]/input[@type='checkbox']", partialText);

        return new CheckboxComponent(parent, selectByXPath(xpath));
    }

    public static CheckboxComponent byValue(SeleniumComponent parent, String value) {
        String xpath = String.format(".//input[@type='checkbox' and @value='%s']", value);

        return new CheckboxComponent(parent, selectByXPath(xpath));
    }

    /**
     * Creates the component.
     *
     * @param parent the parent
     * @param selector the selector for the element
     */
    public CheckboxComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public CheckboxComponent(SeleniumComponent parent) {
        this(parent, selectByCss("input[type=\"checkbox\"]"));
    }

    public String getId() {
        return getAttribute("id");
    }

    public String getName() {
        return getAttribute("name");
    }
}
