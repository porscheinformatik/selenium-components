package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.SelectComponent;

public class ClaritySelectContainerComponent extends ClarityFormControlContainer {

    private static final String TAG_NAME = "clr-select-container";

    public static ClaritySelectContainerComponent within(SeleniumComponent parent) {
        return new ClaritySelectContainerComponent(parent, WebElementSelector.selectByTagName(TAG_NAME));
    }

    public static ClaritySelectContainerComponent byTestId(SeleniumComponent parent, String testId) {
        return new ClaritySelectContainerComponent(parent, WebElementSelector.selectByTestId(TAG_NAME, testId));
    }

    public static ClaritySelectContainerComponent byText(SeleniumComponent parent, String partialText) {
        return new ClaritySelectContainerComponent(parent, WebElementSelector.selectByText(TAG_NAME, partialText));
    }

    public static ClaritySelectContainerComponent byFormControlName(SeleniumComponent parent, String formControlName) {
        String xpath = String.format(".//clr-select-container[.//select[@formcontrolname='%s']]", formControlName);
        return new ClaritySelectContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClaritySelectContainerComponent byLabel(SeleniumComponent parent, String label) {
        String xpath = String.format(
            ".//clr-input-container[.//label[contains(@class, 'clr-control-label') and contains(.,'%s')]]",
            label
        );
        return new ClaritySelectContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public final SelectComponent select = new SelectComponent(this);

    public ClaritySelectContainerComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public String getSelectedLabel() {
        return select.getSelectedOption().getLabel();
    }

    public String getSelectedValue() {
        return select.getSelectedOption().getValue();
    }

    public void selectByValue(String value) {
        select.selectByValue(value);
    }

    public void selectByValueContains(String value) {
        select.selectByValueContains(value);
    }

    public void selectByLabel(String label) {
        select.selectByLabel(label);
    }

    public void selectByLabelContains(String label) {
        select.selectByLabelContains(label);
    }
}
