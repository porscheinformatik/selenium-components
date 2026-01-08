package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.TextAreaComponent;
import org.openqa.selenium.Keys;

public class ClarityTextareaContainerComponent extends ClarityFormControlContainer {

    private static final String TAG_NAME = "clr-textarea-container";

    public static ClarityTextareaContainerComponent within(SeleniumComponent parent) {
        return new ClarityTextareaContainerComponent(parent, selectByTagName(TAG_NAME));
    }

    public static ClarityTextareaContainerComponent byTestId(SeleniumComponent parent, String testId) {
        return new ClarityTextareaContainerComponent(parent, selectByTestId(TAG_NAME, testId));
    }

    public static ClarityTextareaContainerComponent byText(SeleniumComponent parent, String partialText) {
        return new ClarityTextareaContainerComponent(parent, selectByText(TAG_NAME, partialText));
    }

    public static ClarityTextareaContainerComponent byTestIdOfTextarea(SeleniumComponent parent, String testId) {
        String xpath = String.format(".//clr-textarea-container[.//textarea[@data-testid='%s']]", testId);

        return new ClarityTextareaContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityTextareaContainerComponent byFormControlName(
        SeleniumComponent parent,
        String formControlName
    ) {
        String xpath = String.format(".//clr-textarea-container[.//textarea[@formcontrolname='%s']]", formControlName);

        return new ClarityTextareaContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityTextareaContainerComponent byLabel(SeleniumComponent parent, String label) {
        String xpath = String.format(
            ".//clr-textarea-container[.//label[contains(@class, 'clr-control-label') and contains(.,'%s')]]",
            label
        );

        return new ClarityTextareaContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public final TextAreaComponent textarea = new TextAreaComponent(this);

    public ClarityTextareaContainerComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public String getValue() {
        return textarea.getValue();
    }

    public void setValue(String value) {
        textarea.enter(value);
    }

    public void appendValue(String value) {
        textarea.sendKeys(value);
    }

    public void clear() {
        // Mark the whole textarea text and delete it
        textarea.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.BACK_SPACE);
    }

    /**
     * Use this method to check whether or not you can write a new value to the textarea control.
     *
     * @return true if the textarea control has been disabled, and true otherwise
     */
    public boolean isDisabled() {
        return textarea.isDisabled();
    }
}
