/**
 * 
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.AbstractSeleniumContainer;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

/**
 * @author Daniel Furtlehner
 * @param <RowT> type of rows in this grid
 */
public abstract class AbstractClarityDataGridComponent<RowT extends AbstractSeleniumComponent>
    extends AbstractSeleniumComponent
{
    private final HtmlComponent gridContainer = new HtmlComponent(this, selectByClassName("datagrid"));
    private final DataGridHeadComponent head = new DataGridHeadComponent(gridContainer);
    private final DataGridBodyComponent<RowT> body;

    protected AbstractClarityDataGridComponent(SeleniumComponent parent, SeleniumComponentFactory<RowT> rowFactory)
    {
        super(parent, selectByTagName("clr-datagrid"));

        body = new DataGridBodyComponent<>(this, rowFactory);
    }

    public void stringFilterColumn(int columnIndex, String filterQuery)
    {
        DataGridColumnComponent column = head.getColumn(columnIndex);

        column.stringFilter.filter(filterQuery);
    }

    public SeleniumComponentList<RowT> getEntries()
    {
        return body.findAllChilds();
    }

    private static class DataGridHeadComponent extends AbstractSeleniumContainer<DataGridRowComponent>
    {
        DataGridHeadComponent(SeleniumComponent parent)
        {
            super(parent, selectByClassName("datagrid-header"), selectByClassName("datagrid-row"),
                DataGridRowComponent::new);
        }

        public DataGridColumnComponent getColumn(int index)
        {
            return findAllChilds().get(0).findAllChilds().get(index);
        }
    }

    private static class DataGridBodyComponent<RowT extends AbstractSeleniumComponent>
        extends AbstractSeleniumContainer<RowT>
    {
        DataGridBodyComponent(SeleniumComponent parent, SeleniumComponentFactory<RowT> rowFactory)
        {
            super(parent, selectByClassName("datagrid"), selectByCss(".datagrid-table > .datagrid-row"), rowFactory);
        }
    }

    private static class DataGridRowComponent extends AbstractSeleniumContainer<DataGridColumnComponent>
    {
        protected DataGridRowComponent(SeleniumComponent parent, WebElementSelector selector)
        {
            super(parent, selector, selectByClassName("datagrid-column"), DataGridColumnComponent::new);
        }
    }

    private static class DataGridColumnComponent extends AbstractSeleniumComponent
    {
        // With newer Clarity versions the datagrid filters are in the body and not in the column anymore
        public final ClarityDataGridStringFilterComponent stringFilter = new ClarityDataGridStringFilterComponent(this);

        DataGridColumnComponent(SeleniumComponent parent, WebElementSelector selector)
        {
            super(parent, selector);
        }
    }
}
