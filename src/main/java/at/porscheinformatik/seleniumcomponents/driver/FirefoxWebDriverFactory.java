package at.porscheinformatik.seleniumcomponents.driver;

import at.porscheinformatik.seleniumcomponents.AbstractWebDriverFactory;
import at.porscheinformatik.seleniumcomponents.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * A {@link WebDriverFactory} for Firefox
 *
 * @author ham
 */
public class FirefoxWebDriverFactory extends AbstractWebDriverFactory {

    @Override
    public WebDriver createWebDriver(String sessionName) {
        return buildDriver();
    }

    public WebDriver buildDriver() {
        return new FirefoxDriver(createOptions());
    }

    protected FirefoxOptions createOptions() {
        FirefoxOptions options = new FirefoxOptions();

        options.setProfile(createProfile());

        return options;
    }

    protected FirefoxProfile createProfile() {
        return new FirefoxProfile();
    }
}
