package at.porscheinformatik.seleniumcomponents.clarity;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumAsserts;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumUtils;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.hamcrest.Matchers;

/**
 * @param <T> the type of item
 * @author cet
 */
public class ClarityDropDownComponent<T extends AbstractSeleniumComponent & ActiveSeleniumComponent>
    extends AbstractSeleniumComponent {

    public final T toggle;

    private final HtmlComponent entryWrapper = new HtmlComponent(
        new ClarityOverlayContainerComponent(this),
        WebElementSelector.selectByTagName("clr-dropdown-menu")
    );

    public final SeleniumComponentListFactory<T> entries;

    public ClarityDropDownComponent(SeleniumComponent parent, SeleniumComponentFactory<T> entryFactory) {
        this(parent, WebElementSelector.selectByTagName("clr-dropdown"), entryFactory);
    }

    public ClarityDropDownComponent(
        SeleniumComponent parent,
        WebElementSelector selector,
        SeleniumComponentFactory<T> entryFactory
    ) {
        super(parent, selector);
        toggle = entryFactory.create(this, WebElementSelector.selectByClassName("dropdown-toggle"));

        entries = new SeleniumComponentListFactory<>(
            entryWrapper,
            WebElementSelector.selectByClassName("dropdown-item"),
            entryFactory
        );
    }

    public String getLabel() {
        return SeleniumUtils.getText(toggle);
    }

    public boolean isExpanded() {
        String expanded = SeleniumAsserts.assertThatSoon(
            () -> SeleniumUtils.getAttribute(toggle, "aria-expanded"),
            Matchers.oneOf("true", "false")
        );

        return "true".equals(expanded);
    }

    public void expand() {
        SeleniumUtils.keepTrying(() -> {
            if (isExpanded()) {
                return true;
            }

            toggle.click();

            return null;
        });
    }

    public void shrink() {
        SeleniumUtils.keepTrying(() -> {
            if (!isExpanded()) {
                return true;
            }

            toggle.click();

            return null;
        });
    }

    public List<T> getEntries() {
        return entries.findAll().toList();
    }

    public List<String> getEntryLabels() {
        expand();

        try {
            return entries.findAll().map(SeleniumUtils::getText).map(String::trim).toList();
        } finally {
            shrink();
        }
    }

    public void clickEntry(String entryLabel) {
        clickEntry(currentEntry -> Objects.equals(entryLabel, SeleniumUtils.getText(currentEntry).trim()));
    }

    public void clickEntryByTestId(String testId) {
        clickEntry(currentEntry -> Objects.equals(testId, SeleniumUtils.getAttribute(currentEntry, "data-testid")));
    }

    public void clickEntry(Predicate<T> filter) {
        expand();

        T entry = entries.find(filter);

        assertThat(entry, notNullValue());

        entry.click();
    }
}
