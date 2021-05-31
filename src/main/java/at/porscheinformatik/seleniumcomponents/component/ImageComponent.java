/**
 *
 */
package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author Daniel Furtlehner
 */
public class ImageComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{

    public ImageComponent(SeleniumComponent parent)
    {
        this(parent, selectByTagName("img"));
    }

    public ImageComponent(SeleniumComponent parent, String seleniumKey)
    {
        this(parent, selectBySeleniumKey("img", seleniumKey));
    }

    public ImageComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getAlt()
    {
        return getAttribute("alt");
    }

    public String getSrc()
    {
        return getAttribute("src");
    }
}
