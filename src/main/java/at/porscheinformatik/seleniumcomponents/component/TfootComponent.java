package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumContainer;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * Represents a default TFOOT element with {@link TrComponent}s as children.
 *
 * @author ham
 */
public class TfootComponent extends AbstractSeleniumContainer<TrComponent>
{

    public TfootComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("tfoot"));
    }

    public TfootComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, WebElementSelector.selectByTagName("tfoot"), WebElementSelector.selectByTagName("tr"),
            TrComponent::new);
    }

}
