package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityComboboxOptionsComponent.AbstractClarityComboboxOptionComponent;
import java.util.List;
import java.util.function.Predicate;

public class ClarityComboboxOptionsComponent<OPTION_TYPE extends AbstractClarityComboboxOptionComponent>
    extends AbstractSeleniumComponent
    implements WithClarityControlHelpers {

    private final SeleniumComponentListFactory<OPTION_TYPE> options;

    private final SeleniumComponentFactory<OPTION_TYPE> optionFactory;

    // ---

    /**
     * {@code <clr-options class="clr-combobox-options">} is not child node of the {@code <clr-combobox>} node in the
     * DOM, but appended at the very end, just before the {@code </body>} tag. Therefore, it must be queried from the
     * DOM root. There can only be one active {@code <clr-options class="clr-combobox-options">} dropdown at a time, so
     * this should always work (considering a timeout).
     *
     * @param parent The parent component
     */
    public ClarityComboboxOptionsComponent(
        SeleniumComponent parent,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory
    ) {
        this(parent, selectByClassName("clr-combobox-options"), optionFactory);
    }

    public ClarityComboboxOptionsComponent(
        SeleniumComponent parent,
        WebElementSelector selector,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory
    ) {
        super(parent, selector);
        this.optionFactory = optionFactory;
        options = new SeleniumComponentListFactory<>(this, selectByClassName("clr-combobox-option"), optionFactory);
    }

    // ---

    /**
     * @param predicate the predicate to find the option
     * @return the option or null
     * @deprecated avoid for performance reasons
     */
    @Deprecated(forRemoval = true)
    public OPTION_TYPE findOption(Predicate<OPTION_TYPE> predicate) {
        return options.find(predicate);
    }

    public OPTION_TYPE getOptionByLabel(String label) {
        return optionFactory.create(this, WebElementSelector.selectByText("clr-option", label));
    }

    public void selectOptionByLabel(String label) {
        getOptionByLabel(label).click();
    }

    public List<String> getOptionLabels() {
        return options.findAll().stream().map(OPTION_TYPE::getText).toList();
    }

    public abstract static class AbstractClarityComboboxOptionComponent
        extends AbstractSeleniumComponent
        implements ActiveSeleniumComponent {

        // add any custom components in your implementation

        protected AbstractClarityComboboxOptionComponent(SeleniumComponent parent) {
            super(parent, selectByClassName("clr-combobox-option"));
        }

        protected AbstractClarityComboboxOptionComponent(SeleniumComponent parent, WebElementSelector selector) {
            super(parent, selector);
        }

        // ---

        @Override
        protected String getText() {
            return super.getText();
        }
    }
}
