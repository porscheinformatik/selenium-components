package at.porscheinformatik.seleniumcomponents;

import org.openqa.selenium.WebDriver;

/**
 * A factory for WebDrivers
 *
 * @author ham
 */
@FunctionalInterface
public interface WebDriverFactory
{

    WebDriver createWebDriver(String sessionName);

}
