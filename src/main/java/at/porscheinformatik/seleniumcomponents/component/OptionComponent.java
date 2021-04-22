package at.porscheinformatik.seleniumcomponents.component;

import org.hamcrest.Matchers;
import org.openqa.selenium.WebElement;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ClickableSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumAsserts;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author cet
 */
public class OptionComponent extends AbstractSeleniumComponent implements ClickableSeleniumComponent
{

    public OptionComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public void select()
    {
        if (!isSelected())
        {
            SeleniumAsserts.assertThatSoon("Select option", () -> {
                WebElement element = element();

                element.click();

                return element.isSelected();
            }, Matchers.is(true));
        }
    }

    public boolean isSelected()
    {
        return element().isSelected();
    }

    public String getValue()
    {
        return getAttribute("value");
    }

    public String getLabel()
    {
        return getText();
    }

}
