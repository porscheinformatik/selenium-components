package at.porscheinformatik.seleniumcomponents;

import java.util.Objects;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * The abstract base implementation of a {@link SeleniumComponent}.
 *
 * @author ham
 */
public abstract class AbstractSeleniumComponent implements VisibleSeleniumComponent
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
    public final SeleniumComponent parent()
    {
        return parent;
    }

    @Override
    public WebElement element() throws NoSuchElementException
    {
        try
        {
            return element(selector);
        }
        catch (Exception e)
        {
            throw new NoSuchElementException(selector.decribe(parent.describe()), e);
        }
    }

    protected WebElement element(WebElementSelector selector)
    {
        if (parent instanceof AbstractSeleniumComponent)
        {
            AbstractSeleniumComponent seleniumP = (AbstractSeleniumComponent) parent;
            WebElementSelector combinedSelector = seleniumP.getSelector().combine(selector);

            if (combinedSelector != null)
            {
                return seleniumP.element(combinedSelector);
            }
        }

        return SeleniumUtils
            .keepTrying(SeleniumGlobals.getShortTimeoutInSeconds(), () -> selector.find(parent.searchContext()));
    }

    @Override
    public boolean isReady()
    {
        try
        {
            return SeleniumUtils.retryOnStale(() -> selector.find(parent.searchContext()) != null);
        }
        catch (Exception e)
        {
            return false;
        }
    }

    protected final WebElementSelector getSelector()
    {
        return selector;
    }

    protected String getTagName()
    {
        return SeleniumUtils.getTagName(this);
    }

    protected String getAttribute(String name)
    {
        return SeleniumUtils.getAttribute(this, name);
    }

    protected String getClassAttribute()
    {
        return SeleniumUtils.getClassAttribute(this);
    }

    protected boolean containsClassName(String className)
    {
        return SeleniumUtils.containsClassName(this, className);
    }

    protected String getText()
    {
        return SeleniumUtils.getText(this);
    }

    @Override
    public String describe()
    {
        return selector.decribe(parent.describe());
    }

    @Override
    public String toString()
    {
        return String.format("%s(%s)", Utils.toClassName(getClass()), describe());
    }

}
