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

    public static ClaritySingleSelectComboboxContainerComponent<DefaultClarityComboboxOptionComponent> within(
        SeleniumComponent parent)
    {
        return new ClaritySingleSelectComboboxContainerComponent<>(parent, selectByTagName("clr-combobox-container"),
            DefaultClarityComboboxOptionComponent::new);
    }

    public static <OPTION_TYPE extends AbstractClarityComboboxOptionComponent> ClaritySingleSelectComboboxContainerComponent<OPTION_TYPE> within(
        SeleniumComponent parent, SeleniumComponentFactory<OPTION_TYPE> optionFactory)
    {
        return new ClaritySingleSelectComboboxContainerComponent<>(parent, selectByTagName("clr-combobox-container"),
            optionFactory);
    }

    public static ClaritySingleSelectComboboxContainerComponent<DefaultClarityComboboxOptionComponent> byTestId(
        SeleniumComponent parent, String testId)
    {
        return new ClaritySingleSelectComboboxContainerComponent<>(parent, selectByTestId(testId),
            DefaultClarityComboboxOptionComponent::new);
    }

    public static <OPTION_TYPE extends AbstractClarityComboboxOptionComponent> ClaritySingleSelectComboboxContainerComponent<OPTION_TYPE> byTestId(
        SeleniumComponent parent, String testId, SeleniumComponentFactory<OPTION_TYPE> optionFactory)
    {
        return new ClaritySingleSelectComboboxContainerComponent<>(parent, selectByTestId(testId), optionFactory);
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String, SeleniumComponentFactory)} instead
     */
    @Deprecated(forRemoval = true)
    public static <OPTION_TYPE extends AbstractClarityComboboxOptionComponent> ClaritySingleSelectComboboxContainerComponent<OPTION_TYPE> bySeleniumKey(
        SeleniumComponent parent, String seleniumKey, SeleniumComponentFactory<OPTION_TYPE> optionFactory)
    {
        return new ClaritySingleSelectComboboxContainerComponent<>(parent, selectBySeleniumKey(seleniumKey),
            optionFactory);
    }

    public ClaritySingleSelectComboboxContainerComponent(SeleniumComponent parent, WebElementSelector selector,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory)
    {
        super(parent, selector);

        singleSelect = new ClaritySingleSelectComboboxComponent<>(this, optionFactory);
    }

    public void clear()
    {
        singleSelect.clear();
    }

    public void selectByLabel(String partialText)
    {
        singleSelect.selectByLabel(partialText);
    }

    public void assertSelected(String partialText)
    {
        singleSelect.assertSelected(partialText);
    }

    public boolean isEnabled()
    {
        return singleSelect.isEnabled();
    }

    public boolean isEditable()
    {
        return singleSelect.isEditable();
    }

    public boolean isDisabled()
    {
        return singleSelect.isDisabled();
    }
}
