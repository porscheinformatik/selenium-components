package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.Objects;
import java.util.function.BiPredicate;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ClickableSeleniumComponent;
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
public class SelectComponent extends AbstractSeleniumComponent implements ClickableSeleniumComponent
{
    /**
     * @deprecated 1. this value is not available if compiled for productive environments and 2. this is Angular
     *             specific while this component must remain HTML specific.
     */
    private static final BiPredicate<String, OptionComponent> DEFAULT_VALUE_COMPARATOR =
        (value, option) -> Objects.equals(value, option.getValue()) || Objects.equals(value, option.getNgSelectValue());

    private final SeleniumComponentListFactory<OptionComponent> optionsFactory =
        new SeleniumComponentListFactory<>(this, WebElementSelector.selectByTagName("option"), OptionComponent::new);
    private final SeleniumComponentListFactory<OptionGroupComponent> optionGroupsFactory =
        new SeleniumComponentListFactory<>(this, WebElementSelector.selectByTagName("optgroup"),
            OptionGroupComponent::new);

    private final BiPredicate<String, OptionComponent> valueComparator;

    public SelectComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        this(parent, selector, DEFAULT_VALUE_COMPARATOR);
    }

    public SelectComponent(SeleniumComponent parent)
    {
        this(parent, DEFAULT_VALUE_COMPARATOR);
    }

    public SelectComponent(SeleniumComponent parent, WebElementSelector selector,
        BiPredicate<String, OptionComponent> valueComparator)
    {
        super(parent, selector);

        this.valueComparator = valueComparator;
    }

    public SelectComponent(SeleniumComponent parent, BiPredicate<String, OptionComponent> valueComparator)
    {
        this(parent, selectByTagName("select"), valueComparator);
    }

    public String getValue()
    {
        OptionComponent option = getFirstSelectedOption();

        return option != null ? option.getValue() : null;
    }

    /**
     * In Angular, when we use a "ngValue" directive, the real value is prefixed with a number followed by a ":". So we
     * should strip that off.
     *
     * @return the value
     * @deprecated 1. this value is not available if compiled for productive environments and 2. this is Angular
     *             specific and does not belong here
     */
    @Deprecated
    public String getNgValue()
    {
        OptionComponent option = getFirstSelectedOption();

        return option != null ? option.getNgSelectValue() : null;
    }

    public String getSelectedLabel()
    {
        OptionComponent option = getFirstSelectedOption();

        return option != null ? option.getLabel() : null;
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

        click();

        OptionComponent option = optionsFactory.find($ -> valueComparator.test(value, $));

        if (option == null)
        {
            throw new AssertionError(String.format("Options with value \"%s\" not found", value));
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
