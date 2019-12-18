package at.porscheinformatik.seleniumcomponents;

import org.openqa.selenium.WebDriver;

/**
 * Default implementation of a {@link SeleniumEnvironment}
 *
 * @author HAM
 */
public class DefaultSeleniumEnvironment implements SeleniumEnvironment
{

    private final WebDriverFactory driverFactory;
    private final String sessionName;

    private WebDriver driver;

    public DefaultSeleniumEnvironment(WebDriverFactory driverFactory, String sessionName)
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
    public String getMessage(String key, Object... args)
    {
        return null;
    }

    @Override
    public void restart()
    {
        getDriver().quit();

        SeleniumUtils.waitForSeconds(5);

        driver = buildDriver();
    }

    private WebDriver buildDriver()
    {
        /*
         *  When running on the server with browserstack it happens from time to time that browserstack is not reachable and a exception is thrown.
         *  Wait some time and try it again. Hopefully it works then.
         */
        return SeleniumUtils
            .keepTrying(SeleniumGlobals.getLongTimeoutInSeconds(), () -> driverFactory.createWebDriver(sessionName));
    }
}
