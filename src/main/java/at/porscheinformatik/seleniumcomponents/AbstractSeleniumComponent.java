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
    public final SeleniumComponent parent()
    {
        return parent;
    }

    @Override
    public WebElement element() throws NoSuchElementException
    {
        try
        {
            return SeleniumUtils.keepTrying(SeleniumGlobals.getShortTimeoutInSeconds(), () -> selector.find(parent));
        }
        catch (Exception e)
        {
            throw new NoSuchElementException(describe(), e);
        }
    }

    @Override
    public boolean isReady()
    {
        try
        {
            return SeleniumActions.retryOnStale(() -> !selector.findAll(parent).isEmpty());
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    @Override
    public String describe()
    {
        String description = String.format("%s[%s]", SeleniumUtils.toClassName(getClass()), selector);
        SeleniumComponent parent = parent();

        if (parent != null)
        {
            description = String.format("%s->%s", parent.describe(), description);
        }

        return description;
    }

    protected final WebElementSelector getSelector()
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

    public boolean isVisible()
    {
        return SeleniumActions.isVisible(this);
    }

    public final void waitUntilVisible(double timeoutInSeconds)
    {
        SeleniumActions.waitUntilVisible(timeoutInSeconds, this);
    }

    @Override
    public String toString()
    {
        return describe();
    }

}
