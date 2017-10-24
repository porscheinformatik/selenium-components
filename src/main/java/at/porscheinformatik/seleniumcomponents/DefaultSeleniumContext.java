package at.porscheinformatik.seleniumcomponents;

import java.util.Locale;

import org.openqa.selenium.WebDriver;

/**
 * Default implementation of a {@link SeleniumContext}
 *
 * @author HAM
 */
public class DefaultSeleniumContext implements SeleniumContext
{

    private final WebDriverFactory driverFactory;
    private final String sessionName;

    private WebDriver driver;

    public DefaultSeleniumContext(WebDriverFactory driverFactory, String sessionName)
    {
        super();

        this.driverFactory = driverFactory;
        this.sessionName = sessionName;

        driver = buildDriver();
    }

    @Override
    public WebDriver getDriver()
    {
        return driver;
    }

    @Override
    public Locale getLanguage()
    {
        return null;
    }

    @Override
    public String getMessage(String key, Object... args)
    {
        return null;
    }

    @Override
    public void restart()
    {
        getDriver().quit();

        TestUtils.waitForSeconds(5);

        driver = buildDriver();
    }

    private WebDriver buildDriver()
    {
        /*
         *  When running on the server with browserstack it happens from time to time that browserstack is not reachable and a exception is thrown.
         *  Wait some time and try it again. Hopefully it works then.
         */
        try
        {
            return driverFactory.createWebDriver(sessionName);
        }
        catch (Exception ex)
        {
            TestUtils.waitForSeconds(5);

            return driverFactory.createWebDriver(sessionName);
        }
    }
}
