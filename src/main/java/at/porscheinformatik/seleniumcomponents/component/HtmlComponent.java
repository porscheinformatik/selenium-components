package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumUtils;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A generic HTML Component that can be used for simple tags like &lt;p&gt; , &lt;span&gt;, &lt;div&gt; Should only be
 * used if a more specific component is not available.
 *
 * @author ham
 * @author Daniel Furtlehner
 */
public final class HtmlComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{

    /**
     * Creates the component.
     *
     * @param parent the parent
     * @param selector the selector for the element
     */
    public HtmlComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    @Override
    public String getTagName()
    {
        return super.getTagName();
    }

    @Override
    public String getText()
    {
        return super.getText();
    }

    @Override
    public String getAttribute(String name)
    {
        return SeleniumUtils.getAttribute(this, name);
    }

    @Override
    public boolean containsClassName(String className)
    {
        return super.containsClassName(className);
    }

}
