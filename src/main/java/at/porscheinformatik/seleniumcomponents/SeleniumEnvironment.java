package at.porscheinformatik.seleniumcomponents;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.openqa.selenium.WebDriver;

/**
 * Environment for Selenium tests.
 *
 * @author HAM
 */
public interface SeleniumEnvironment
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
     * Parses the given date string in the locale returned by {@link #getLanguage()} with {@link FormatStyle#MEDIUM}
     * 
     * @param dateAsString the date to parse
     * @return the parsed date
     */
    default LocalDate parseDate(String dateAsString)
    {
        return parseDate(dateAsString, FormatStyle.MEDIUM);
    }

    /**
     * Parses the given date string in the locale returned by {@link #getLanguage()}
     * 
     * @param dateAsString the date to parse
     * @param style the date style
     * @return the parsed date
     */
    default LocalDate parseDate(String dateAsString, FormatStyle style)
    {
        if (dateAsString == null)
        {
            return null;
        }

        DateTimeFormatter formatter = getDateTimeFormatter(style);

        return LocalDate.parse(dateAsString, formatter);
    }

    /**
     * Formats the given date string in the locale returned by {@link #getLanguage()} with {@link FormatStyle#MEDIUM}
     * 
     * @param date the date to format
     * @return the formatted date
     */
    default String formatDate(LocalDate date)
    {
        return formatDate(date, FormatStyle.MEDIUM);
    }

    /**
     * Formats the given date string in the locale returned by {@link #getLanguage()}
     * 
     * @param date the date to format
     * @param style the date style
     * @return the formatted date
     */
    default String formatDate(LocalDate date, FormatStyle style)
    {
        if (date == null)
        {
            return null;
        }

        DateTimeFormatter formatter = getDateTimeFormatter(style);

        return date.format(formatter);
    }

    default DateTimeFormatter getDateTimeFormatter(FormatStyle style)
    {
        Locale language = getLanguage();

        return DateTimeFormatter.ofLocalizedDate(style).localizedBy(language);
    }

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
