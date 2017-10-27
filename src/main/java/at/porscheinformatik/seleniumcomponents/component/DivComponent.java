package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * Represents a DIV element.
 *
 * @author ham
 */
public class DivComponent extends HtmlComponent
{

    public DivComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("div"));
    }

    public DivComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

}
