package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityComboboxOptionsComponent.AbstractClarityComboboxOptionComponent;

public class DefaultClarityComboboxOptionComponent extends AbstractClarityComboboxOptionComponent {

    public DefaultClarityComboboxOptionComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public String getLabel() {
        return getText();
    }
}
