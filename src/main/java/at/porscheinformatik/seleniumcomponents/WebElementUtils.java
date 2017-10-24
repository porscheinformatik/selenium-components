/**
 *
 */
package at.porscheinformatik.seleniumcomponents;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Daniel Furtlehner
 */
public final class WebElementUtils
{

    private WebElementUtils()
    {
        super();
    }

    public static String toString(WebElement... elements)
    {
        return toString(Arrays.asList(elements));
    }

    public static String toString(Collection<WebElement> elements)
    {
        StringBuilder builder = new StringBuilder();

        for (WebElement element : elements)
        {
            if (builder.length() > 0)
            {
                builder.append("\n");
            }

            String tagName = element.getTagName();

            builder.append("<").append(tagName).append(">\n");

            List<WebElement> innerElements = element.findElements(By.className("*"));

            if (!SeleniumUtils.isEmpty(innerElements))
            {
                builder.append("\t").append(toString(innerElements).replace("\n", "\n\t"));
            }

            builder.append("</").append(tagName).append(">");
        }

        return builder.toString();
    }
}
