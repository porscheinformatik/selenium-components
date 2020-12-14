package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.function.Predicate;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ClickableSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.StringPredicate;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A &lt;select&gt;
 *
 * @author HAM
 * @author Daniel Furtlehner
 */
public class SelectComponent extends AbstractSeleniumComponent implements ClickableSeleniumComponent
{
    protected final SeleniumComponentListFactory<OptionComponent> optionsFactory =
        new SeleniumComponentListFactory<>(this, WebElementSelector.selectByTagName("option"), OptionComponent::new);

    protected final SeleniumComponentListFactory<OptionGroupComponent> optionGroupsFactory =
        new SeleniumComponentListFactory<>(this, WebElementSelector.selectByTagName("optgroup"),
            OptionGroupComponent::new);

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
        OptionComponent option = getFirstSelectedOption();

        return option != null ? option.getValue() : null;
    }

    public String getSelectedLabel()
    {
        OptionComponent option = getFirstSelectedOption();

        return option != null ? option.getLabel() : null;
    }

    public void selectByLabel(String label)
    {
        selectByLabel(StringPredicate.equalTo(label));
    }

    public void selectByLabel(Predicate<String> labelPredicate)
    {
        select(option -> labelPredicate.test(option.getLabel()));
    }

    public void selectByValue(String value)
    {
        selectByValue(StringPredicate.equalTo(value));
    }

    public void selectByValue(Predicate<String> valuePredicate)
    {
        select(option -> valuePredicate.test(option.getValue()));
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

    private OptionComponent getFirstSelectedOption()
    {
        return optionsFactory.find(OptionComponent::isSelected);
    }
}
