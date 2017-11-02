package at.porscheinformatik.seleniumcomponents;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Common utilities for the library.
 *
 * @author ham
 */
public final class Utils
{

    private Utils()
    {
        super();
    }

    /**
     * Null-safe empty and blank check for strings.
     *
     * @param s the string
     * @return true if empty
     */
    public static boolean isEmpty(String s)
    {
        return s == null || s.trim().length() == 0;
    }

    /**
     * Null-safe empty function for arrays.
     *
     * @param <Any> the type of array items
     * @param array the array
     * @return true if null or empty
     */
    public static <Any> boolean isEmpty(Any[] array)
    {
        return array == null || Array.getLength(array) <= 0;
    }

    /**
     * Null-safe empty function for collections.
     *
     * @param collection the collection
     * @return true if null or empty
     */
    public static boolean isEmpty(Collection<?> collection)
    {
        return collection == null || collection.isEmpty();
    }

    /**
     * Null-safe empty function for maps.
     *
     * @param map the maps
     * @return true if null or empty
     */
    public static boolean isEmpty(Map<?, ?> map)
    {
        return map == null || map.isEmpty();
    }

    public static String repeat(String s, int length)
    {
        StringBuilder builder = new StringBuilder();

        while (builder.length() < length)
        {
            builder.append(s);
        }

        return builder.substring(0, length);
    }

    /**
     * Returns the name of the class in a short and readable form.
     *
     * @param type the class, may be null
     * @return the name
     */
    public static String toClassName(Class<?> type)
    {
        if (type == null)
        {
            return "?";
        }

        String name = "";

        while (type.isArray())
        {
            name = "[]" + name;
            type = type.getComponentType();
        }

        if (type.isPrimitive())
        {
            name = type.getName() + name;
        }
        else
        {
            name = Utils.getShortName(type.getName()) + name;
        }

        return name;
    }

    private static String getShortName(String currentName)
    {
        int beginIndex = currentName.lastIndexOf('.');

        if (beginIndex >= 0)
        {
            currentName = currentName.substring(beginIndex + 1);
        }

        return currentName;
    }

    static String toCallLine(StackTraceElement element)
    {
        return element != null ? "(" + element.getFileName() + ":" + element.getLineNumber() + ")" : "(?:?)";
    }

    public static String describeCallLine()
    {
        return toCallLine(findCallElement(SeleniumGlobals.getIgnorableCallElements()));
    }

    private static StackTraceElement findCallElement(List<Pattern> ignorableCallElements)
    {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        outerLoop: for (StackTraceElement element : stackTrace)
        {
            String methodName = element.getClassName() + "." + element.getMethodName();

            if (methodName.startsWith(SeleniumUtils.class.getName()))
            {
                // skip myself
                continue;
            }

            for (Pattern pattern : ignorableCallElements)
            {
                if (pattern.matcher(methodName).matches())
                {
                    continue outerLoop;
                }
            }

            return element;
        }

        return null;
    }
}
