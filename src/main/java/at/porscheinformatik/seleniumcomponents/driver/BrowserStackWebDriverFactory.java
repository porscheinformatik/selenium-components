package at.porscheinformatik.seleniumcomponents.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import at.porscheinformatik.seleniumcomponents.AbstractWebDriverFactory;

/**
 * Default driver for BrowserStack
 *
 * @author ham
 */
public class BrowserStackWebDriverFactory extends AbstractWebDriverFactory
{

    private final String projectName;
    private final String username;
    private final String automateKey;

    public BrowserStackWebDriverFactory(String projectName, String username, String automateKey)
    {
        super();

        this.projectName = projectName;
        this.username = username;
        this.automateKey = automateKey;
    }

    @Override
    public WebDriver createWebDriver(String sessionName)
    {
        return new RemoteWebDriver(createCapabilities(sessionName));
    }

    protected URL getBrowserStackUrl()
    {
        URL url = null;

        try
        {
            url = new URL(String.format("https://%s:%s@hub-cloud.browserstack.com/wd/hub", username, automateKey));
        }
        catch (MalformedURLException e)
        {
            throw new IllegalArgumentException(e);
        }

        return url;
    }

    protected DesiredCapabilities createCapabilities(String sessionName)
    {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("browser", "Firefox");
        //        caps.setCapability("browser", "Chrome");
        //        caps.setCapability("browser", "edge");
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "10");
        caps.setCapability("resolution", "1280x1024");

        caps.setCapability("browserstack.debug", "true");
        caps.setCapability("browserstack.networkLogs", "true");

        caps.setCapability("project", projectName);
        caps.setCapability("name", sessionName);

        return caps;
    }

}
