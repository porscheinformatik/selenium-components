package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumAsserts;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityComboboxOptionsComponent.AbstractClarityComboboxOptionComponent;
import org.hamcrest.Matchers;
import org.openqa.selenium.Keys;

/**
 * @author TechScar
 */
public class ClaritySingleSelectComboboxComponent<OPTION_TYPE extends AbstractClarityComboboxOptionComponent>
    extends AbstractClarityComboboxComponent<OPTION_TYPE> {

    protected ClaritySingleSelectComboboxComponent(
        SeleniumComponent parent,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory
    ) {
        super(parent, selectByTagName("clr-combobox"), optionFactory);
    }

    protected ClaritySingleSelectComboboxComponent(
        SeleniumComponent parent,
        WebElementSelector selector,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory
    ) {
        super(parent, selector, optionFactory);
    }

    @Override
    public void clear() {
        input.clear();
    }

    public void clearAndMarkDirty() {
        input.clear();

        // we need to do this here or the formControl will not recognize the value having changed
        input.sendKeys(".", Keys.BACK_SPACE);
    }

    public String getSelectedLabel() {
        return input.getValue();
    }

    @Override
    public void assertSelected(String partialText) {
        SeleniumAsserts.assertThatSoon(
            "\"" + partialText + "\" is selected",
            this::getSelectedLabel,
            Matchers.containsString(partialText)
        );
    }
}
