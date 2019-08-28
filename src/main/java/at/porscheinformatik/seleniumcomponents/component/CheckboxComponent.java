/**
 *
 */
package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SelectableSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author Daniel Furtlehner
 */
public class CheckboxComponent extends AbstractSeleniumComponent implements SelectableSeleniumComponent
{

    /**
     * Creates the component.
     *
     * @param parent the parent
     * @param selector the selector for the element
     */
    public CheckboxComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public CheckboxComponent(SeleniumComponent parent)
    {
        this(parent, selectByCss("input[type=\"checkbox\"]"));
    }

    public void setSelected(boolean selected)
    {
        if (selected)
        {
            select();
        }
        else
        {
            unselect();
        }
    }

    public String getId()
    {
        return getAttribute("id");
    }

    public String getName()
    {
        return getAttribute("name");
    }

}
