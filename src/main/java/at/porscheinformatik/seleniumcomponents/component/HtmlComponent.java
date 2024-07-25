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
    public static HtmlComponent byId(SeleniumComponent parent, String id)
    {
        return new HtmlComponent(parent, WebElementSelector.selectById(id));
    }

    public static HtmlComponent byTagName(SeleniumComponent parent, String tagName)
    {
        return new HtmlComponent(parent, WebElementSelector.selectByTagName(tagName));
    }

    public static HtmlComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new HtmlComponent(parent, WebElementSelector.selectByTestId(testId));
    }

    public static HtmlComponent byText(SeleniumComponent parent, String tagName, String partialText)
    {
        return new HtmlComponent(parent, WebElementSelector.selectByText(tagName, partialText));
    }

    public static HtmlComponent byClassName(SeleniumComponent parent, String className)
    {
        return new HtmlComponent(parent, WebElementSelector.selectByClassName(className));
    }

    public static HtmlComponent byClassNameAndText(SeleniumComponent parent, String className, String text)
    {
        return new HtmlComponent(parent, WebElementSelector.selectByClassNameAndText(className, text));
    }

    public static HtmlComponent byClassNameAndText(SeleniumComponent parent, String tagName, String className,
        String text)
    {
        return new HtmlComponent(parent, WebElementSelector.selectByClassNameAndText(tagName, className, text));
    }

    public static HtmlComponent byCss(SeleniumComponent parent, String css)
    {
        return new HtmlComponent(parent, WebElementSelector.selectByCss(css));
    }

    public static HtmlComponent byXPath(SeleniumComponent parent, String xpath)
    {
        return new HtmlComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

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
