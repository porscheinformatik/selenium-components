/**
 *
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

/**
 * Compnent for https://vmware.github.io/clarity/documentation/v0.11/alerts
 *
 * @author Daniel Furtlehner
 */
public class ClarityAlertComponent extends AbstractSeleniumComponent
{
    private final SeleniumComponentListFactory<ClarityAlertItem> entryFactory =
        new SeleniumComponentListFactory<>(this, selectByTagName("clr-alert-item"), ClarityAlertItem::new);
    private final HtmlComponent alertContainer = new HtmlComponent(this, selectByClassName("alert"));

    public ClarityAlertComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("clr-alert"));
    }

    public ClarityAlertComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public SeleniumComponentList<ClarityAlertItem> getItems()
    {
        return entryFactory.findAll();
    }

    public ClarityAlertType getType()
    {
        String containerClasses = alertContainer.getAttribute("class");
        String[] classNames = containerClasses.split(" ");
        String attributeValue = null;

        for (String className : classNames)
        {
            if (className.startsWith("alert-"))
            {
                attributeValue = className;
            }
        }

        return ClarityAlertType.forClassName(attributeValue);
    }

    /**
     * Alert types for clarity
     */
    public enum ClarityAlertType
    {
        DANGER("alert-danger"),
        WARNING("alert-warning"),
        INFO("alert-info"),
        SUCCESS("alert-success");

        private final String className;

        ClarityAlertType(String className)
        {
            this.className = className;
        }

        public static ClarityAlertType forClassName(String className)
        {
            for (ClarityAlertType type : values())
            {
                if (type.className.equals(className))
                {
                    return type;
                }
            }

            throw new IllegalArgumentException(String.format("Unknown alert class %s", className));
        }
    }

    /**
     * Alert items for Clarity
     */
    public static class ClarityAlertItem extends AbstractSeleniumComponent
    {
        private final HtmlComponent alertText = new HtmlComponent(this, selectByClassName("alert-text"));

        public ClarityAlertItem(SeleniumComponent parent, WebElementSelector selector)
        {
            super(parent, selector);
        }

        public String getAlertText()
        {
            return alertText.getText();
        }
    }
}
