package at.porscheinformatik.seleniumcomponents;

import org.openqa.selenium.WebDriver;

/**
 * A factory for {@link WebDriver}s.
 *
 * @author ham
 */
@FunctionalInterface
public interface WebDriverFactory {
    /**
     * Called once before the {@link WebDriver} is created. Can be used to initialize the environment.
     */
    default void initializeEnvironment() {
        // intentionally left blank
    }

    /**
     * Create the {@link WebDriver}.
     *
     * @param sessionName the name of the session
     * @return the {@link WebDriver}.
     */
    WebDriver createWebDriver(String sessionName);
}
