package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ClickableSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author cet
 */
public class LinkComponent extends AbstractSeleniumComponent implements ClickableSeleniumComponent
{

    public LinkComponent(SeleniumComponent parent)
    {
        this(parent, selectByTagName("a"));
    }

    public LinkComponent(SeleniumComponent parent, String seleniumKey)
    {
        this(parent, selectBySeleniumKey("a", seleniumKey));
    }

    public LinkComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getHref()
    {
        return getAttribute("href");
    }

    @Override
    public String getText()
    {
        return super.getText();
    }

}
