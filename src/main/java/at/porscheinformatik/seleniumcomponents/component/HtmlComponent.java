package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumActions;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * Represents an HTML tag.
 *
 * @author ham
 */
public class HtmlComponent extends AbstractSeleniumComponent
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

    public void click()
    {
        click(DEFAULT_TIMEOUT_IN_SECONDS);
    }

    public void click(double timeoutInSeconds)
    {
        SeleniumActions.click(timeoutInSeconds, this);
    }

    public String getText()
    {
        return SeleniumActions.getText(this);
    }

    @Override
    public String getAttribute(String name)
    {
        return SeleniumActions.getAttribute(this, name);
    }

    public boolean containsClassName(String className)
    {
        String attribute = getAttribute("class");

        return attribute != null && attribute.contains(className);
    }

}
