/**
 *
 */
package at.porscheinformatik.seleniumcomponents;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * A selector as used by {@link SeleniumComponent}s.
 *
 * @author Daniel Furtlehner
 */
public interface WebElementSelector
{

    /**
     * Creates a selector that always uses the specified element. This selector ignores the search context.
     *
     * @param description a description for toString, to make it easier to find the specified element. Most-often the
     *            description looks like a CSS selector.
     * @param element the element to return
     * @return the selector
     */
    static WebElementSelector selectElement(String description, WebElement element)
    {
        return new WebElementSelector()
        {
            @Override
            public WebElement find(SearchContext context)
            {
                return element;
            }

            @Override
            public List<WebElement> findAll(SearchContext context)
            {
                return Arrays.asList(element);
            }

            @Override
            public String decribe(String contextDescription)
            {
                return "$" + description;
            }

            @Override
            public String toString()
            {
                return description;
            }
        };
    }

    /**
     * This is a shortcut method to use a Selenium selector as selector.
     *
     * @param description a description for toString, to make it easier to find the specified element. Most-often the
     *            description looks like a CSS selector.
     * @param by the Selenium selector
     * @return the {@link WebElementSelector} for the given Selenium selector
     */
    static WebElementSelector selectBy(String description, By by)
    {
        return new WebElementSelector()
        {
            @Override
            public WebElement find(SearchContext context)
            {
                return context.findElement(by);
            }

            @Override
            public List<WebElement> findAll(SearchContext context)
            {
                return context.findElements(by);
            }

            @Override
            public String toString()
            {
                return description;
            }
        };
    }

    /**
     * A selector that uses the id of an element. This selector usually does not ignore the hierarchy of components.
     *
     * @param id the id of the element
     * @return the selector
     */
    static WebElementSelector selectById(String id)
    {
        return selectBy("#" + id, By.id(id));
    }

    /**
     * A selector that uses the value of the "name" attribute of an element. This selector respects the hierarchy of
     * components.
     *
     * @param name the expected value of the "name" attribute of the element
     * @return the selector
     */
    static WebElementSelector selectByName(String name)
    {
        return selectBy(String.format("*[name='%s']", name), By.name(name));
    }

    /**
     * A selector that uses the tag name of an element. This selector respects the hierarchy of components.
     *
     * @param tagName the tag name of the element
     * @return the selector
     */
    static WebElementSelector selectByTagName(String tagName)
    {
        return selectBy(tagName, By.tagName(tagName));
    }

    /**
     * A selector that uses the value of the "class" attribute of an element. If the "class" attribute contains multiple
     * classes, the selector will test each. This selector respects the hierarchy of components.
     *
     * @param className the tag class of the element
     * @return the selector
     */
    static WebElementSelector selectByClassName(String className)
    {
        return selectBy("." + className, By.className(className));
    }

    /**
     * A selector that uses a CSS selector query. This selector respects the hierarchy of components.
     *
     * @param css the CSS selector query
     * @return the selector
     */
    static WebElementSelector selectByCss(String css)
    {
        return selectBy(css.contains(" ") ? String.format("(%s)", css) : css, By.cssSelector(css));
    }

    /**
     * A selector that uses the value of the "selenium-key" attribute of an element. This selector respects the
     * hierarchy of components. The implementation is bases on a CSS selector query.
     *
     * @param key the expected value of the "selenium-key" attribute of the element
     * @return the selector
     */
    static WebElementSelector selectBySeleniumKey(String key)
    {
        return selectBySeleniumKey("*", key);
    }

    /**
     * A selector that uses a tagName and the value of the "selenium-key" attribute of an element. This selector
     * respects the hierarchy of components. The implementation is bases on a CSS selector query.
     *
     * @param tagName the tag name of the element
     * @param key the expected value of the "selenium-key" attribute of the element
     * @return the selector
     */
    static WebElementSelector selectBySeleniumKey(String tagName, String key)
    {
        return selectByCss(String.format("%s[selenium-key='%s']", tagName, key));
    }

    /**
     * A selector that uses an XPath query. If this selector starts with a "/", it will ignore the hierarchy of
     * components.
     *
     * @param xpath the XPath query
     * @return the selector
     */
    static WebElementSelector selectByXPath(String xpath)
    {
        return selectBy(String.format("{%s}", xpath), By.xpath(xpath));
    }

    /**
     * Returns the element at the specified index of all direct siblings. The index is zero-based.
     *
     * @param index the index (0-based)
     * @return the selector
     */
    static WebElementSelector selectByIndex(int index)
    {
        return selectByIndex(selectChildren(), index);
    }

