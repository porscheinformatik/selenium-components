/**
 * 
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumUtils;
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
            label.click();
        }
    }

    @Override
    public void unselect()
    {
        if (isSelected())
        {
            label.click();
        }
    }

    public String getId()
    {
        return checkbox.getId();
    }

    public String getName()
    {
        String name = checkbox.getName();

        if (name == null)
        {
            name = SeleniumUtils.getAttribute(checkbox, "ng-reflect-name");
        }

        return name;
    }
}
