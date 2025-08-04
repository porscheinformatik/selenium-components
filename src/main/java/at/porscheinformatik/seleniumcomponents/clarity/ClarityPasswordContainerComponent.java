package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.InputComponent;
import org.openqa.selenium.Keys;

/**
 * @author scar
 */
public class ClarityPasswordContainerComponent extends ClarityFormControlContainer {

    public static ClarityPasswordContainerComponent within(SeleniumComponent parent) {
        return new ClarityPasswordContainerComponent(
            parent,
            WebElementSelector.selectByTagName("clr-password-container")
        );
    }

    public static ClarityPasswordContainerComponent byTestId(SeleniumComponent parent, String testId) {
        return new ClarityPasswordContainerComponent(
            parent,
            WebElementSelector.selectByTestId("clr-password-container", testId)
        );
    }

    public static ClarityPasswordContainerComponent byTestIdOfInput(SeleniumComponent parent, String testId) {
        String xpath = String.format(".//clr-password-container[.//input[@data-testid='%s']]", testId);

        return new ClarityPasswordContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityPasswordContainerComponent byName(SeleniumComponent parent, String name) {
        String xpath = String.format(".//clr-password-container[.//input[@name='%s']]", name);

        return new ClarityPasswordContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityPasswordContainerComponent byFormControlName(
        SeleniumComponent parent,
        String formControlName
    ) {
        String xpath = String.format(".//clr-password-container[.//input[@formcontrolname='%s']]", formControlName);

        return new ClarityPasswordContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityPasswordContainerComponent byLabel(SeleniumComponent parent, String label) {
        String xpath = String.format(
            ".//clr-password-container[.//label[contains(@class, 'clr-control-label') and contains(.,'%s')]]",
            label
        );

        return new ClarityPasswordContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    // --- //

    private final InputComponent input = new InputComponent(this);

    public ClarityPasswordContainerComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public String getValue() {
        return input.getValue();
    }

    public void setValue(String value) {
        input.enter(value);
    }

    /**
     * Clear the input control by selecting the entire text and then backspacing. This is necessary as otherwise the
     * form control won't be marked as dirty.
     */
    public void clear() {
        input.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.BACK_SPACE);
    }

    /**
     * @return true if the input control has been disabled, and false otherwise
     */
    public boolean isDisabled() {
        return input.isDisabled();
    }
}
