package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author cet
 */
public class LinkComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent {

    public static LinkComponent within(SeleniumComponent parent) {
        return new LinkComponent(parent, selectByTagName("a"));
    }

    public static LinkComponent byTestId(SeleniumComponent parent, String testId) {
        return new LinkComponent(parent, WebElementSelector.selectByTestId("a", testId));
    }

    public static LinkComponent byText(SeleniumComponent parent, String partialText) {
        return new LinkComponent(parent, selectByText("a", partialText));
    }

    public static LinkComponent byHref(SeleniumComponent parent, String partialHref) {
        String xpath = String.format(".//a[contains(@href, '%s')]", partialHref);

        return new LinkComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public LinkComponent(SeleniumComponent parent) {
        this(parent, selectByTagName("a"));
    }

    public LinkComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public String getHref() {
        return getAttribute("href");
    }

    @Override
    public String getText() {
        return super.getText();
    }
}
