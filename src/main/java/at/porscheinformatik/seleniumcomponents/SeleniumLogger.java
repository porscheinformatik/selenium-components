package at.porscheinformatik.seleniumcomponents;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A themed logger for this library. What out! The args use the {@link String#format(String, Object...)} instead of the
 * {@link MessageFormat}.
 *
 * @author ham
 */
public class SeleniumLogger
{
    private static boolean enabled = true;

    private final Logger logger;

    public SeleniumLogger(Class<?> type)
    {
        super();

        logger = LoggerFactory.getLogger(type);
    }

    public static void enable()
    {
        enabled = true;
    }

    public static void disable()
    {
        enabled = false;
    }

    /**
     * Logs an interaction. The log contains the original call line.
     *
     * @param description the description
     * @param args some arguments used for the {@link String#format(String, Object...)} call.
     * @return the message
     */
    public String interaction(String description, Object... args)
    {
        String message = String.format(description, args);

        info(message + " | at " + ThreadUtils.describeCallLine());

        return message;
    }

    /**
     * Logs a debug hint.
     *
     * @param description the description
     * @param args some arguments used for the {@link String#format(String, Object...)} call.
     * @return the message
     */
    public String hint(String description, Object... args)
    {
        String message = String.format(description, args);

        debug(message);

        return message;
    }

    /**
     * Logs a debug hint. The log contains the original call line.
     *
     * @param description the description
     * @param args some arguments used for the {@link String#format(String, Object...)} call.
     * @return the message
     */
    public String hintAt(String description, Object... args)
    {
        return hint(description + " | at " + ThreadUtils.describeCallLine(), args);
    }

    /**
     * Log calling of an url
     *
     * @param url the url
     */
    public void callUrl(String url)
    {
        if (enabled && logger.isInfoEnabled())
        {
            info("Calling URL ...\n\n%s\n%s\n%s\n\n", Utils.repeat("-", url.length()), url,
                Utils.repeat("-", url.length()));
        }
    }

    public void trace(String description, Object... args)
    {
        if (enabled && logger.isTraceEnabled())
        {
            logger.trace("[S] " + String.format(description, args));
        }
    }

    public void trace(String description, Throwable t, Object... args)
    {
        if (enabled && logger.isTraceEnabled())
        {
            logger.trace("[S] " + String.format(description, args), t);
        }
    }

    public void debug(String description, Object... args)
    {
        if (enabled && logger.isDebugEnabled())
        {
            logger.debug("[S] " + String.format(description, args));
        }
    }

    public void debug(String description, Throwable t, Object... args)
    {
        if (enabled && logger.isDebugEnabled())
        {
            logger.debug("[S] " + String.format(description, args), t);
        }
    }

    public void info(String description, Object... args)
    {
        if (enabled && logger.isInfoEnabled())
        {
            logger.info("[S] " + String.format(description, args));
        }
    }

    public void info(String description, Throwable t, Object... args)
    {
        if (enabled && logger.isInfoEnabled())
        {
            logger.info("[S] " + String.format(description, args), t);
        }
    }

    public void warn(String description, Object... args)
    {
        if (enabled && logger.isWarnEnabled())
        {
            logger.warn("[S] " + String.format(description, args));
        }
    }

    public void warn(String description, Throwable t, Object... args)
    {
        if (enabled && logger.isWarnEnabled())
        {
            logger.warn("[S] " + String.format(description, args), t);
        }
    }

    public void error(String description, Object... args)
    {
        if (enabled && logger.isErrorEnabled())
        {
            logger.error("[S] " + String.format(description, args));
        }
    }

    public void error(String description, Throwable t, Object... args)
    {
        if (enabled && logger.isErrorEnabled())
        {
            logger.error("[S] " + String.format(description, args), t);
        }
    }
}
