package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author cet
 */
public class OptionComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent {

    public OptionComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    @Override
    public void unselect() {
        throw new UnsupportedOperationException("An option cannot be unselected. Another one must be selected instead");
    }

    public String getValue() {
        return getAttribute("value");
    }

    public String getLabel() {
        return getText();
    }
}
