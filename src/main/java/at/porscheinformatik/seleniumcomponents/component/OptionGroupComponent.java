package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumContainer;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author cet
 */
public class OptionGroupComponent extends AbstractSeleniumContainer<OptionComponent> {

    public OptionGroupComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector, WebElementSelector.selectByTagName("option"), OptionComponent::new);
    }

    public String getLabel() {
        return getAttribute("label");
    }
}
