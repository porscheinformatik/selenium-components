/**
 *
 */
package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.DeactivateableSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SelectableSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author Daniel Furtlehner
 */
public class RadioComponent extends AbstractSeleniumComponent
    implements SelectableSeleniumComponent, DeactivateableSeleniumComponent
{

    /**
     * Creates the component.
     *
     * @param parent the parent
     * @param selector the selector for the element
     */
    public RadioComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public RadioComponent(SeleniumComponent parent)
    {
        this(parent, selectByCss("input[type=\"radio\"]"));
    }
}
