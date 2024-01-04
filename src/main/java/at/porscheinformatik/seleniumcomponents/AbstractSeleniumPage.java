package at.porscheinformatik.seleniumcomponents;

import java.util.Objects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * A component, that spans the whole page, starting with a "body" tag. In contrast to the
 * {@link AbstractSeleniumStandalonePage}, it has no URL nor it can open itself.
 *
 * @author Manfred Hantschel
 * @author Daniel Furtlehner
 */
public abstract class AbstractSeleniumPage implements SeleniumComponent
{
    private final SeleniumEnvironment environment;
    private final WebElementSelector selector = WebElementSelector.selectByTagName("body");

    protected AbstractSeleniumPage(SeleniumEnvironment environment)
    {
        super();

        this.environment = Objects.requireNonNull(environment, "Context is null");
    }

    @Override
    public final SeleniumComponent parent()
    {
        return null;
    }

    @Override
    public final SeleniumEnvironment environment()
    {
        return environment;
    }

    @Override
    public final WebElement element() throws NoSuchElementException
    {
        try
        {
            return SeleniumUtils.keepTrying(SeleniumGlobals.getLongTimeoutInSeconds(),
                () -> selector.find(environment.getDriver()));
        }
        catch (Exception e)
        {
            throw new NoSuchElementException(selector.decribe(null), e);
        }
    }

    @Override
    public SearchContext searchContext()
    {
        return environment.getDriver();
    }

    @Override
    public boolean isReady()
    {
        JavascriptExecutor e = (JavascriptExecutor) environment.getDriver();
        Object readyState = e.executeScript("return document.readyState");

        return readyState.equals("complete");
    }

    public final WebElementSelector getSelector()
    {
        return selector;
    }

    public String takeScreenshot()
    {
        return environment.takeScreenshot();
    }

    @Override
    public final void waitUntilReady()
    {
        assertReadySoon(SeleniumGlobals.getLongTimeoutInSeconds());
    }

    @Override
    public final void assertReadySoon()
    {
        assertReadySoon(SeleniumGlobals.getLongTimeoutInSeconds());
    }

    @Override
    public String describe()
    {
        return "";
    }
}
