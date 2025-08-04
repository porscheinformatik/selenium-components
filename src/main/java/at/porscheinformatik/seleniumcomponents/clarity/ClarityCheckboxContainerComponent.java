/**
 *
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumUtils;
import at.porscheinformatik.seleniumcomponents.Utils;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.CheckboxComponent;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

/**
 * Component for https://vmware.github.io/clarity/documentation/v1.0/checkboxes
 *
 * @author Daniel Furtlehner
 */
public class ClarityCheckboxContainerComponent
    extends AbstractSeleniumComponent
    implements ActiveSeleniumComponent, WithClarityControlHelpers {

    public static ClarityCheckboxContainerComponent within(SeleniumComponent parent) {
        return new ClarityCheckboxContainerComponent(parent, selectByTagName("clr-checkbox-container"));
    }

    public static ClarityCheckboxContainerComponent byTestId(SeleniumComponent parent, String testId) {
        return new ClarityCheckboxContainerComponent(parent, selectByTestId("clr-checkbox-container", testId));
    }

    public static ClarityCheckboxContainerComponent byText(SeleniumComponent parent, String partialText) {
        return new ClarityCheckboxContainerComponent(parent, selectByText("clr-checkbox-container", partialText));
    }

    public static ClarityCheckboxContainerComponent byName(SeleniumComponent parent, String name) {
        String xpath = String.format(".//clr-checkbox-container[.//input[@name='%s']]", name);

        return new ClarityCheckboxContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityCheckboxContainerComponent byFormControlName(
        SeleniumComponent parent,
        String formControlName
    ) {
        String xpath = String.format(".//clr-checkbox-container[.//input[@formcontrolname='%s']]", formControlName);

        return new ClarityCheckboxContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityCheckboxContainerComponent byLabel(SeleniumComponent parent, String label) {
        String xpath = String.format(
            ".//clr-checkbox-container[.//label[contains(@class, 'clr-control-label') and contains(.,'%s')]]",
            label
        );

        return new ClarityCheckboxContainerComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    private final HtmlComponent label = new HtmlComponent(this, selectByTagName("label"));
    private final CheckboxComponent checkbox = new CheckboxComponent(this);

    public ClarityCheckboxContainerComponent(SeleniumComponent parent) {
        this(parent, selectByTagName("clr-checkbox-container"));
    }

    public ClarityCheckboxContainerComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public String getLabel() {
        return label.getText();
    }

    @Override
    public boolean isSelected() {
        return checkbox.isSelected();
    }

    @Override
    public void select() {
        if (!isSelected()) {
            LOG.interaction("Selecting %s", describe());

            if (label.isInteractable() && label.isClickable()) {
                label.click();
            } else if (checkbox.isClickable()) {
                checkbox.click();
            } else {
                click();
            }
        }
    }

    @Override
    public void unselect() {
        if (isSelected()) {
            LOG.interaction("Unselecting %s", describe());

            if (label.isInteractable() && label.isClickable()) {
                label.click();
            } else if (checkbox.isClickable()) {
                checkbox.click();
            } else {
                click();
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return checkbox.isEnabled();
    }

    @Override
    public boolean isEditable() {
        return checkbox.isEditable();
    }

    @Override
    public boolean isDisabled() {
        return checkbox.isDisabled();
    }

    public String getId() {
        return checkbox.getId();
    }

    public String getName() {
        String name = checkbox.getName();

        if (Utils.isEmpty(name)) {
            name = SeleniumUtils.getAttribute(checkbox, "ng-reflect-name");
        }

        return name;
    }
}
