package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityComboboxOptionsComponent.AbstractClarityComboboxOptionComponent;

/**
 * @author TechScar
 */
public class ClaritySingleSelectComboboxContainerComponent<T extends AbstractClarityComboboxOptionComponent>
    extends ClarityFormControlContainer {

    private static final String TAG_NAME = "clr-combobox-container";

    public final ClaritySingleSelectComboboxComponent<T> singleSelect;

    public static ClaritySingleSelectComboboxContainerComponent<DefaultClarityComboboxOptionComponent> within(
        SeleniumComponent parent
    ) {
        return ClaritySingleSelectComboboxContainerComponent.within(parent, DefaultClarityComboboxOptionComponent::new);
    }

    public static <T extends AbstractClarityComboboxOptionComponent> ClaritySingleSelectComboboxContainerComponent<
        T
    > within(SeleniumComponent parent, SeleniumComponentFactory<T> optionFactory) {
        return new ClaritySingleSelectComboboxContainerComponent<>(
            parent,
            WebElementSelector.selectByTagName(TAG_NAME),
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

    public static <T extends AbstractClarityComboboxOptionComponent> ClaritySingleSelectComboboxContainerComponent<
        T
    > byTestId(SeleniumComponent parent, String testId, SeleniumComponentFactory<T> optionFactory) {
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

    public static <T extends AbstractClarityComboboxOptionComponent> ClaritySingleSelectComboboxContainerComponent<
        T
    > byFormControlName(SeleniumComponent parent, String formControlName, SeleniumComponentFactory<T> optionFactory) {
        return new ClaritySingleSelectComboboxContainerComponent<>(
            parent,
            WebElementSelector.selectByTagNameContainingFormControlName(TAG_NAME, formControlName),
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

    public static <T extends AbstractClarityComboboxOptionComponent> ClaritySingleSelectComboboxContainerComponent<
        T
    > byLabel(SeleniumComponent parent, String partialLabel, SeleniumComponentFactory<T> optionFactory) {
        return new ClaritySingleSelectComboboxContainerComponent<>(
            parent,
            WebElementSelector.selectByTagNameContainingLabel(TAG_NAME, partialLabel),
            optionFactory
        );
    }

    public ClaritySingleSelectComboboxContainerComponent(
        SeleniumComponent parent,
        WebElementSelector selector,
        SeleniumComponentFactory<T> optionFactory
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
