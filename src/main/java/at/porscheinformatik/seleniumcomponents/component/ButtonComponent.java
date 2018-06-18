/**
 * 
 */
package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ClickableSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author Daniel Furtlehner
 */
public class ButtonComponent extends AbstractSeleniumComponent implements ClickableSeleniumComponent
{
    public ButtonComponent(SeleniumComponent parent)
    {
        super(parent, selectByTagName("button"));
    }

    public ButtonComponent(SeleniumComponent parent, String seleniumKey)
    {
        this(parent, selectBySeleniumKey("button", seleniumKey));
    }

    public ButtonComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getLabel()
    {
        return getText();
    }
}
