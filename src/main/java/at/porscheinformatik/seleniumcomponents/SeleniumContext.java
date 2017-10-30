package at.porscheinformatik.seleniumcomponents;

import java.util.Locale;

import org.openqa.selenium.WebDriver;

/**
 * Context for Selenium tests
 *
 * @author HAM
 */
public interface SeleniumContext
{

    /**
     * Returns the driver for the browser.
     *
     * @return the web driver
     */
    WebDriver getDriver();

    /**
     * The language used for the messages.
     *
     * @return the locale
     */
    Locale getLanguage();

    /**
     * Returns the message with the specified key. If the message is missing, it should return null.
     *
     * @param key the key
     * @param args the args
     * @return the message, null if not found
     */
    String getMessage(String key, Object... args);

    /**
     * Returns the message with the specified key. If the message is missing, it returns the key itself.
     *
     * @param key the key
     * @param args the args
     * @return the message, never null
     */
    default String getMessageOrKey(String key, Object... args)
    {
        String message = getMessage(key, args);

        return message != null ? message : key;
    }

    /**
     * Quits the Selenium driver.
     */
    default void quit()
    {
        getDriver().quit();
    }

    /**
     * Restarts the driver.
     */
    void restart();

}
