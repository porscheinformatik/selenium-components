package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.function.Predicate;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.Select;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.EditableSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumUtils;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A &lt;select&gt;
 *
 * @author HAM
 * @author Daniel Furtlehner
 */
public class SelectComponent extends AbstractSeleniumComponent implements EditableSeleniumComponent
{
    protected final SeleniumComponentListFactory<OptionComponent> optionsFactory =
        new SeleniumComponentListFactory<>(this, WebElementSelector.selectByTagName("option"), OptionComponent::new);

    protected final SeleniumComponentListFactory<OptionGroupComponent> optionGroupsFactory =
        new SeleniumComponentListFactory<>(this, WebElementSelector.selectByTagName("optgroup"),
            OptionGroupComponent::new);

    private Select select;

    public SelectComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public SelectComponent(SeleniumComponent parent)
    {
        this(parent, selectByTagName("select"));
    }

    protected Select getSelect()
    {
        if (select == null)
        {
            select = new Select(element());
        }

        return select;
    }

    public String getValue()
    {
        WebElement option = getSelect().getFirstSelectedOption();

        return option != null ? option.getAttribute("value") : null;
    }

    public String getSelectedLabel()
    {
        WebElement option = getSelect().getFirstSelectedOption();

        return option != null ? option.getText() : null;
    }

    public OptionComponent optionByIndex(int index)
    {
        return new OptionComponent(this, WebElementSelector.selectByIndex("option", index));
    }

    public OptionComponent optionByValue(String value)
    {
        return new OptionComponent(this,
            WebElementSelector.selectByXPath(".//option[@value = " + Quotes.escape(value) + "]"));
    }

    public OptionComponent optionByLabel(String label)
    {
        return new OptionComponent(this,
            WebElementSelector.selectByXPath(".//option[normalize-space(.) = " + Quotes.escape(label) + "]"));
    }

    public void selectByIndex(int index)
    {
        SeleniumUtils.retryOnStale(optionByIndex(index)::select);
    }

    public void selectByValue(String value)
    {
        SeleniumUtils.retryOnStale(optionByValue(value)::select);
    }

    public void selectByValue(Predicate<String> valuePredicate)
    {
        select(option -> valuePredicate.test(option.getValue()));
    }

    public void selectByLabel(String label)
    {
        SeleniumUtils.retryOnStale(optionByLabel(label)::select);
    }

    public void selectByLabel(Predicate<String> labelPredicate)
    {
        select(option -> labelPredicate.test(option.getLabel()));
    }

    public void select(Predicate<OptionComponent> predicate)
    {
        LOG.interaction("Selecting item of of %s by predicate", describe());

        click();

        OptionComponent option = optionsFactory.find(predicate);

        if (option == null)
        {
            throw new AssertionError(String.format("Matching option not found"));
        }

        option.click();
    }

    /**
     * @return all the options this select component contains
     */
    public SeleniumComponentList<OptionComponent> getOptions()
    {
        return optionsFactory.findAll();
    }

    public SeleniumComponentList<OptionGroupComponent> getOptionGroups()
    {
        return optionGroupsFactory.findAll();
    }
}
