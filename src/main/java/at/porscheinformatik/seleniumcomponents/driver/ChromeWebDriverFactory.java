package at.porscheinformatik.seleniumcomponents.driver;

import at.porscheinformatik.seleniumcomponents.AbstractWebDriverFactory;
import at.porscheinformatik.seleniumcomponents.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * A {@link WebDriverFactory} for Chrome
 *
 * @author ham
 */
public class ChromeWebDriverFactory extends AbstractWebDriverFactory {

    @Override
    public WebDriver createWebDriver(String sessionName) {
        return new ChromeDriver(createOptions());
    }

    protected ChromeOptions createOptions() {
        return new ChromeOptions();
    }
}
