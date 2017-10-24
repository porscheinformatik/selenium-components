/**
 *
 */
package at.porscheinformatik.seleniumcomponents;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Daniel Furtlehner
 */
public abstract class AbstractSeleniumPage implements SeleniumPage
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
    public SeleniumContext context()
    {
        return context;
    }

    @Override
    public WebElement element(double timeoutInSeconds) throws NoSuchElementException
    {
        return TestUtils.keepTrying(timeoutInSeconds, () -> selector.find(context.getDriver())).orElseThrow(
            () -> new TestException("Element not found: " + selector));
    }

    @Override
    public List<WebElement> elements()
    {
        return selector.findAll(context().getDriver());
    }

    @Override
    public List<WebElement> findElements(By by)
    {
        return context.getDriver().findElements(by);
    }

    @Override
    public WebElement findElement(By by)
    {
        return context.getDriver().findElement(by);
    }

    @Override
    public String describe()
    {
        return "root";
    }

    @Override
    public void open()
    {
        String url = getStartUrl();
        String navigate = "Calling " + url;

        logger.info(String.format("\n\n%s\n%s\n%s\n\n", TestUtils.repeat("-", navigate.length()), navigate,
            TestUtils.repeat("-", navigate.length())));

        context.getDriver().get(url);

        waitUntilReady();
    }

    public void waitUntilLoaded()
    {
        waitUntilReady();
    }

    protected void waitUntilReady()
    {
        waitUntilReady(10);
    }

    protected void waitUntilReady(double timeoutInSeconds)
    {
        TestUtils.waitUntil(timeoutInSeconds, SeleniumConditions.readyState(context().getDriver()));
    }

    @Override
    public String getScreenshotFromActualPage()
    {
        WebDriver driver = context.getDriver();

        if (driver instanceof TakesScreenshot)
        {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        }

        return null;
    }

    @Override
    public void close()
    {
        context.quit();
    }

    protected abstract String getStartUrl();

}
