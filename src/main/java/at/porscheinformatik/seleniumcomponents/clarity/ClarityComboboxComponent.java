package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.SeleniumAsserts.*;
import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;
import static org.hamcrest.Matchers.*;

import javax.annotation.Nullable;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.ButtonComponent;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;
import at.porscheinformatik.seleniumcomponents.component.InputComponent;

/**
 * @author scar
 */
public class ClarityComboboxComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{
    private final InputComponent input = new InputComponent(this);
    private final SeleniumComponentListFactory<ClarityComboboxLabelPillComponent> labelPills =
        new SeleniumComponentListFactory<>(this, selectByClassName("label-combobox-pill"),
            ClarityComboboxLabelPillComponent::new);
    private final ClarityOptionsComponent options = new ClarityOptionsComponent(this);

    // ---

    public ClarityComboboxComponent(SeleniumComponent parent)
    {
        this(parent, selectByTagName("clr-combobox"));
    }

    public ClarityComboboxComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    // ---

    public void selectByLabel(String label)
    {
        input.clear();
        input.enter(label);

        assertThatSoon(options::isVisible, is(true));

        ClarityOptionComponent foundOption = assertThatSoon(() -> options.findOption(label), notNullValue());

        foundOption.click();
    }

    public void removeByLabel(String label)
    {
        ClarityComboboxLabelPillComponent foundLabel =
            assertThatSoon(() -> labelPills.find(labelPill -> labelPill.getLabel().contains(label)), notNullValue());

        foundLabel.delete();
    }

    public void clearAll()
    {
        assertThatLater(() -> {
            labelPills.findAll().forEach(ClarityComboboxLabelPillComponent::delete);

            return labelPills.findAll().size();
        }, equalTo(0));
    }

    // ===

    protected class ClarityComboboxLabelPillComponent extends AbstractSeleniumComponent
    {
        private final HtmlComponent label = new HtmlComponent(this, selectByClassName("clr-combobox-pill-content"));
        private final ClarityIconComponent icon = new ClarityIconComponent(label);
        private final ButtonComponent deleteButton =
            new ButtonComponent(this, selectByClassName("clr-combobox-remove-btn"));

        // ---

        protected ClarityComboboxLabelPillComponent(SeleniumComponent parent)
        {
            this(parent, selectByClassName("label-combobox-pill"));
        }

        protected ClarityComboboxLabelPillComponent(SeleniumComponent parent, WebElementSelector selector)
        {
            super(parent, selector);
        }

        // ---

        @Nullable
        protected String getIcon()
        {
            return icon.getShape();
        }

        protected String getLabel()
        {
            return label.getText();
        }

        protected void delete()
        {
            deleteButton.click();
        }
    }
}
