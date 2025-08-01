package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityComboboxOptionsComponent.AbstractClarityComboboxOptionComponent;

/**
 * @author TechScar
 */
public class ClaritySingleSelectComboboxContainerComponent<OPTION_TYPE extends AbstractClarityComboboxOptionComponent>
    extends ClarityFormControlContainer {

    public final ClaritySingleSelectComboboxComponent<OPTION_TYPE> singleSelect;

    public static ClaritySingleSelectComboboxContainerComponent<DefaultClarityComboboxOptionComponent> within(
        SeleniumComponent parent
    ) {
        return ClaritySingleSelectComboboxContainerComponent.within(parent, DefaultClarityComboboxOptionComponent::new);
    }

    public static <
        OPTION_TYPE extends AbstractClarityComboboxOptionComponent
    > ClaritySingleSelectComboboxContainerComponent<OPTION_TYPE> within(
        SeleniumComponent parent,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory
    ) {
        return new ClaritySingleSelectComboboxContainerComponent<>(
            parent,
            WebElementSelector.selectByTagName("clr-combobox-container"),
            optionFactory
        );
    }

    public static ClaritySingleSelectComboboxContainerComponent<DefaultClarityComboboxOptionComponent> byTestId(
        SeleniumComponent parent,
        String testId
    ) {
        return ClaritySingleSelectComboboxContainerComponent.byTestId(
            parent,
            testId,
            DefaultClarityComboboxOptionComponent::new
        );
    }

    public static <
        OPTION_TYPE extends AbstractClarityComboboxOptionComponent
    > ClaritySingleSelectComboboxContainerComponent<OPTION_TYPE> byTestId(
        SeleniumComponent parent,
        String testId,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory
    ) {
        return new ClaritySingleSelectComboboxContainerComponent<>(
            parent,
            WebElementSelector.selectByTestId(testId),
            optionFactory
        );
    }

    public static ClaritySingleSelectComboboxContainerComponent<
        DefaultClarityComboboxOptionComponent
    > byFormControlName(SeleniumComponent parent, String formControlName) {
        return ClaritySingleSelectComboboxContainerComponent.byFormControlName(
            parent,
            formControlName,
            DefaultClarityComboboxOptionComponent::new
        );
    }

    public static <
        OPTION_TYPE extends AbstractClarityComboboxOptionComponent
    > ClaritySingleSelectComboboxContainerComponent<OPTION_TYPE> byFormControlName(
        SeleniumComponent parent,
        String formControlName,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory
    ) {
        return new ClaritySingleSelectComboboxContainerComponent<>(
            parent,
            WebElementSelector.selectByTagNameContainingFormControlName("clr-combobox-container", formControlName),
            optionFactory
        );
    }

    public static ClaritySingleSelectComboboxContainerComponent<DefaultClarityComboboxOptionComponent> byLabel(
        SeleniumComponent parent,
        String partialLabel
    ) {
        return ClaritySingleSelectComboboxContainerComponent.byLabel(
            parent,
            partialLabel,
            DefaultClarityComboboxOptionComponent::new
        );
    }

    public static <
        OPTION_TYPE extends AbstractClarityComboboxOptionComponent
    > ClaritySingleSelectComboboxContainerComponent<OPTION_TYPE> byLabel(
        SeleniumComponent parent,
        String partialLabel,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory
    ) {
        return new ClaritySingleSelectComboboxContainerComponent<>(
            parent,
            WebElementSelector.selectByTagNameContainingLabel("clr-combobox-container", partialLabel),
            optionFactory
        );
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String, SeleniumComponentFactory)} instead
     */
    @Deprecated(forRemoval = true)
    public static <
        OPTION_TYPE extends AbstractClarityComboboxOptionComponent
    > ClaritySingleSelectComboboxContainerComponent<OPTION_TYPE> bySeleniumKey(
        SeleniumComponent parent,
        String seleniumKey,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory
    ) {
        return new ClaritySingleSelectComboboxContainerComponent<>(
            parent,
            WebElementSelector.selectBySeleniumKey(seleniumKey),
            optionFactory
        );
    }

    public ClaritySingleSelectComboboxContainerComponent(
        SeleniumComponent parent,
        WebElementSelector selector,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory
    ) {
        super(parent, selector);
        singleSelect = new ClaritySingleSelectComboboxComponent<>(this, optionFactory);
    }

    public void clear() {
        singleSelect.clear();
    }

    public void selectByLabel(String partialText) {
        singleSelect.selectByLabel(partialText);
    }

    public void assertSelected(String partialText) {
        singleSelect.assertSelected(partialText);
    }

    public boolean isEnabled() {
        return singleSelect.isEnabled();
    }

    public boolean isEditable() {
        return singleSelect.isEditable();
    }

    public boolean isDisabled() {
        return singleSelect.isDisabled();
    }
}
