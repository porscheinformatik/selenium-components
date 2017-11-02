package at.porscheinformatik.seleniumcomponents;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Global settings for Selenium
 *
 * @author ham
 */
public final class SeleniumGlobals
{

    private static final List<Pattern> IGNORABLE_CALL_ELEMENTS = new ArrayList<>();

    private static double timeMultiplier = 1;

    private static double shortTimeoutInSeconds = 1.0;
    private static double longTimeoutInSeconds = 10;

    static
    {
        addIgnorableCallElement(Pattern.compile("^java\\.lang\\..*"));
        addIgnorableCallElement(Pattern.compile("^"
            + SeleniumUtils.class.getName().substring(0, SeleniumUtils.class.getName().lastIndexOf(".")).replace(".",
                "\\.")
            + ".*"));
    }

    private SeleniumGlobals()
    {
        super();
    }

    /**
     * Returns the multiplier, that will be applied to any timeout or delay passed to this class.
     *
     * @return the multiplier
     */
    public static double getTimeMultiplier()
    {
        return timeMultiplier;
    }

    /**
     * Any timeout or delay passed to this class, will be multiplied by the specified multiplier. The default value is
     * 1.
     *
     * @param timeMultiplier the multiplier
     */
    public static void setTimeMultiplier(double timeMultiplier)
    {
        SeleniumGlobals.timeMultiplier = timeMultiplier;
    }

    /**
     * Returns the timeout used for short user interactions like looking at some text or clicking a link. The returned
     * was not yet multiplied by the time multiplier, this is usually done by the timeout methods in the
     * {@link SeleniumUtils}.
     *
     * @return the timeout in seconds
     */
    public static double getShortTimeoutInSeconds()
    {
        return shortTimeoutInSeconds;
    }

    /**
     * Sets the timeout used for short user interactions like looking at some text or clicking a link.
     *
     * @param shortTimeoutInSeconds the timeout in seconds
     */
    public static void setShortTimeoutInSeconds(double shortTimeoutInSeconds)
    {
        SeleniumGlobals.shortTimeoutInSeconds = shortTimeoutInSeconds;
    }

    /**
     * Returns the timeout used for long user interactions like loading a page. The returned was not yet multiplied by
     * the time multiplier, this is usually done by the timeout methods in the {@link SeleniumUtils}.
     *
     * @return the timeout in seconds
     */
    public static double getLongTimeoutInSeconds()
    {
        return longTimeoutInSeconds;
    }

    /**
     * Sets the timeout used for long user interactions like loading a page.
     *
     * @param longTimeoutInSeconds the timeout in seconds
     */
    public static void setLongTimeoutInSeconds(double longTimeoutInSeconds)
    {
        SeleniumGlobals.longTimeoutInSeconds = longTimeoutInSeconds;
    }

    /**
     * Adds an item to the list of {@link Pattern}s for method names, that should be ignored in call stacks. This
     * patterns are used in the {@link Utils#describeCallLine()} methods to find the caller excluding the
     * framework itself.
     *
     * @param pattern the pattern
     */
    public static void addIgnorableCallElement(Pattern pattern)
    {
        if (IGNORABLE_CALL_ELEMENTS.contains(pattern))
        {
            return;
        }

        IGNORABLE_CALL_ELEMENTS.add(Objects.requireNonNull(pattern));
    }

    /**
     * Returns a list of {@link Pattern}s for method names, that should be ignored in call stacks. This patterns are
     * used in the {@link Utils#describeCallLine()} methods to find the caller excluding the framework itself.
     *
     * @return the patterns
     */
    public static List<Pattern> getIgnorableCallElements()
    {
        return IGNORABLE_CALL_ELEMENTS;
    }

}
