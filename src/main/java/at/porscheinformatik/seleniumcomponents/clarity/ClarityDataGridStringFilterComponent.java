/**
 *
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.SeleniumUtils.*;
import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.component.ButtonComponent;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;
import at.porscheinformatik.seleniumcomponents.component.InputComponent;

/**
 * @author Daniel Furtlehner
 */
public class ClarityDataGridStringFilterComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{
    private final HtmlComponent filterContainer = new HtmlComponent(root(this), selectByClassName("datagrid-filter"));
    private final InputComponent input = new InputComponent(filterContainer);
    private final ButtonComponent closeButton = new ButtonComponent(filterContainer);

    public ClarityDataGridStringFilterComponent(SeleniumComponent parent)
    {
        super(parent, selectByTagName("clr-dg-filter"));
    }

    public void filter(String filterQuery)
    {
        click();

        input.clear();
        input.sendKeys(filterQuery);

        closeButton.click();
    }
}
