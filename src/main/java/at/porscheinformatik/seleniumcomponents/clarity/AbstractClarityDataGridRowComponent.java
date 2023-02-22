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
import at.porscheinformatik.seleniumcomponents.clarity.ClarityIconComponent.ClarityIconDirection;
import at.porscheinformatik.seleniumcomponents.component.ButtonComponent;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

/**
 * @author Daniel Furtlehner
 */
public abstract class AbstractClarityDataGridRowComponent extends AbstractSeleniumComponent
{
    protected final SeleniumComponentListFactory<HtmlComponent> cells;
    protected final HtmlComponent rowDetail = new HtmlComponent(this, selectByTagName("clr-dg-row-detail"));

    private final ButtonComponent expandButton =
        new ButtonComponent(this, selectByClassName("datagrid-expandable-caret-button"));
    private final ClarityIconComponent caret = ClarityIconComponent.in(expandButton);

    // ---

    public AbstractClarityDataGridRowComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);

        cells = SeleniumComponentListFactory.of(this, selectByClassName("datagrid-cell"), HtmlComponent::new);
    }

    // ---

    public boolean isCollapsed()
    {
        return caret.getDirection() == ClarityIconDirection.RIGHT;
    }

    public boolean isExpanded()
    {
        return caret.getDirection() == ClarityIconDirection.DOWN;
    }

    public void collapse()
    {
        if (isExpanded())
        {
            expandButton.click();
        }
    }

    public void expand()
    {
        if (isCollapsed())
        {
            expandButton.click();
        }
    }

    protected <T> T mapCell(int index, Function<HtmlComponent, T> mapping)
    {
        HtmlComponent cell = cells.findAll().get(index);

        return mapping.apply(cell);
    }
}
