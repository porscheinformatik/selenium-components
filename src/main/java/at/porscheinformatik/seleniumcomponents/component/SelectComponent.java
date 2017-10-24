package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.Objects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A &lt;select&gt;
 *
 * @author HAM
 */
public class SelectComponent extends HtmlComponent
{

    private final SeleniumComponentListFactory<OptionComponent> optionsFactory =
        new SeleniumComponentListFactory<>(this, WebElementSelector.selectByTagName("option"), OptionComponent::new);

    public SelectComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public SelectComponent(HtmlComponent parent)
    {
        this(parent, selectByTagName("select"));
    }

    public String getValue()
    {
        return getAttribute("value");
    }

    public void selectByLabel(String label)
    {
        WebElement element = element();

        element.click();
        element.sendKeys(label);
        element.sendKeys(Keys.RETURN);
    }

    public void selectByValue(String value)
    {
        OptionComponent option = optionsFactory.find($ -> Objects.equals(value, $.getValue()));

        if (option == null)
        {
            throw new AssertionError(String.format("Options with value \"%s\" not found", value));
        }

        selectByLabel(option.getLabel());
    }

}
