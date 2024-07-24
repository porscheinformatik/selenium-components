package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.function.Predicate;

import at.porscheinformatik.seleniumcomponents.SeleniumAsserts;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumMatchers;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityComboboxOptionsComponent.AbstractClarityComboboxOptionComponent;

/**
 * @author TechScar
 */
public class ClarityMultiSelectComboboxComponent<OPTION_TYPE extends AbstractClarityComboboxOptionComponent,
    PILL_TYPE extends AbstractClarityComboboxLabelPillComponent>
    extends AbstractClarityComboboxComponent<OPTION_TYPE>
{
    private final SeleniumComponentFactory<PILL_TYPE> pillFactory;
    private final SeleniumComponentListFactory<PILL_TYPE> labelPills;

    protected ClarityMultiSelectComboboxComponent(SeleniumComponent parent,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory, SeleniumComponentFactory<PILL_TYPE> pillFactory)
    {
        this(parent, selectByTagName("clr-combobox"), optionFactory, pillFactory);
    }

    protected ClarityMultiSelectComboboxComponent(SeleniumComponent parent, WebElementSelector selector,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory, SeleniumComponentFactory<PILL_TYPE> pillFactory)
    {
        super(parent, selector, optionFactory);

        this.pillFactory = pillFactory;

        labelPills = new SeleniumComponentListFactory<>(this, selectByClassName("label-combobox-pill"), pillFactory);
    }

    @Override
    public void clear()
    {
        labelPills.findAll().forEach(AbstractClarityComboboxLabelPillComponent::delete);
    }

    public PILL_TYPE findLabelPill(Predicate<PILL_TYPE> predicate)
    {
        return labelPills.find(predicate);
    }

    public PILL_TYPE getPillByLabel(String partialText)
    {
        return pillFactory.create(this, selectByClassNameAndText("span", "label-combobox-pill", partialText));
    }

    @Override
    public void assertSelected(String partialText)
    {
        SeleniumAsserts.assertThatSoon("\"" + partialText + "\" is selected", () -> getPillByLabel(partialText),
            SeleniumMatchers.isReady());
    }

    public SeleniumComponentListFactory<PILL_TYPE> getLabelPills()
    {
        return labelPills;
    }

    public void removeByLabel(String partialText)
    {
        getPillByLabel(partialText).delete();
    }
}
