/**
 *
 */
package at.porscheinformatik.seleniumcomponents;

import java.util.Locale;

/**
 * @author Daniel Furtlehner
 */
public final class LocaleUtils {

    private LocaleUtils() {}

    /**
     * Parses the locale from the specified string. Expects the locale in the form of: language [SEPARATOR country
     * [SEPARATOR variant]]. The SEPARATOR may be '-' or '_'. If the string is empty or null it returns null.
     *
     * @param value the string
     * @return the locale
     */
    public static Locale toLocale(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Locale.ROOT;
        }

        String[] values = value.split("[_-]");

        if (values.length >= 3) {
            return new Locale(values[0], values[1], values[2]);
        }

        if (values.length == 2) {
            return new Locale(values[0], values[1]);
        }

        return new Locale(values[0]);
    }
}
