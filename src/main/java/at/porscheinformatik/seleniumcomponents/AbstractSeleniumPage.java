package at.porscheinformatik.seleniumcomponents;

import java.util.Objects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Daniel Furtlehner
 */
public abstract class AbstractSeleniumPage implements SeleniumComponent
{
    private final SeleniumEnvironment environment;
    private final WebElementSelector selector = WebElementSelector.selectByTagName("body");

    public AbstractSeleniumPage(SeleniumEnvironment environment)
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

    /**
     * Returns the URL to open.
     *
     * @return the url
     */
    protected abstract String getUrl();

    public final WebElementSelector getSelector()
    {
        return selector;
    }

    public void open()
    {
        String url = getUrl();

        LOG.callUrl(url);

        environment.getDriver().get(url);

        waitUntilReady();
    }

    public void close()
    {
        environment.quit();
    }

    public String getScreenshot()
    {
        WebDriver driver = environment.getDriver();

        if (driver instanceof TakesScreenshot)
        {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        }

        return null;
    }

    public final void waitUntilReady()
    {
        waitUntilReady(SeleniumGlobals.getLongTimeoutInSeconds());
    }

    public final void waitUntilReady(double timeoutInSeconds)
    {
        SeleniumUtils.waitUntilReady(timeoutInSeconds, this);
    }

    @Override
    public String describe()
    {
        return "";
    }

    @Override
    public String toString()
    {
        return String.format(getUrl());
    }
}
