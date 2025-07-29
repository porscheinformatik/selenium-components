package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityComboboxOptionsComponent.AbstractClarityComboboxOptionComponent;
import java.util.function.Predicate;

/**
 * @author TechScar
 */
public class ClarityMultiSelectComboboxContainerComponent<
    OPTION_TYPE extends AbstractClarityComboboxOptionComponent,
    PILL_TYPE extends AbstractClarityComboboxLabelPillComponent
>
    extends ClarityFormControlContainer {

    public final ClarityMultiSelectComboboxComponent<OPTION_TYPE, PILL_TYPE> multiSelect;

    public static ClarityMultiSelectComboboxContainerComponent<
        DefaultClarityComboboxOptionComponent,
        DefaultClarityComboboxLabelPillComponent
    > within(SeleniumComponent parent) {
        return within(
            parent,
            DefaultClarityComboboxOptionComponent::new,
            DefaultClarityComboboxLabelPillComponent::new
        );
    }

    public static <
        OPTION_TYPE extends AbstractClarityComboboxOptionComponent,
        PILL_TYPE extends AbstractClarityComboboxLabelPillComponent
    > ClarityMultiSelectComboboxContainerComponent<OPTION_TYPE, PILL_TYPE> within(
        SeleniumComponent parent,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory,
        SeleniumComponentFactory<PILL_TYPE> pillFactory
    ) {
        return new ClarityMultiSelectComboboxContainerComponent<>(
            parent,
            selectByTagName("clr-combobox-container"),
            optionFactory,
            pillFactory
        );
    }

    public static ClarityMultiSelectComboboxContainerComponent<
        DefaultClarityComboboxOptionComponent,
        DefaultClarityComboboxLabelPillComponent
    > byTestId(SeleniumComponent parent, String testId) {
        return byTestId(
            parent,
            testId,
            DefaultClarityComboboxOptionComponent::new,
            DefaultClarityComboboxLabelPillComponent::new
        );
    }

    public static <
        OPTION_TYPE extends AbstractClarityComboboxOptionComponent,
        PILL_TYPE extends AbstractClarityComboboxLabelPillComponent
    > ClarityMultiSelectComboboxContainerComponent<OPTION_TYPE, PILL_TYPE> byTestId(
        SeleniumComponent parent,
        String testId,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory,
        SeleniumComponentFactory<PILL_TYPE> pillFactory
    ) {
        return new ClarityMultiSelectComboboxContainerComponent<>(
            parent,
            selectByTestId(testId),
            optionFactory,
            pillFactory
        );
    }

    public static ClarityMultiSelectComboboxContainerComponent<
        DefaultClarityComboboxOptionComponent,
        DefaultClarityComboboxLabelPillComponent
    > byLabel(SeleniumComponent parent, String partialLabel) {
        return byLabel(
            parent,
            partialLabel,
            DefaultClarityComboboxOptionComponent::new,
            DefaultClarityComboboxLabelPillComponent::new
        );
    }

    public static <
        OPTION_TYPE extends AbstractClarityComboboxOptionComponent,
        PILL_TYPE extends AbstractClarityComboboxLabelPillComponent
    > ClarityMultiSelectComboboxContainerComponent<OPTION_TYPE, PILL_TYPE> byLabel(
        SeleniumComponent parent,
        String partialLabel,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory,
        SeleniumComponentFactory<PILL_TYPE> pillFactory
    ) {
        return new ClarityMultiSelectComboboxContainerComponent<>(
            parent,
            selectByTagNameContainingLabel("clr-combobox-container", partialLabel),
            optionFactory,
            pillFactory
        );
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String, SeleniumComponentFactory, SeleniumComponentFactory)}
     * instead
     */
    @Deprecated(forRemoval = true)
    public static <
        OPTION_TYPE extends AbstractClarityComboboxOptionComponent,
        PILL_TYPE extends AbstractClarityComboboxLabelPillComponent
    > ClarityMultiSelectComboboxContainerComponent<OPTION_TYPE, PILL_TYPE> bySeleniumKey(
        SeleniumComponent parent,
        String seleniumKey,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory,
        SeleniumComponentFactory<PILL_TYPE> pillFactory
    ) {
        return new ClarityMultiSelectComboboxContainerComponent<>(
            parent,
            selectBySeleniumKey(seleniumKey),
            optionFactory,
            pillFactory
        );
    }

    public ClarityMultiSelectComboboxContainerComponent(
        SeleniumComponent parent,
        WebElementSelector selector,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory,
        SeleniumComponentFactory<PILL_TYPE> pillFactory
    ) {
        super(parent, selector);
        multiSelect = new ClarityMultiSelectComboboxComponent<>(this, optionFactory, pillFactory);
    }

    public void clear() {
        multiSelect.clear();
    }

    public void selectByLabel(String partialText) {
        multiSelect.selectByLabel(partialText);
    }

    public PILL_TYPE findLabelPill(Predicate<PILL_TYPE> predicate) {
        return multiSelect.findLabelPill(predicate);
    }

    public PILL_TYPE getPillByLabel(String partialText) {
        return multiSelect.getPillByLabel(partialText);
    }

    public void assertSelected(String partialText) {
        multiSelect.assertSelected(partialText);
    }

    public SeleniumComponentListFactory<PILL_TYPE> getLabelPills() {
        return multiSelect.getLabelPills();
    }

    public void removeByLabel(String partialText) {
        multiSelect.removeByLabel(partialText);
    }
}
