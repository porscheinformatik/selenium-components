package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.SeleniumAsserts.*;
import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;
import static org.hamcrest.Matchers.*;

import java.util.function.Predicate;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityComboboxOptionsComponent.AbstractClarityComboboxOptionComponent;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityMultiSelectComboboxComponent.AbstractClarityComboboxLabelPillComponent;
import at.porscheinformatik.seleniumcomponents.component.ButtonComponent;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

/**
 * @author TechScar
 */
public class ClarityMultiSelectComboboxComponent<OPTION_TYPE extends AbstractClarityComboboxOptionComponent,
    PILL_TYPE extends AbstractClarityComboboxLabelPillComponent> extends AbstractClarityComboboxComponent<OPTION_TYPE>
{
    private final SeleniumComponentListFactory<PILL_TYPE> labelPills;

    // ---

    public ClarityMultiSelectComboboxComponent(SeleniumComponent parent,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory, SeleniumComponentFactory<PILL_TYPE> pillFactory)
    {
        this(parent, selectByTagName("clr-combobox"), optionFactory, pillFactory);
    }

    public ClarityMultiSelectComboboxComponent(SeleniumComponent parent, WebElementSelector selector,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory, SeleniumComponentFactory<PILL_TYPE> pillFactory)
    {
        super(parent, selector, optionFactory);

        labelPills = new SeleniumComponentListFactory<>(this, selectByClassName("label-combobox-pill"), pillFactory);
    }

    // ---

    @Override
    public void clear()
    {
        labelPills.findAll().forEach(AbstractClarityComboboxLabelPillComponent::delete);
    }

    public PILL_TYPE findLabelPill(Predicate<PILL_TYPE> predicate)
    {
        return labelPills.find(predicate);
    }

    public SeleniumComponentListFactory<PILL_TYPE> getLabelPills()
    {
        return labelPills;
    }

    public void removeByLabel(String label)
    {
        PILL_TYPE foundPill =
            assertThatSoon(() -> labelPills.find(pill -> pill.content.getText().contains(label)), notNullValue());

        foundPill.delete();
    }

    // ---

    public static abstract class AbstractClarityComboboxLabelPillComponent extends AbstractSeleniumComponent
    {
        // add any custom components in your implementation

        protected final HtmlComponent content = new HtmlComponent(this, selectByClassName("clr-combobox-pill-content"));

        private final ButtonComponent deleteButton =
            new ButtonComponent(this, selectByClassName("clr-combobox-remove-btn"));

        // ---

        protected AbstractClarityComboboxLabelPillComponent(SeleniumComponent parent)
        {
            this(parent, selectByClassName("label-combobox-pill"));
        }

        protected AbstractClarityComboboxLabelPillComponent(SeleniumComponent parent, WebElementSelector selector)
        {
            super(parent, selector);
        }

        // ---

        protected void delete()
        {
            deleteButton.click();
        }
    }
}
