/**
 *
 */
package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.function.Predicate;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.TestUtils;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author Daniel Furtlehner
 * @param <ENTRY_TYPE> type of entry
 */
public class UlComponent<ENTRY_TYPE extends AbstractSeleniumComponent> extends AbstractSeleniumComponent
{
    private final SeleniumComponentListFactory<ENTRY_TYPE> listEntries;

    public UlComponent(SeleniumComponent parent, SeleniumComponentFactory<ENTRY_TYPE> componentFactory)
    {
        this(parent, selectByTagName("ul"), componentFactory);
    }

    public UlComponent(SeleniumComponent parent, WebElementSelector selector,
        SeleniumComponentFactory<ENTRY_TYPE> componentFactory)
    {
        super(parent, selector);

        listEntries = new SeleniumComponentListFactory<>(this, selectByTagName("li"), componentFactory);
    }

    public SeleniumComponentList<ENTRY_TYPE> getItems()
    {
        return listEntries.findAll();
    }

    public boolean containsItem(Predicate<ENTRY_TYPE> predicate)
    {
        return TestUtils.keepTrying(1, () -> listEntries.find(predicate)).isPresent();
    }

    public SeleniumComponentList<ENTRY_TYPE> getVisibleItems()
    {
        return getItems().filter($ -> $.isVisible());
    }

}
