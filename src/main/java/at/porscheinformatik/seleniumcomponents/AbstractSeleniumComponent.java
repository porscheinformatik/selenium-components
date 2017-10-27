package at.porscheinformatik.seleniumcomponents;

import java.util.Objects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * The abstract base implementation of a {@link SeleniumComponent}.
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
            return SeleniumUtils.keepTrying(timeoutInSeconds, () -> selector.find(parent)).orElseThrow(
                () -> new SeleniumTestException("Element not found: " + selector));
        }
        catch (SeleniumTestException e)
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

    @Override
    public WebElementSelector selector()
    {
        return selector;
    }
    
    protected String getTagName()
    {
        return SeleniumActions.getTagName(this);
    }

    protected String getAttribute(String name)
    {
        return SeleniumActions.getAttribute(this, name);
    }

    public boolean exists()
    {
        return SeleniumActions.exists(this);
    }

    public boolean isVisible()
    {
        return SeleniumActions.isVisible(this);
    }

    public void waitUntilVisible(double timeoutInSeconds)
    {
        SeleniumActions.waitUntilVisible(timeoutInSeconds, this);
    }

    @Override
    public String toString()
    {
        return describe();
    }

}
