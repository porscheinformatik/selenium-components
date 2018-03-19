package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.Objects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A &lt;select&gt;
 *
 * @author HAM
 * @author Daniel Furtlehner
 */
public class SelectComponent extends AbstractSeleniumComponent
{

    private final SeleniumComponentListFactory<OptionComponent> optionsFactory =
        new SeleniumComponentListFactory<>(this, WebElementSelector.selectByTagName("option"), OptionComponent::new);

    public SelectComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public SelectComponent(SeleniumComponent parent)
    {
        this(parent, selectByTagName("select"));
    }

    public String getValue()
    {
        return getAttribute("value");
    }

    public String getSelectedLabel()
    {
        return getText();
    }

    public void selectByLabel(String label)
    {
        LOG.interaction("Selecting \"%s\" of %s by label", label, describe());

        WebElement element = element();

        element.click();
        element.sendKeys(label);
        element.sendKeys(Keys.RETURN);
    }

    public void selectByValue(String value)
    {
        LOG.interaction("Selecting \"%s\" of %s by value", value, describe());

        OptionComponent option = optionsFactory.find($ -> Objects.equals(value, $.getValue()));

        if (option == null)
        {
            throw new AssertionError(String.format("Options with value \"%s\" not found", value));
        }

        element().click();
        option.click();
    }

    /**
     * @return all the options this select component contains
     */
    public SeleniumComponentList<OptionComponent> getOptions()
    {
        return optionsFactory.findAll();
    }

}
