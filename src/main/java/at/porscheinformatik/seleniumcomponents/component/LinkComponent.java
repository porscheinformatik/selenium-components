package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;

/**
 * @author cet
 */
public class LinkComponent extends HtmlComponent
{

    public LinkComponent(SeleniumComponent parent)
    {
        super(parent, selectByTagName("a"));
    }

    public LinkComponent(SeleniumComponent parent, String seleniumKey)
    {
        super(parent, selectBySeleniumKey("a", seleniumKey));
    }

}
