/**
 * 
 */
package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author Daniel Furtlehner
 */
public class HeadingComponent extends AbstractSeleniumComponent
{

    public HeadingComponent(SeleniumComponent parent, int level)
    {
        super(parent, selectByTagName("h" + level));
    }

    public HeadingComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    @Override
    public String getText()
    {
        return super.getText();
    }

}
