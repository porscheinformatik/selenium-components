package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.ButtonComponent;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;
import at.porscheinformatik.seleniumcomponents.component.InputComponent;
import at.porscheinformatik.seleniumcomponents.component.SelectComponent;

public class ClarityDataGridFooterComponent extends AbstractSeleniumComponent
{
    private final ClarityDataGridPaginationComponent pagination = new ClarityDataGridPaginationComponent(this);

    public ClarityDataGridFooterComponent(SeleniumComponent parent)
    {
        super(parent, selectByTagName("clr-dg-footer"));
    }

    public ClarityDataGridFooterComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    // ---

    public int getCurrentPage()
    {
        return pagination.paginationList.getCurrentPage();
    }

    public int getTotalPages()
    {
        return pagination.paginationList.getTotalPages();
    }

    public void firstPage()
    {
        pagination.paginationList.first();
    }

    public void previousPage()
    {
        pagination.paginationList.previous();
    }

    public void nextPage()
    {
        pagination.paginationList.next();
    }

    public void lastPage()
    {
        pagination.paginationList.last();
    }

    public void selectPageSize(int pageSize)
    {
        pagination.pageSize.selectPageSize(pageSize);
    }

    // ===

    private static class ClarityDataGridPaginationComponent extends AbstractSeleniumComponent
    {
        private final ClarityDataGridPageSize pageSize = new ClarityDataGridPageSize(this);
        private final ClarityDataGridPaginationList paginationList = new ClarityDataGridPaginationList(this);

        // ---

        ClarityDataGridPaginationComponent(SeleniumComponent parent)
        {
            super(parent, selectByTagName("clr-dg-pagination"));
        }

        ClarityDataGridPaginationComponent(SeleniumComponent parent, WebElementSelector selector)
        {
            super(parent, selector);
        }
    }

    private static class ClarityDataGridPageSize extends AbstractSeleniumComponent
    {
        private final SelectComponent pageSizeSelect = new SelectComponent(this);

        // ---

        ClarityDataGridPageSize(SeleniumComponent parent)
        {
            super(parent, selectByTagName("clr-dg-page-size"));
        }

        ClarityDataGridPageSize(SeleniumComponent parent, WebElementSelector selector)
        {
            super(parent, selector);
        }

        // ---

        public void selectPageSize(int pageSize)
        {
            pageSizeSelect.selectByLabel(String.valueOf(pageSize));
        }
    }

    private static class ClarityDataGridPaginationList extends AbstractSeleniumComponent
    {
        private final ButtonComponent firstButton = new ButtonComponent(this, selectByClassName("pagination-first"));
        private final ButtonComponent previousButton =
            new ButtonComponent(this, selectByClassName("pagination-previous"));
        private final InputComponent currentPage = new InputComponent(this, selectByClassName("pagination-current"));
        private final ButtonComponent nextButton = new ButtonComponent(this, selectByClassName("pagination-next"));
        private final ButtonComponent lastButton = new ButtonComponent(this, selectByClassName("pagination-last"));
        private final HtmlComponent totalPages =
            new HtmlComponent(this, selectByAttribute("aria-label", "Total Pages"));

        // ---

        ClarityDataGridPaginationList(SeleniumComponent parent)
        {
            super(parent, selectByClassName("pagination-list"));
        }

        ClarityDataGridPaginationList(SeleniumComponent parent, WebElementSelector selector)
        {
            super(parent, selector);
        }

        // ---

        public int getCurrentPage()
        {
            if (!isReady())
            {
                return 1;
            }

            String page = currentPage.getValue();

            if (null == page || page.isBlank())
            {
                return 1;
            }

            return Integer.parseInt(page);
        }

        public int getTotalPages()
        {
            if (!isReady())
            {
                return 1;
            }

            String total = totalPages.getText();

            if (null == total || total.isBlank())
            {
                return 0;
            }

            return Integer.parseInt(total);
        }

        public void first()
        {
            if (isReady())
            {
                firstButton.click();
            }
        }

        public void previous()
        {
            if (isReady())
            {
                previousButton.click();
            }
        }

        public void next()
        {
            if (isReady())
            {
                nextButton.click();
            }
        }

        public void last()
        {
            if (isReady())
            {
                lastButton.click();
            }
        }
    }
}
