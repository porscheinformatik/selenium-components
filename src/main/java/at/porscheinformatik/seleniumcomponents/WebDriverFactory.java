package at.porscheinformatik.seleniumcomponents;

import org.openqa.selenium.WebDriver;

/**
 * A factory for {@link WebDriver}s.
 *
 * @author ham
 */
@FunctionalInterface
public interface WebDriverFactory
{

    /**
     * Create the {@link WebDriver}.
     *
     * @param sessionName the name of the session
     * @return the {@link WebDriver}.
     */
    WebDriver createWebDriver(String sessionName);

}
