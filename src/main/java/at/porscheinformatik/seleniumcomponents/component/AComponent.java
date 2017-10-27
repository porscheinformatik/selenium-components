package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * Represents an anchor element.
 *
 * @author ham
 */
public class AComponent extends HtmlComponent
{

    public AComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("a"));
    }

    public AComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getHref()
    {
        return getAttribute("href");
    }
}
