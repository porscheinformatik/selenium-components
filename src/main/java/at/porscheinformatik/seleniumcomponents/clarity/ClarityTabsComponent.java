package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentTemplate;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

public class ClarityTabsComponent extends AbstractSeleniumComponent
{

    private final HtmlComponent tabList = new HtmlComponent(this, selectByCss("ul[role='tablist']"));

    private final SeleniumComponentListFactory<ClarityTabLinkComponent> tabLinks =
        SeleniumComponentListFactory.of(tabList,
            SeleniumComponentTemplate.of(selectByTagName("li"), ClarityTabLinkComponent::new));

    public ClarityTabsComponent(SeleniumComponent parent)
    {
        super(parent, WebElementSelector.selectByTagName("clr-tabs"));
    }

    /**
     * @deprecated Use {@link #ClarityTabsComponent(SeleniumComponent, WebElementSelector)} instead
     */
    @Deprecated(forRemoval = true)
    public ClarityTabsComponent(SeleniumComponent parent, String seleniumKey)
    {
        super(parent, WebElementSelector.selectByTestIdOrSeleniumKey("clr-tabs", seleniumKey));
    }

    public ClarityTabsComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public ClarityTabLinkComponent getTabLink(int index)
    {
        return new ClarityTabLinkComponent(tabList, selectByIndex("li", index));
    }

    public void activateTab(int index)
    {
        getTabLink(index).activate();
    }

    public SeleniumComponentList<ClarityTabLinkComponent> getTabLinks()
    {
        return tabLinks.findAll();
    }
}
