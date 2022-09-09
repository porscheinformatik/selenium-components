package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityComboboxOptionsComponent.AbstractClarityComboboxOptionComponent;

/**
 * @author TechScar
 */
public class ClaritySingleSelectComboboxContainerComponent<OPTION_TYPE extends AbstractClarityComboboxOptionComponent>
    extends ClarityFormControlContainer
{
    public final ClaritySingleSelectComboboxComponent<OPTION_TYPE> singleSelect;

    // ---

    public static <OPTION_TYPE extends AbstractClarityComboboxOptionComponent> ClaritySingleSelectComboboxContainerComponent<OPTION_TYPE> within(
        SeleniumComponent parent, SeleniumComponentFactory<OPTION_TYPE> optionFactory)
    {
        return new ClaritySingleSelectComboboxContainerComponent<>(parent, selectByTagName("clr-combobox-container"),
            optionFactory);
    }

    public static <OPTION_TYPE extends AbstractClarityComboboxOptionComponent> ClaritySingleSelectComboboxContainerComponent<OPTION_TYPE> bySeleniumKey(
        SeleniumComponent parent, String seleniumKey, SeleniumComponentFactory<OPTION_TYPE> optionFactory)
    {
        return new ClaritySingleSelectComboboxContainerComponent<>(parent, selectBySeleniumKey(seleniumKey),
            optionFactory);
    }

    // ---

    public ClaritySingleSelectComboboxContainerComponent(SeleniumComponent parent, WebElementSelector selector,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory)
    {
        super(parent, selector);

        singleSelect = new ClaritySingleSelectComboboxComponent<>(this, optionFactory);
    }
}
