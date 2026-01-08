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
    O extends AbstractClarityComboboxOptionComponent,
    P extends AbstractClarityComboboxLabelPillComponent
>
    extends ClarityFormControlContainer {

    public final ClarityMultiSelectComboboxComponent<O, P> multiSelect;

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
        O extends AbstractClarityComboboxOptionComponent,
        P extends AbstractClarityComboboxLabelPillComponent
    > ClarityMultiSelectComboboxContainerComponent<O, P> within(
        SeleniumComponent parent,
        SeleniumComponentFactory<O> optionFactory,
        SeleniumComponentFactory<P> pillFactory
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
        O extends AbstractClarityComboboxOptionComponent,
        P extends AbstractClarityComboboxLabelPillComponent
    > ClarityMultiSelectComboboxContainerComponent<O, P> byTestId(
        SeleniumComponent parent,
        String testId,
        SeleniumComponentFactory<O> optionFactory,
        SeleniumComponentFactory<P> pillFactory
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
        O extends AbstractClarityComboboxOptionComponent,
        P extends AbstractClarityComboboxLabelPillComponent
    > ClarityMultiSelectComboboxContainerComponent<O, P> byLabel(
        SeleniumComponent parent,
        String partialLabel,
        SeleniumComponentFactory<O> optionFactory,
        SeleniumComponentFactory<P> pillFactory
    ) {
        return new ClarityMultiSelectComboboxContainerComponent<>(
            parent,
            selectByTagNameContainingLabel("clr-combobox-container", partialLabel),
            optionFactory,
            pillFactory
        );
    }

    public ClarityMultiSelectComboboxContainerComponent(
        SeleniumComponent parent,
        WebElementSelector selector,
        SeleniumComponentFactory<O> optionFactory,
        SeleniumComponentFactory<P> pillFactory
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

    public P findLabelPill(Predicate<P> predicate) {
        return multiSelect.findLabelPill(predicate);
    }

    public P getPillByLabel(String partialText) {
        return multiSelect.getPillByLabel(partialText);
    }

    public void assertSelected(String partialText) {
        multiSelect.assertSelected(partialText);
    }

    public SeleniumComponentListFactory<P> getLabelPills() {
        return multiSelect.getLabelPills();
    }

    public void removeByLabel(String partialText) {
        multiSelect.removeByLabel(partialText);
    }
}
