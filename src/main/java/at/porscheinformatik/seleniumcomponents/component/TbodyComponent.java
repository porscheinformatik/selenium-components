package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumContainer;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * Represents a default TBODY element with {@link TrComponent}s as children.
 *
 * @author ham
 */
public class TbodyComponent extends AbstractSeleniumContainer<TrComponent>
{

    public TbodyComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("tbody"));
    }

    public TbodyComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, WebElementSelector.selectByTagName("tbody"), WebElementSelector.selectByTagName("tr"),
            TrComponent::new);
    }

}
