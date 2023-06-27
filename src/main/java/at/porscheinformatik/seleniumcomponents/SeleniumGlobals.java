package at.porscheinformatik.seleniumcomponents;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;

/**
 * Global settings for Selenium. The settings can be set by System properties.
 * <table>
 * <caption>Supported System properties</caption>
 * <tr>
 * <th>{@value #DEBUG_KEY}</th>
 * <td>If set to true, timeouts are set to {@link Double#POSITIVE_INFINITY}.</td>
 * </tr>
 * <tr>
 * <th>{@value #TIME_MULTIPLIER_KEY}</th>
 * <td>A multiplier for timeouts. A value &gt; 1 increases timeouts. 0 or NaN disables timeouts.</td>
 * </tr>
 * </table>
 *
 * @author ham
 */
public final class SeleniumGlobals
{
    private static final SeleniumLogger LOG = new SeleniumLogger(SeleniumGlobals.class);

    public static final String DEBUG_KEY = "selenium-components.debug";
    public static final String TIME_MULTIPLIER_KEY = "selenium-components.timeMultiplier";
    public static final String SHORT_TIMEOUT_IN_SECONDS_KEY = "selenium-components.shortTimeoutInSeconds";
    public static final String LONG_TIMEOUT_IN_SECONDS_KEY = "selenium-components.longTimeoutInSeconds";
    public static final String SCREENSHOT_OUTPUT_TYPE = "selenium-components.screenshotOutputType";

    private static final int IMAGE_SIZE_THRESHOLD = 80000;

    private static boolean debug = false;
    private static double timeMultiplier = 1;
    private static double shortTimeoutInSeconds = 1.0;
    private static double longTimeoutInSeconds = 10;
    private static ScreenshotOutputType screenshotOutputType = ScreenshotOutputType.BASE64;

    /**
     * In contrast to the OutputType.BASE64, this type transforms the image to a low quality JPG. This should avoid
     * truncating by Surefire.
     */
    public static final OutputType<String> LOW_QUALITY_BASE64 = new OutputType<>()
    {
        public String convertFromBase64Png(String base64Png)
        {
            if (base64Png.length() < IMAGE_SIZE_THRESHOLD)
            {
                return "data:image/png;base64," + base64Png;
            }

            return convertFromPngBytes(Base64.getMimeDecoder().decode(base64Png));
        }

        public String convertFromPngBytes(byte[] png)
        {
            BufferedImage image;

            try (ByteArrayInputStream in = new ByteArrayInputStream(png))
            {
                image = ImageIO.read(in);
            }
            catch (IOException e)
            {
                LOG.warn("Failed to transform image. Returning original", e);

                return "data:image/png;base64,"
                    + Base64.getMimeEncoder().encodeToString(png).replace("\r", "").replace("\n", "");
            }

            BufferedImage opaqueImage =
                new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

            opaqueImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

            float quality = 0.5f;
            int attempt = 1;

            while (true)
            {
                ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
                ImageWriteParam param = writer.getDefaultWriteParam();

                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality);

                try (ByteArrayOutputStream out = new ByteArrayOutputStream())
                {
                    try (MemoryCacheImageOutputStream memOut = new MemoryCacheImageOutputStream(out))
                    {
                        writer.setOutput(memOut);
                        writer.write(null, new IIOImage(opaqueImage, null, null), param);

                        out.flush();

                        String result =
                            "data:image/jpg;base64," + Base64.getMimeEncoder().encodeToString(out.toByteArray());

                        if (result.length() < IMAGE_SIZE_THRESHOLD || quality < 0.05f)
                        {
                            if (attempt > 1)
                            {
                                LOG
                                    .debug("Needed %s attempts to reduce image size (quality: %s, size: %s)", attempt,
                                        quality, result.length());
                            }

                            return result.replace("\r", "").replace("\n", "");
                        }

                        quality /= 2;
                        ++attempt;
                    }
                }
                catch (IOException e)
                {
                    LOG.warn("Failed to transform image. Returning original", e);

                    return "data:image/png;base64," + Base64.getMimeEncoder().encodeToString(png);
                }
            }
        }

