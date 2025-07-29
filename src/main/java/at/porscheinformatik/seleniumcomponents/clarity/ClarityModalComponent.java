package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

public class ClarityModalComponent extends AbstractClarityModalComponent {

    public static ClarityModalComponent within(SeleniumComponent parent) {
        return new ClarityModalComponent(parent, selectByTagName("clr-modal"));
    }

    public static ClarityModalComponent byTestId(SeleniumComponent parent, String testId) {
        return new ClarityModalComponent(parent, selectByTestId("clr-modal", testId));
    }

    public ClarityModalComponent(SeleniumComponent parent) {
        super(parent);
    }

    public ClarityModalComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }
}
