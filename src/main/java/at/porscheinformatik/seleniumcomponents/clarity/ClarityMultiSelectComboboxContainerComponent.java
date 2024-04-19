package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityComboboxOptionsComponent.AbstractClarityComboboxOptionComponent;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityMultiSelectComboboxComponent.AbstractClarityComboboxLabelPillComponent;

/**
 * @author TechScar
 */
public class ClarityMultiSelectComboboxContainerComponent<OPTION_TYPE extends AbstractClarityComboboxOptionComponent,
    PILL_TYPE extends AbstractClarityComboboxLabelPillComponent>
    extends ClarityFormControlContainer
{
    public final ClarityMultiSelectComboboxComponent<OPTION_TYPE, PILL_TYPE> multiSelect;

    // ---

    public static <OPTION_TYPE extends AbstractClarityComboboxOptionComponent,
        PILL_TYPE extends AbstractClarityComboboxLabelPillComponent> ClarityMultiSelectComboboxContainerComponent<OPTION_TYPE, PILL_TYPE> within(
        SeleniumComponent parent, SeleniumComponentFactory<OPTION_TYPE> optionFactory,
        SeleniumComponentFactory<PILL_TYPE> pillFactory)
    {
        return new ClarityMultiSelectComboboxContainerComponent<>(parent, selectByTagName("clr-combobox-container"),
            optionFactory, pillFactory);
    }

    public static <OPTION_TYPE extends AbstractClarityComboboxOptionComponent,
        PILL_TYPE extends AbstractClarityComboboxLabelPillComponent> ClarityMultiSelectComboboxContainerComponent<OPTION_TYPE, PILL_TYPE> byTestId(
        SeleniumComponent parent, String testId, SeleniumComponentFactory<OPTION_TYPE> optionFactory,
        SeleniumComponentFactory<PILL_TYPE> pillFactory)
    {
        return new ClarityMultiSelectComboboxContainerComponent<>(parent, selectByTestId(testId), optionFactory,
            pillFactory);
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String, SeleniumComponentFactory, SeleniumComponentFactory)}
     * instead
     */
    @Deprecated(forRemoval = true)
    public static <OPTION_TYPE extends AbstractClarityComboboxOptionComponent,
        PILL_TYPE extends AbstractClarityComboboxLabelPillComponent> ClarityMultiSelectComboboxContainerComponent<OPTION_TYPE, PILL_TYPE> bySeleniumKey(
        SeleniumComponent parent, String seleniumKey, SeleniumComponentFactory<OPTION_TYPE> optionFactory,
        SeleniumComponentFactory<PILL_TYPE> pillFactory)
    {
        return new ClarityMultiSelectComboboxContainerComponent<>(parent, selectBySeleniumKey(seleniumKey),
            optionFactory, pillFactory);
    }

    // ---

    public ClarityMultiSelectComboboxContainerComponent(SeleniumComponent parent, WebElementSelector selector,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory, SeleniumComponentFactory<PILL_TYPE> pillFactory)
    {
        super(parent, selector);

        multiSelect = new ClarityMultiSelectComboboxComponent<>(this, optionFactory, pillFactory);
    }
}