        public String toString()
        {
            return "OutputType.LOW_QUALITY_BASE64";
        }
    };

    /**
     * In contrast to the OutputType.FILE, this type does not delete the file on exit :rolling-eyes:
     */
    public static final OutputType<File> PERSISTENT_FILE = new OutputType<>()
    {
        @Override
        public File convertFromBase64Png(String base64Png)
        {
            return save(BYTES.convertFromBase64Png(base64Png));
        }

        @Override
        public File convertFromPngBytes(byte[] data)
        {
            return save(data);
        }

        private File save(byte[] data)
        {
            OutputStream stream = null;

            try
            {
                File tmpFile = File.createTempFile("screenshot", ".png");

                stream = new FileOutputStream(tmpFile);
                stream.write(data);

                return tmpFile;
            }
            catch (IOException e)
            {
                throw new WebDriverException(e);
            }
            finally
            {
                if (stream != null)
                {
                    try
                    {
                        stream.close();
                    }
                    catch (IOException e)
                    {
                        // Nothing sane to do
                    }
                }
            }
        }

        @Override
        public String toString()
        {
            return "OutputType.PERSISTENT_FILE";
        }
    };

    static
    {
        initializeFromProperties();

        ThreadUtils.excludeCallElement(Pattern.compile("^java\\..*"));
        ThreadUtils.excludeCallElement(Pattern.compile("^javax\\..*"));
        ThreadUtils.excludeCallElement(Pattern.compile("^com\\.sun\\..*"));
        ThreadUtils.excludeCallElement(Pattern.compile("^sun\\..*"));
        ThreadUtils
            .excludeCallElement(Pattern
                .compile("^"
                    + SeleniumUtils.class
                        .getName()
                        .substring(0, SeleniumUtils.class.getName().lastIndexOf("."))
                        .replace(".", "\\.")
                    + ".*"));
    }

    @SuppressWarnings("null")
    private static void initializeFromProperties()
    {
        String debug = System.getProperty(DEBUG_KEY);

        if ("false".equals(debug))
        {
            setDebug(false);
        }
        else if ("".equals(debug) || "true".equals(debug) || Utils.isDebugging())
        {
            setDebug(true);
        }

        setDoubleFromProperty(TIME_MULTIPLIER_KEY, SeleniumGlobals::setTimeMultiplier);
        setDoubleFromProperty(SHORT_TIMEOUT_IN_SECONDS_KEY, SeleniumGlobals::setShortTimeoutInSeconds);
        setDoubleFromProperty(LONG_TIMEOUT_IN_SECONDS_KEY, SeleniumGlobals::setLongTimeoutInSeconds);
        setEnumFromProperty(SCREENSHOT_OUTPUT_TYPE, ScreenshotOutputType.class,
            SeleniumGlobals::setScreenshotOutputType);
    }

    private SeleniumGlobals()
    {
        super();
    }

    /**
     * Returns true if debugging is enabled
     *
     * @return true if debugging
     */
    public static boolean isDebug()
    {
        return debug;
    }

    /**
     * Sets the debug mode.
     *
     * @param debug the debug mode
     */
    public static void setDebug(boolean debug)
    {
        LOG.info("Setting debug mode to: " + debug);

        SeleniumGlobals.debug = debug;
    }

    /**
     * Some functions rely on timeouts, like the isInvisible call. It not good, but it's a fact. The debug mode breaks
     * these functions. Use this method to disable the debug mode.
     *
     * @param runnable the code to execute
     */
    public static void ignoreDebug(Runnable runnable)
    {
        boolean debug = isDebug();

        try
        {
            SeleniumGlobals.debug = false;

            runnable.run();
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new SeleniumException(LOG.hintAt("Call failed in ignoreDebug()"), e);
        }
        finally
        {
            SeleniumGlobals.debug = debug;
        }
    }

    /**
     * Some functions rely on timeouts, like the isInvisible call. It not good, but it's a fact. The debug mode breaks
     * these functions. Use this method to disable the debug mode.
     *
     * @param <Any> the type of return value
     * @param callable the code to execute
     * @return the result of the call
     */
    public static <Any> Any ignoreDebug(Callable<Any> callable)
    {
        boolean debug = isDebug();

        try
        {
            SeleniumGlobals.debug = false;

            return callable.call();
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new SeleniumException(LOG.hintAt("Call failed in ignoreDebug()"), e);
        }
        finally
        {
            SeleniumGlobals.debug = debug;
        }
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
        LOG.info("Settings time multiplier to %,.1f.", timeMultiplier);

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
        LOG.info("Settings short timeout to %,.1f seconds.", timeMultiplier);

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
        LOG.info("Setting long timeout to %,.1f seconds.", timeMultiplier);

        SeleniumGlobals.longTimeoutInSeconds = longTimeoutInSeconds;
    }

    public static ScreenshotOutputType getScreenshotOutputType()
    {
        return screenshotOutputType;
    }

    public static void setScreenshotOutputType(ScreenshotOutputType screenshotOutputType)
    {
        LOG.info("Setting screenshot output type to: %s", screenshotOutputType);

        SeleniumGlobals.screenshotOutputType = Objects.requireNonNull(screenshotOutputType);
    }

    private static void setDoubleFromProperty(String key, Consumer<Double> setter)
    {
        String value = System.getProperty(key);

        if (value != null)
        {
            try
            {
                setter.accept(Double.parseDouble(value));
            }
            catch (NumberFormatException e)
            {
                throw new IllegalArgumentException("Failed to parse " + key, e);
            }
        }
    }

    private static <T extends Enum<T>> void setEnumFromProperty(String key, Class<T> enumType, Consumer<T> setter)
    {
        String value = System.getProperty(key);

        if (value != null)
        {
            setter.accept(Enum.<T> valueOf(enumType, value));
        }
    }
}
