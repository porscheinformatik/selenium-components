/**
 *
 */
package at.porscheinformatik.seleniumcomponents;

import static java.lang.String.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * @author Daniel Furtlehner
 */
public class CombinableWebElementSelector implements WebElementSelector {

    private final String[] cssSelectors;

    private By by;

    public CombinableWebElementSelector(String... cssSelectors) {
        super();
        this.cssSelectors = cssSelectors;
    }

    @Override
    public WebElement find(SearchContext context) {
        return context.findElement(byCss());
    }

    @Override
    public List<WebElement> findAll(SearchContext context) {
        return context.findElements(byCss());
    }

    @Override
    public String toString() {
        return toCssString();
    }

    public String toCssString() {
        return Arrays.stream(cssSelectors).collect(Collectors.joining(", "));
    }

    @Override
    public WebElementSelector combine(@Nonnull WebElementSelector child) {
        if (child instanceof CombinableWebElementSelector) {
            List<String> combinedSelectors = new ArrayList<>();

            for (String cssSelector : cssSelectors) {
                for (String childsCssSelector : ((CombinableWebElementSelector) child).cssSelectors) {
                    combinedSelectors.add(format("%s %s", cssSelector, childsCssSelector));
                }
            }

            return new CombinableWebElementSelector(combinedSelectors.stream().toArray(size -> new String[size]));
        }

        return null;
    }

    private By byCss() {
        if (by == null) {
            by = By.cssSelector(toCssString());
        }

        return by;
    }
}
