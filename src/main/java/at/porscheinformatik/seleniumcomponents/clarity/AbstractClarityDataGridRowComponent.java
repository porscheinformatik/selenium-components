/**
 * 
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.function.Function;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

/**
 * @author Daniel Furtlehner
 */
public abstract class AbstractClarityDataGridRowComponent extends AbstractSeleniumComponent
{
    protected final SeleniumComponentListFactory<HtmlComponent> cells;

    public AbstractClarityDataGridRowComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);

        cells = SeleniumComponentListFactory.of(this, selectByClassName("datagrid-cell"), HtmlComponent::new);
    }

    protected <T> T mapCell(int index, Function<HtmlComponent, T> mapping)
    {
        HtmlComponent cell = cells.findAll().get(index);

        return mapping.apply(cell);
    }

}
