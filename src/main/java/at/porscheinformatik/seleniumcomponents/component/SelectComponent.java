package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumAsserts;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumMatchers;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import java.util.function.Predicate;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.Select;

/**
 * A &lt;select&gt;
 *
 * @author HAM
 * @author Daniel Furtlehner
 */
public class SelectComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent {

    protected final SeleniumComponentListFactory<OptionComponent> optionsFactory = new SeleniumComponentListFactory<>(
        this,
        WebElementSelector.selectByTagName("option"),
        OptionComponent::new
    );

    protected final SeleniumComponentListFactory<OptionGroupComponent> optionGroupsFactory =
        new SeleniumComponentListFactory<>(
            this,
            WebElementSelector.selectByTagName("optgroup"),
            OptionGroupComponent::new
        );

    private Select select;

    public SelectComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public SelectComponent(SeleniumComponent parent) {
        this(parent, selectByTagName("select"));
    }

    protected Select getSelect() {
        if (select == null) {
            select = new Select(element());
        }

        return select;
    }

    /**
     * @return the (first) selected value
     */
    public String getValue() {
        try {
            WebElement option = getSelect().getFirstSelectedOption();

            return option != null ? option.getAttribute("value") : null;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * @return the (first) selected label
     */
    public String getSelectedLabel() {
        WebElement option = getSelect().getFirstSelectedOption();

        return option != null ? option.getText() : null;
    }

    public OptionComponent getSelectedOption() {
        return new OptionComponent(
            this,
            WebElementSelector.selectElement("option[@selected]", getSelect().getFirstSelectedOption())
        );
    }

    public OptionComponent optionByIndex(int index) {
        return new OptionComponent(this, WebElementSelector.selectByIndex("option", index));
    }

    public OptionComponent optionByValue(String value) {
        return new OptionComponent(
            this,
            WebElementSelector.selectByXPath(".//option[@value = " + Quotes.escape(value) + "]")
        );
    }

    public OptionComponent optionByValueContains(String value) {
        return new OptionComponent(
            this,
            WebElementSelector.selectByXPath(".//option[contains(@value, " + Quotes.escape(value) + ")]")
        );
    }

    public OptionComponent optionByLabel(String label) {
        return new OptionComponent(
            this,
            WebElementSelector.selectByXPath(".//option[normalize-space(.) = " + Quotes.escape(label) + "]")
        );
    }

    public void selectByIndex(int index) {
        LOG.interaction("Selecting item of %s by index: %s", describe(), index);

        SeleniumAsserts.assertThatSoon(
            "Select item at index: " + index,
            () -> {
                optionByIndex(index).select();

                // it's intended, that the option is searched again (the element may change on select an become stale)
                return optionByIndex(index);
            },
            SeleniumMatchers.isSelected()
        );
    }

    public void selectByValue(String value) {
        LOG.interaction("Selecting item of %s by value: %s", describe(), value);

        SeleniumAsserts.assertThatSoon(
            "Select item by value: " + value,
            () -> {
                optionByValue(value).select();

                // it's intended, that the option is searched again (the element may change on select an become stale)
                return optionByValue(value);
            },
            SeleniumMatchers.isSelected()
        );
    }

    public void selectByValueContains(String value) {
        LOG.interaction("Selecting item of %s by value: %s", describe(), value);

        SeleniumAsserts.assertThatSoon(
            "Select item by value: " + value,
            () -> {
                optionByValueContains(value).select();

                // it's intended, that the option is searched again (the element may change on select an become stale)
                return optionByValueContains(value);
            },
            SeleniumMatchers.isSelected()
        );
    }

    public void selectByValue(Predicate<String> valuePredicate) {
        select(option -> valuePredicate.test(option.getValue()));
    }

    public void selectByLabel(String label) {
        LOG.interaction("Selecting item of %s by label: %s", describe(), label);

        SeleniumAsserts.assertThatSoon(
            "Select item by label: " + label,
            () -> {
                optionByLabel(label).select();

                // it's intended, that the option is searched again (the element may change on select an become stale)
                return optionByLabel(label);
            },
            SeleniumMatchers.isSelected()
        );
    }

    public void selectByLabel(Predicate<String> labelPredicate) {
        select(option -> labelPredicate.test(option.getLabel()));
    }

    public void select(Predicate<OptionComponent> predicate) {
        LOG.interaction("Selecting item of %s by predicate", describe());

        SeleniumAsserts.assertThatSoon(
            "Select item by predicate",
            () -> {
                click();

                OptionComponent option = optionsFactory.find(predicate);

                if (option == null) {
                    throw new AssertionError(String.format("Matching option not found"));
                }

                option.click();

                // it's intended, that the option is searched again (the element may change on select an become stale)
                return optionsFactory.find(predicate);
            },
            SeleniumMatchers.isSelected()
        );
    }

    /**
     * @return all the options this select component contains
     */
    public SeleniumComponentList<OptionComponent> getOptions() {
        return optionsFactory.findAll();
    }

    public SeleniumComponentList<OptionGroupComponent> getOptionGroups() {
        return optionGroupsFactory.findAll();
    }
}
