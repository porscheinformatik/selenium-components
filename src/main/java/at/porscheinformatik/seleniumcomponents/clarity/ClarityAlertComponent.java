/**
 *
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public static ClarityAlertComponent within(SeleniumComponent parent)
    {
        return new ClarityAlertComponent(parent, selectByTagName("clr-alert"));
    }

    public static ClarityAlertComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new ClarityAlertComponent(parent, selectByTestId("clr-alert", testId));
    }

    public static ClarityAlertComponent byText(SeleniumComponent parent, String partialText)
    {
        return new ClarityAlertComponent(parent, selectByText("clr-alert", partialText));
    }

    private final SeleniumComponentListFactory<ClarityAlertItem> entryFactory =
        new SeleniumComponentListFactory<>(this, selectByTagName("clr-alert-item"), ClarityAlertItem::new);

    private final HtmlComponent alertContainer = new HtmlComponent(this, selectByClassName("alert"));

    public ClarityAlertComponent(SeleniumComponent parent)
    {
        this(parent, selectByTagName("clr-alert"));
    }

    public ClarityAlertComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public SeleniumComponentList<ClarityAlertItem> getItems()
    {
        return entryFactory.findAll();
    }

    public List<String> getAlertTexts()
    {
        return getItems().map(ClarityAlertItem::getAlertText).collect(Collectors.toList());
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
        public static ClarityAlertItem within(SeleniumComponent parent)
        {
            return new ClarityAlertItem(parent, selectByTagName("clr-alert-item"));
        }

        public static ClarityAlertItem byTestId(SeleniumComponent parent, String testId)
        {
            return new ClarityAlertItem(parent, selectByTestId("clr-alert-item", testId));
        }

        public static ClarityAlertItem byText(SeleniumComponent parent, String partialText)
        {
            return new ClarityAlertItem(parent, selectByText("clr-alert-item", partialText));
        }

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
