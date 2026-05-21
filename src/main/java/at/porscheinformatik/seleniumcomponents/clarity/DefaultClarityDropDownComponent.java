package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

/**
 * @author cet
 */
public class DefaultClarityDropDownComponent extends ClarityDropDownComponent<HtmlComponent> {

    public DefaultClarityDropDownComponent(SeleniumComponent parent) {
        super(parent, HtmlComponent::new);
    }

    public DefaultClarityDropDownComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector, HtmlComponent::new);
    }
}
