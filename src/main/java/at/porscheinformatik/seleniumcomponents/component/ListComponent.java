package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumContainer;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * Default implements of an {@link AbstractSeleniumContainer}.
 *
 * @author ham
 * @param <AnySeleniumComponent> the type of child
 */
public class ListComponent<AnySeleniumComponent extends SeleniumComponent>
    extends AbstractSeleniumContainer<AnySeleniumComponent>
{

    public ListComponent(SeleniumComponent parent, WebElementSelector selector, WebElementSelector childSelector,
        SeleniumComponentFactory<AnySeleniumComponent> childFactory)
    {
        super(parent, selector, childSelector, childFactory);
    }

}
