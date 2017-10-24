package at.porscheinformatik.seleniumcomponents;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * The base implementation of a {@link SeleniumComponent}.
 *
 * @author ham
 */
public abstract class AbstractSeleniumComponent implements SeleniumComponent
{

    private final SeleniumComponent parent;
    private final WebElementSelector selector;

    /**
     * Creates a new {@link SeleniumComponent} with the specified parent and the specified selector.
     *
     * @param parent the mandatory parent
     * @param selector the mandatory selector
     */
    public AbstractSeleniumComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super();

        this.parent = Objects.requireNonNull(parent, "Parent is null");
        this.selector = Objects.requireNonNull(selector, "Selector is null");
    }

    @Override
    public SeleniumComponent parent()
    {
        return parent;
    }

    @Override
    public WebElement element(double timeoutInSeconds) throws NoSuchElementException
    {
        try
        {
            return TestUtils.keepTrying(timeoutInSeconds, () -> selector.find(parent)).orElseThrow(
                () -> new TestException("Element not found: " + selector));
        }
        catch (TestException e)
        {
            if (parent != null)
            {
                try
                {
                    parent.element();
                }
                catch (NoSuchElementException e2)
                {
                    throw new NoSuchElementException(describe(), e2);
                }
            }

            throw new NoSuchElementException(describe(), e);
        }
    }

    /**
     * Returns a list of all {@link WebElement}s, that match the selector of this component.
     *
     * @return a list of elements, never null
     */
    @Override
    public List<WebElement> elements()
    {
        return selector.findAll(parent);
    }

    protected String getTagName()
    {
        return SeleniumComponentUtils.getTagName(this);
    }

    protected String getAttribute(String name)
    {
        return SeleniumComponentUtils.getAttribute(this, name);
    }

    public boolean exists()
    {
        return SeleniumComponentUtils.exists(this);
    }

    public boolean isVisible()
    {
        return SeleniumComponentUtils.isVisible(this);
    }

    public void waitUntilVisible(double timeoutInSeconds)
    {
        SeleniumComponentUtils.waitUntilVisible(timeoutInSeconds, this);
    }

    /**
     * Selects all children of this component by the given selector and wraps each element in a component with ourself
     * as the parent.
     *
     * @param selector the selector to select the children
     * @param componentFactory the factory to create components out of the elements. Is called with the parent and the
     *            selector.
     * @param <COMPONENT_TYPE> type of components to produce
     * @return list of components
     */
    protected <COMPONENT_TYPE extends SeleniumComponent> List<COMPONENT_TYPE> descendants(WebElementSelector selector,
        SeleniumComponentFactory<COMPONENT_TYPE> componentFactory)
    {
        List<WebElement> allElements = elements();
        List<COMPONENT_TYPE> components = new ArrayList<>();

        for (WebElement element : allElements)
        {
            components.add(componentFactory.apply(this, selectElement(element)));
        }

        return components;
    }

    @Override
    public String toString()
    {
        return describe();
    }

    @Override
    public String describe()
    {
        return String.format("%s -> %s[%s]", parent.describe(), TestUtils.toClassName(getClass()), selector);
    }

}
