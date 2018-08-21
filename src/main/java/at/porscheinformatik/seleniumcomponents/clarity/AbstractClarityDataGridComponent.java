/**
 * 
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import org.openqa.selenium.Keys;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.AbstractSeleniumContainer;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;
import at.porscheinformatik.seleniumcomponents.component.InputComponent;

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

        column.stringFilter(filterQuery);
    }

    public SeleniumComponentList<RowT> getEntries()
    {
        return body.findAllChilds();
    }

    private static class DataGridHeadComponent extends AbstractSeleniumContainer<DataGridRowComponent>
    {
        public DataGridHeadComponent(SeleniumComponent parent)
        {
            super(parent, selectByClassName("datagrid-head"), selectByClassName("datagrid-row"),
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
        public DataGridBodyComponent(SeleniumComponent parent, SeleniumComponentFactory<RowT> rowFactory)
        {
            super(parent, selectByClassName("datagrid-body"), selectByClassName("datagrid-row"), rowFactory);
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
        private final HtmlComponent filterContainer = new HtmlComponent(this, selectByTagName("clr-dg-filter"));
        private final InputComponent stringFilter = new InputComponent(filterContainer);

        public DataGridColumnComponent(SeleniumComponent parent, WebElementSelector selector)
        {
            super(parent, selector);
        }

        public void stringFilter(String filterQuery)
        {
            filterContainer.click();

            stringFilter.clear();
            stringFilter.sendKeys(filterQuery);

            stringFilter.sendKeys(Keys.ENTER);
        }
    }
}