    /**
     * Returns the element at the specified index selected by the specified selector. The index is zero-based.
     *
     * @param selector the selector for the child elements
     * @param index the index (0-based)
     * @return the selector
     */
    static WebElementSelector selectByIndex(WebElementSelector selector, int index)
    {
        return new WebElementSelector()
        {
            @Override
            public WebElement find(SearchContext context)
            {
                if (index < 0)
                {
                    return null;
                }

                List<WebElement> elements = selector.findAll(context);

                if (index >= elements.size())
                {
                    return null;
                }

                return elements.get(index);
            }

            @Override
            public List<WebElement> findAll(SearchContext context)
            {
                return Arrays.asList(find(context));
            }

            @Override
            public String toString()
            {
                return String.format("%s:nth-child(%d)", selector, index);
            }
        };
    }

    /**
     * Returns the element that represents the specified column. The first column is 1. Looks at all direct children of
     * the search context. If the element has a "colspan" attribute it is assumed, that the element spans over multiple
     * indices.
     *
     * @param column the column (1-based)
     * @return the selector
     */
    static WebElementSelector selectByColumn(int column)
    {
        return new WebElementSelector()
        {
            @Override
            public WebElement find(SearchContext context)
            {
                List<WebElement> elements = By.xpath("./*").findElements(context);
                int currentIndex = 1;

                for (WebElement element : elements)
                {
                    currentIndex += getColspan(element);

                    if (currentIndex > column)
                    {
                        return element;
                    }
                }

                return null;
            }

            @Override
            public List<WebElement> findAll(SearchContext context)
            {
                return Arrays.asList(find(context));
            }

            private int getColspan(WebElement element)
            {
                String colspan = element.getAttribute("colspan");

                if (Utils.isEmpty(colspan))
                {
                    return 1;
                }

                try
                {
                    return Integer.parseInt(colspan);
                }
                catch (NumberFormatException e)
                {
                    throw new SeleniumException("Failed to parse colspan: " + colspan, e);
                }
            }

            @Override
            public String toString()
            {
                return String.format("*:nth-column(%d)", column);
            }
        };
    }

    /**
     * A selector that selects the component itself.
     *
     * @return the selector
     */
    static WebElementSelector selectSelf()
    {
        return selectBy("", By.xpath("."));
    }

    /**
     * A selector that selects all direct children.
     *
     * @return the selector
     */
    static WebElementSelector selectChildren()
    {
        return selectBy("*", By.xpath("./*"));
    }

    /**
     * Searches for the element.
     *
     * @param context the context to search in
     * @return the element returned by this selector.
     */
    WebElement find(SearchContext context);

    /**
     * Searches for all the elements.
     *
     * @param context the context to search in
     * @return a list of elements, never null
     */
    List<WebElement> findAll(SearchContext context);

    /**
     * Returns a description of the selector based on the context
     *
     * @param contextDescription the description of the context
     * @return the description
     */
    default String decribe(String contextDescription)
    {
        return Utils.isEmpty(contextDescription) ? toString() : contextDescription + " " + toString();
    }

    /**
     * Describe the selector to simplify debugging.
     *
     * @return a string representation
     */
    @Override
    String toString();

    /**
     * Chains the specified selector after this selector.
     *
     * @param selector the selector
     * @return the new selector instance
     */
    default WebElementSelector descendant(WebElementSelector selector)
    {
        // I could never imagine a situation for needing the following in Java
        WebElementSelector that = this;

        return new WebElementSelector()
        {
            @Override
            public WebElement find(SearchContext context)
            {
                return selector.find(that.find(context));
            }

            @Override
            public List<WebElement> findAll(SearchContext context)
            {
                return selector.findAll(that.find(context));
            }

            @Override
            public String decribe(String contextDescription)
            {
                return selector.decribe(that.decribe(contextDescription));
            }

            @Override
            public String toString()
            {
                return String.format("%s %s", that, selector);
            }
        };
    }

    /**
     * @param xpath the xpath to the parent
     * @return the parent
     * @deprecated I think this is too complex. It should not be used.
     */
    @Deprecated
    default WebElementSelector ancestor(String xpath)
    {
        WebElementSelector parent = selectByXPath("ancestor::" + xpath);
        WebElementSelector that = this;

        return new WebElementSelector()
        {
            @Override
            public WebElement find(SearchContext context)
            {
                return parent.find(that.find(context));
            }

            @Override
            public List<WebElement> findAll(SearchContext context)
            {
                return parent.findAll(that.find(context));
            }

            @Override
            public String decribe(String contextDescription)
            {
                return parent.decribe(that.decribe(contextDescription));
            }

            @Override
            public String toString()
            {
                return String.format("%s %s", that, parent);
            }
        };
    }
}
