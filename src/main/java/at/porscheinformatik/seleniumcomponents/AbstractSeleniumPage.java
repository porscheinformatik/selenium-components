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
            return SeleniumUtils
                .keepTrying(SeleniumGlobals.getLongTimeoutInSeconds(), () -> selector.find(environment.getDriver()));
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

    public final void waitUntilReady()
    {
        waitUntilReady(SeleniumGlobals.getLongTimeoutInSeconds());
    }

    /**
     * Opens the specified URL and assumes, that the result consists of this page.
     *
     * @param url the URL to be called
     * @deprecated Do not use this method anymore! The class, that implements this page, should rather provide a static
     *             "open" method (without an URL, but with supported parameters) and should return an instance of
     *             itself. This is because the page usually knows it's URL while this method implies, that the caller
     *             should know the URL. This call can be replaced with a call to
     *             {@link SeleniumEnvironment#open(String, AbstractSeleniumPage)}. A static open method looks like the
     *             following:
     *
     *             <pre>
     *             public static MyPage open(SeleniumEnvironment environment)
     *             {
     *                 return environment.open("https://localhost/mypage", MyPage::new);
     *             }
     *             </pre>
     */
    @Deprecated
    public void open(String url)
    {
        environment().url(url);

        waitUntilReady();
    }

    @Override
    public String describe()
    {
        return "";
    }
}
