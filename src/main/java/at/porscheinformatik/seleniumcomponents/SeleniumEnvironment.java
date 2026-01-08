package at.porscheinformatik.seleniumcomponents;

import static at.porscheinformatik.seleniumcomponents.SeleniumAsserts.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Environment for Selenium tests.
 *
 * @author HAM
 */
public interface SeleniumEnvironment {
    SeleniumLogger LOG = new SeleniumLogger(SeleniumEnvironment.class);

    /**
     * Returns the driver for the browser.
     *
     * @return the web driver
     */
    WebDriver getDriver();

    /**
     * Open the specified URL
     *
     * @param url the URL
     */
    default void url(String url) {
        LOG.callUrl(url);

        getDriver().get(url);
    }

    /**
     * @return the current URL
     */
    default String url() {
        return getDriver().getCurrentUrl();
    }

    /**
     * Calls the URL, assumes that the result is compatible to the specified page and waits until the page gets ready.
     *
     * @param <T> the type of page
     * @param url the URL
     * @param page an instance of the page
     * @return the page
     */
    default <T extends AbstractSeleniumPage> T open(String url, T page) {
        url(url);

        page.assertReadySoon();

        return page;
    }

    /**
     * Takes a screenshot. May return null, if taking screenshots is not possible.
     *
     * @param <T> the type of screenshot
     * @param outputType the type of screenshot
     * @return the screenshot, null if not possible
     */
    default <T> T takeScreenshot(OutputType<T> outputType) {
        WebDriver driver = getDriver();

        if (!(driver instanceof TakesScreenshot)) {
            return null;
        }

        try {
            return ((TakesScreenshot) driver).getScreenshotAs(outputType);
        } catch (NoSuchWindowException e) {
            // failed to take screenshot because window has been closed already
            return null;
        } catch (Exception e) {
            LOG.warn("Failed to take screenshot", e);

            return null;
        }
    }

    /**
     * Takes a screenshot.
     *
     * @return the screenshot, depending on {@link SeleniumGlobals#getScreenshotOutputType()} it either returns a Base64
     * image or the path to the file. May be null if screenshots are not possible.
     */
    default String takeScreenshot() {
        ScreenshotOutputType screenshotOutputType = SeleniumGlobals.getScreenshotOutputType();

        return takeScreenshot(screenshotOutputType);
    }

    /**
     * Takes a screenshot of the specified type
     *
     * @param screenshotOutputType the type of screenshot
     * @return either a Base64 image or the path to the file. May be null if screenshots are not possible.
     */
    default String takeScreenshot(ScreenshotOutputType screenshotOutputType) {
        return switch (screenshotOutputType) {
            case BASE64 -> takeScreenshotAsBase64();
            case FILE -> {
                File file = takeScreenshotAsFile();
                yield file != null ? file.toURI().toString() : null;
            }
            default -> throw new UnsupportedOperationException(
                "ScreenshotOutputType not supported: " + screenshotOutputType
            );
        };
    }

    /**
     * Takes a screenshot, stores it in a file.
     *
     * @return path to the file, null if screenshots are not possible
     */
    default File takeScreenshotAsFile() {
        return takeScreenshot(SeleniumGlobals.PERSISTENT_FILE);
    }

    /**
     * Takes a screenshot.
     *
     * @return a Base64 image, null if screenshots are not possible
     */
    default String takeScreenshotAsBase64() {
        return takeScreenshot(SeleniumGlobals.LOW_QUALITY_BASE64);
    }

    /**
     * The language used for the messages.
     *
     * @return the locale
     */
    default Locale getLanguage() {
        try {
            WebElement htmlElement = getDriver().findElement(By.tagName("html"));

            String languageTag = assertThatSoon(() -> htmlElement.getAttribute("lang"), not(emptyString()));

            return LocaleUtils.toLocale(languageTag);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("No html tag found. Maybe the page was not loaded yet?", e);
        }
    }

