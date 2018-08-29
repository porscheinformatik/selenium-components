/**
 * 
 */
package at.porscheinformatik.seleniumcomponents;

import static java.lang.String.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * @author Daniel Furtlehner
 */
public class CombinableWebElementSelector implements WebElementSelector
{
    private final String cssSelector;

    private By by;

    public CombinableWebElementSelector(String cssSelector)
    {
        super();

        this.cssSelector = cssSelector;
    }

    @Override
    public WebElement find(SearchContext context)
    {
        return context.findElement(byCss());
    }

    @Override
    public List<WebElement> findAll(SearchContext context)
    {
        return context.findElements(byCss());
    }

    @Override
    public String toString()
    {
        return cssSelector;
    }

    @Override
    public WebElementSelector combine(WebElementSelector child)
    {
        if (child instanceof CombinableWebElementSelector)
        {
            return new CombinableWebElementSelector(
                format("%s %s", cssSelector, ((CombinableWebElementSelector) child).cssSelector));
        }

        return null;
    }

    private By byCss()
    {
        if (by == null)
        {
            by = By.cssSelector(cssSelector);
        }

        return by;
    }
}
