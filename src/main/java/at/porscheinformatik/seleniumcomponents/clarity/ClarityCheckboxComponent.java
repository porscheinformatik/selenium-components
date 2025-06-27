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
public class ClarityCheckboxComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{
    public static ClarityCheckboxComponent within(SeleniumComponent parent)
    {
        return new ClarityCheckboxComponent(parent, selectByTagName("clr-checkbox-wrapper"));
    }

    public static ClarityCheckboxComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new ClarityCheckboxComponent(parent, selectByTestId("clr-checkbox-wrapper", testId));
    }

    public static ClarityCheckboxComponent byText(SeleniumComponent parent, String partialText)
    {
        return new ClarityCheckboxComponent(parent, selectByText("clr-checkbox-wrapper", partialText));
    }

    public static ClarityCheckboxComponent byFormControlName(SeleniumComponent parent, String formControlName)
    {
        String xpath = String.format(".//clr-checkbox-wrapper[.//input[@formcontrolname='%s']]", formControlName);

        return new ClarityCheckboxComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityCheckboxComponent byLabel(SeleniumComponent parent, String label)
    {
        String xpath = String.format(
            ".//clr-checkbox-wrapper[.//label[contains(@class, 'clr-control-label') and contains(.,'%s')]]", label);

        return new ClarityCheckboxComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    private final HtmlComponent label = new HtmlComponent(this, selectByTagName("label"));
    private final CheckboxComponent checkbox = new CheckboxComponent(this);

    public ClarityCheckboxComponent(SeleniumComponent parent)
    {
        this(parent, selectByTagName("clr-checkbox-wrapper"));
    }

    public ClarityCheckboxComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getLabel()
    {
        return label.getText();
    }

    @Override
    public boolean isSelected()
    {
        return checkbox.isSelected();
    }

    @Override
    public void select()
    {
        if (!isSelected())
        {
            LOG.interaction("Selecting %s", describe());

            if (label.isInteractable() && label.isClickable())
            {
                label.click();
            }
            else if (checkbox.isClickable())
            {
                checkbox.click();
            }
            else
            {
                click();
            }
        }
    }

    @Override
    public void unselect()
    {
        if (isSelected())
        {
            LOG.interaction("Unselecting %s", describe());

            if (label.isInteractable() && label.isClickable())
            {
                label.click();
            }
            else if (checkbox.isClickable())
            {
                checkbox.click();
            }
            else
            {
                click();
            }
        }
    }

    @Override
    public boolean isEnabled()
    {
        return checkbox.isEnabled();
    }

    @Override
    public boolean isEditable()
    {
        return checkbox.isEditable();
    }

    @Override
    public boolean isDisabled()
    {
        return checkbox.isDisabled();
    }

    public String getId()
    {
        return checkbox.getId();
    }

    public String getName()
    {
        String name = checkbox.getName();

        if (Utils.isEmpty(name))
        {
            name = SeleniumUtils.getAttribute(checkbox, "ng-reflect-name");
        }

        return name;
    }
}
