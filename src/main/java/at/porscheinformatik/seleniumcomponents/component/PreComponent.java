/**
 *
 */
package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author Daniel Furtlehner
 */
public class PreComponent extends AbstractSeleniumComponent {

    public PreComponent(SeleniumComponent parent) {
        super(parent, selectByTagName("pre"));
    }

    public PreComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public String getContent() {
        return getText();
    }
}
