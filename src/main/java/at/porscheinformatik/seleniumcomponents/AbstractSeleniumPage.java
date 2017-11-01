package at.porscheinformatik.seleniumcomponents;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Daniel Furtlehner
 */
public abstract class AbstractSeleniumPage implements SeleniumComponent
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final SeleniumContext context;
    private final WebElementSelector selector = WebElementSelector.selectByTagName("body");
    private final int defaultTimeoutInSeconds = 1;

    public AbstractSeleniumPage(SeleniumContext context)
    {
        super();

        this.context = Objects.requireNonNull(context, "Context is null");

        context.getDriver().manage().timeouts().implicitlyWait(defaultTimeoutInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public final SeleniumComponent parent()
    {
        return null;
    }

    @Override
    public final SeleniumContext context()
    {
        return context;
    }

    @Override
    public final WebElement element() throws NoSuchElementException
    {
        try
        {
            return SeleniumUtils.keepTrying(SeleniumGlobals.getLongTimeoutInSeconds(),
                () -> selector.find(context.getDriver()));
        }
        catch (Exception e)
        {
            throw new NoSuchElementException(toString(), e);
        }
    }

    @Override
    public final List<WebElement> findElements(By by)
    {
        return context.getDriver().findElements(by);
    }

    @Override
    public final WebElement findElement(By by)
    {
        return context.getDriver().findElement(by);
    }

    @Override
    public boolean isReady()
    {
        JavascriptExecutor e = (JavascriptExecutor) context.getDriver();
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
        String navigate = "Calling URL: " + url;

        logger.info(String.format("\n\n%s\n%s\n%s\n\n", SeleniumUtils.repeat("-", navigate.length()), navigate,
            SeleniumUtils.repeat("-", navigate.length())));

        context.getDriver().get(url);

        waitUntilReady();
    }

    public void close()
    {
        context.quit();
    }

    public String getScreenshot()
    {
        WebDriver driver = context.getDriver();

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
        SeleniumActions.waitUntilReady(timeoutInSeconds, this);
    }

    @Override
    public String toString()
    {
        return String.format(getUrl());
    }
}
