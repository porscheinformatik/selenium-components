package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumContainer;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * Represents a default THEAD element with {@link TrComponent}s as children.
 *
 * @author ham
 */
public class TheadComponent extends AbstractSeleniumContainer<TrComponent>
{

    public TheadComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("thead"));
    }

    public TheadComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, WebElementSelector.selectByTagName("thead"), WebElementSelector.selectByTagName("tr"),
            TrComponent::new);
    }

}