    /**
     * Parses the given date string in the locale returned by {@link #getLanguage()} with {@link FormatStyle#MEDIUM}
     *
     * @param dateAsString the date to parse
     * @return the parsed date
     */
    default LocalDate parseDate(String dateAsString) {
        return parseDate(dateAsString, FormatStyle.MEDIUM);
    }

    /**
     * Parses the given date string in the locale returned by {@link #getLanguage()}
     *
     * @param dateAsString the date to parse
     * @param style the date style
     * @return the parsed date
     */
    default LocalDate parseDate(String dateAsString, FormatStyle style) {
        if (dateAsString == null || dateAsString.trim().isEmpty()) {
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
    default String formatDate(LocalDate date) {
        return formatDate(date, FormatStyle.MEDIUM);
    }

    /**
     * Formats the given date string in the locale returned by {@link #getLanguage()}
     *
     * @param date the date to format
     * @param style the date style
     * @return the formatted date
     */
    default String formatDate(LocalDate date, FormatStyle style) {
        if (date == null) {
            return null;
        }

        DateTimeFormatter formatter = getDateTimeFormatter(style);

        return date.format(formatter);
    }

    default DateTimeFormatter getDateTimeFormatter(FormatStyle style) {
        Locale language = getLanguage();

        return DateTimeFormatter.ofLocalizedDate(style).withLocale(language);
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
    default String getMessageOrKey(String key, Object... args) {
        String message = getMessage(key, args);

        return message != null ? message : key;
    }

    /**
     * @return the window handle, a string to uniquly identify a browser window
     */
    default String getWindowHandle() {
        try {
            return getDriver().getWindowHandle();
        } catch (NoSuchWindowException e) {
            // current window has been closed
            return null;
        }
    }

    /**
     * Tries to switch to the window with the specified handle. After this, the {@link SeleniumEnvironment} and it's web
     * driver points to the window, until the {@link SubWindow} will be closed.
     *
     * @param windowHandle the the unique id of the window
     * @return the handle for the window
     * @throws IllegalArgumentException if the window was not found
     */
    default SubWindow switchToWindow(String windowHandle) throws IllegalArgumentException {
        WebDriver driver = getDriver();
        String originalHandle = getWindowHandle();

        if (windowHandle == null) {
            Iterator<String> handles = driver.getWindowHandles().iterator();

            if (!handles.hasNext()) {
                throw new IllegalArgumentException("There is no open window");
            }

            windowHandle = handles.next();
        }
        try {
            driver.switchTo().window(windowHandle);

            assertThatSoon(this::getWindowHandle, is(windowHandle));

            return new SubWindow(this, originalHandle, windowHandle);
        } catch (SeleniumException e) {
            throw new IllegalArgumentException(
                String.format("There is no matching window. Known windows are: %s", driver.getWindowHandles()),
                e
            );
        }
    }

    /**
     * Tries to switch the window with the specified title (case-insensitive). After this, the
     * {@link SeleniumEnvironment} and it's web driver points to the window, until the {@link SubWindow} will be
     * closed.
     *
     * @param title the title
     * @return the handle for the window
     * @throws IllegalArgumentException if the window was not found
     */
    default SubWindow switchToWindowByTitle(String title) throws IllegalArgumentException {
        return switchToWindowByTitle(StringPredicate.equalTo(title));
    }

    /**
     * Tries to switch to "the other windows". Assumes that there are exactly two windows. After this, the
     * {@link SeleniumEnvironment} and it's web driver points to the window, until the {@link SubWindow} will be
     * closed.
     *
     * @return the handle for the window
     * @throws IllegalArgumentException if the window was not found
     */
    default SubWindow switchToOtherWindow() throws IllegalArgumentException {
        WebDriver driver = getDriver();
        String originalHandle = getWindowHandle();

        try {
            return SeleniumUtils.keepTrying(SeleniumGlobals.getLongTimeoutInSeconds(), () -> {
                Set<String> windowHandles = driver.getWindowHandles();

                if (windowHandles.size() != 2) {
                    throw new IllegalArgumentException(
                        "Could not switch to other window. There should be exactly two windows, " +
                        "but these windows where found: " +
                        windowHandles
                    );
                }

                for (String windowHandle : windowHandles) {
                    if (Objects.equals(originalHandle, windowHandle)) {
                        continue;
                    }

                    driver.switchTo().window(windowHandle);

                    assertThatSoon(this::getWindowHandle, is(windowHandle));

                    return new SubWindow(this, originalHandle, windowHandle);
                }

                return null;
            });
        } catch (SeleniumException e) {
            throw new IllegalArgumentException("Could not switch to other window", e);
        }
    }

    /**
     * Tries to switch the window matching the specified title. After this, the {@link SeleniumEnvironment} and it's web
     * driver points to the window, until the {@link SubWindow} will be closed.
     *
     * @param titlePredicate the predicate matching the title (best used with {@link StringPredicate}s)
     * @return the handle for the window
     * @throws IllegalArgumentException if the window was not found
     */
    default SubWindow switchToWindowByTitle(Predicate<String> titlePredicate) throws IllegalArgumentException {
        WebDriver driver = getDriver();
        String originalHandle = getWindowHandle();
        Collection<String> titles = new HashSet<>();

        try {
            return SeleniumUtils.keepTrying(SeleniumGlobals.getLongTimeoutInSeconds(), () -> {
                Set<String> windowHandles = driver.getWindowHandles();

                for (String windowHandle : windowHandles) {
                    String currentTitle = driver.switchTo().window(windowHandle).getTitle();

                    if (titlePredicate.test(currentTitle)) {
                        return new SubWindow(this, originalHandle, windowHandle);
                    }

                    titles.add(currentTitle);
                }

                return null;
            });
        } catch (SeleniumException e) {
            throw new IllegalArgumentException(
                String.format("There is no matching window. Known window titles are: %s", titles),
                e
            );
        }
    }

    /**
     * Tries to switch the window with the specified URL (case-insensitive). After this, the {@link SeleniumEnvironment}
     * and it's web driver points to the window, until the {@link SubWindow} will be closed.
     *
     * @param url the URL
     * @return the handle for the window
     * @throws IllegalArgumentException if the window was not found
     */
    default SubWindow switchToWindowByUrl(String url) {
        return switchToWindowByUrl(StringPredicate.equalTo(url));
    }

    /**
     * Tries to switch the window matching the specified URL. After this, the {@link SeleniumEnvironment} and it's web
     * driver points to the window, until the {@link SubWindow} will be closed.
     *
     * @param urlPredicate the predicate matching the URL (best used with {@link StringPredicate}s)
     * @return the handle for the window
     * @throws IllegalArgumentException if the window was not found
     */
    default SubWindow switchToWindowByUrl(Predicate<String> urlPredicate) {
        WebDriver driver = getDriver();
        String originalHandle = getWindowHandle();
        Collection<String> urls = new HashSet<>();

        try {
            return SeleniumUtils.keepTrying(SeleniumGlobals.getLongTimeoutInSeconds(), () -> {
                urls.clear();

                Set<String> windowHandles = driver.getWindowHandles();

                for (String windowHandle : windowHandles) {
                    String currentUrl = driver.switchTo().window(windowHandle).getCurrentUrl();

                    if (urlPredicate.test(currentUrl)) {
                        return new SubWindow(this, originalHandle, windowHandle);
                    }

                    urls.add(currentUrl);
                }

                return null;
            });
        } catch (SeleniumException e) {
            throw new IllegalArgumentException(
                String.format("There is no matching window. Known window URLs are: %s", urls),
                e
            );
        }
    }

    /**
     * Tries to switch to the frame with the specified id or name. After this, the {@link SeleniumEnvironment} and it's
     * web driver points to the frame, until the {@link SubFrame} will be closed.
     *
     * @param nameOrId the name or id
     * @return the handle for the frame
     * @throws IllegalArgumentException if there is no frame with the specified name or id
     */
    default SubFrame switchToFrameByNameOrId(String nameOrId) throws IllegalArgumentException {
        WebDriver driver = getDriver();

        try {
            return SeleniumUtils.keepTrying(SeleniumGlobals.getLongTimeoutInSeconds(), () -> {
                driver.switchTo().frame(nameOrId);

                return new SubFrame(this);
            });
        } catch (SeleniumException e) {
            throw new IllegalArgumentException(String.format("There is no frame with name or id \"%s\"", nameOrId), e);
        }
    }

    /**
     * Tries to switch to the frame of the specified element. After this, the {@link SeleniumEnvironment} and it's web
     * driver points to the frame, until the {@link SubFrame} will be closed.
     *
     * @param frameElement the element
     * @return the handle for the frame
     * @throws IllegalArgumentException if there is no frame with the specified name or id
     */
    default SubFrame switchToFrame(WebElement frameElement) {
        WebDriver driver = getDriver();

        try {
            return SeleniumUtils.keepTrying(SeleniumGlobals.getLongTimeoutInSeconds(), () -> {
                driver.switchTo().frame(frameElement);

                return new SubFrame(this);
            });
        } catch (SeleniumException e) {
            throw new IllegalArgumentException("Failed to switch to the specified frame", e);
        }
    }

    /**
     * @return all cookies
     */
    default Set<Cookie> getAllCookies() {
        return getDriver().manage().getCookies();
    }

    /**
     * Adds a cookie.
     *
     * @param cookie the cookie
     */
    default void addCookie(Cookie cookie) {
        getDriver().manage().addCookie(cookie);
    }

    /**
     * Returns the cookie with the specified name.
     *
     * @param cookieName the name
     * @return the cookie, null if not found
     */
    default Cookie getCookieByName(String cookieName) {
        return getDriver().manage().getCookieNamed(cookieName);
    }

    /**
     * Deletes all cookies.
     */
    default void deleteAllCookies() {
        getDriver().manage().deleteAllCookies();
    }

    /**
     * Deletes the specified cookie.
     *
     * @param cookie the cookie
     */
    default void deleteCookie(Cookie cookie) {
        getDriver().manage().deleteCookie(cookie);
    }

    /**
     * Deletes the cookie with the specified name.
     *
     * @param cookieName the name
     */
    default void deleteCookieByName(String cookieName) {
        getDriver().manage().deleteCookieNamed(cookieName);
    }

    /**
     * Clears the local storage
     */
    default void clearLocalStorage() {
        RemoteWebDriver remoteWebDriver = (RemoteWebDriver) getDriver();

        remoteWebDriver.executeScript("window.localStorage.clear();");
    }

    default String localStorageGetItem(String key) {
        RemoteWebDriver remoteWebDriver = (RemoteWebDriver) getDriver();
        Object result = remoteWebDriver.executeScript("return window.localStorage.getItem(arguments[0]);", key);

        return result != null ? result.toString() : null;
    }

    default void localStorageSetItem(String key, String value) {
        RemoteWebDriver remoteWebDriver = (RemoteWebDriver) getDriver();

        remoteWebDriver.executeScript("window.localStorage.setItem(arguments[0], arguments[1]);", key, value);
    }

    default void localStorageRemoveItem(String key) {
        RemoteWebDriver remoteWebDriver = (RemoteWebDriver) getDriver();

        remoteWebDriver.executeScript("window.localStorage.removeItem(arguments[0]);", key);
    }

    /**
     * Quits the Selenium driver.
     */
    default void quit() {
        getDriver().quit();
    }

    /**
     * Restarts the driver.
     */
    void restart();
}
