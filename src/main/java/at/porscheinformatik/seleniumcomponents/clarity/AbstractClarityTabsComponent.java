/**
 *
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.List;
import java.util.stream.Collectors;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumAsserts;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumMatchers;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.ButtonComponent;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

/**
 * @author Daniel Furtlehner
 */
public abstract class AbstractClarityTabsComponent extends AbstractSeleniumComponent
{
    private final SeleniumComponentListFactory<ButtonComponent> nav =
        new SeleniumComponentListFactory<>(this, selectByTagName("button"), ButtonComponent::new);

    public AbstractClarityTabsComponent(SeleniumComponent parent)
    {
        super(parent, selectByTagName("clr-tabs"));
    }

    public AbstractClarityTabsComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public List<String> getTabLabels()
    {
        return getVisibleNavigationItems() //
            .map(ButtonComponent::getLabel)
            .collect(Collectors.toList());
    }

    public void clickTabAtIndex(int index)
    {
        getVisibleNavigationItems().get(index).click();

        HtmlComponent tab = new HtmlComponent(this, selectById("clr-tab-content-" + index));

        SeleniumAsserts.assertThatSoon(() -> tab, SeleniumMatchers.isVisible());
    }

    private SeleniumComponentList<ButtonComponent> getVisibleNavigationItems()
    {
        return nav //
            .findAll()
            .filter(ButtonComponent::isVisible);
    }
}
