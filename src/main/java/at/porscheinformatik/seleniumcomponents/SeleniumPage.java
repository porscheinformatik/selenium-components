/**
 *
 */
package at.porscheinformatik.seleniumcomponents;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
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
public class SeleniumPage implements SeleniumComponent
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final String url;
    private final SeleniumContext context;
    private final WebElementSelector selector = WebElementSelector.selectByTagName("body");
    private final int defaultTimeoutInSeconds = 1;

    public SeleniumPage(SeleniumContext context, String url)
    {
        super();

        this.context = Objects.requireNonNull(context, "Context is null");
        this.url = url;

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
    public final WebElement element(double timeoutInSeconds) throws NoSuchElementException
    {
        try
        {
            return SeleniumUtils.keepTrying(timeoutInSeconds, () -> selector.find(context.getDriver()));
        }
        catch (Exception e)
        {
            throw new NoSuchElementException(describe(), e);
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
    public String describe()
    {
        return String.format("%s(%s)", SeleniumUtils.toClassName(getClass()), getUrl());
    }

    public String getUrl()
    {
        return url;
    }

    public final WebElementSelector getSelector()
    {
        return selector;
    }

    public void open()
    {
        open(10);
    }

    public void open(double timeoutInSeconds)
    {
        String url = getUrl();
        String navigate = "Calling " + url;

        logger.info(String.format("\n\n%s\n%s\n%s\n\n", SeleniumUtils.repeat("-", navigate.length()), navigate,
            SeleniumUtils.repeat("-", navigate.length())));

        context.getDriver().get(url);

        waitUntilReady(timeoutInSeconds);
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

    public void waitUntilReady(double timeoutInSeconds)
    {
        SeleniumUtils.waitUntil(timeoutInSeconds, SeleniumConditions.readyState(context.getDriver()));
    }

}
