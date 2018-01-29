/**
 * 
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SelectableSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

/**
 * Component for https://vmware.github.io/clarity/documentation/v0.11/checkboxes
 * 
 * @author Daniel Furtlehner
 */
public class ClarityCheckbox extends AbstractSeleniumComponent implements SelectableSeleniumComponent
{
    private final HtmlComponent label = new HtmlComponent(this, selectByTagName("label"));

    public ClarityCheckbox(SeleniumComponent parent)
    {
        this(parent, selectByTagName("clr-checkbox"));
    }

    public ClarityCheckbox(SeleniumComponent parent, WebElementSelector selector)
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
        return "true".equals(getAttribute("ng-reflect-checked"));
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

}
