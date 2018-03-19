package at.porscheinformatik.seleniumcomponents;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * Common utilities for the library.
 *
 * @author ham
 */
public final class Utils
{

    private static final boolean IS_DEBUGGING =
        java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("jdwp") >= 0;

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

    public static String simplify(String s)
    {
        return s.replaceAll("[^\\p{IsLatin}^\\d]", "").toLowerCase();
    }

    public static double simplifiedLevenshteinDistance(String lhs, String rhs)
    {
        return levenshteinDistance(simplify(lhs), simplify(rhs));
    }

    /**
     * Source:
     * <a href="https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java">Wikibooks</a>
     *
     * @param lhs left string
     * @param rhs right string
     * @return the distance
     */
    public static double levenshteinDistance(String lhs, String rhs)
    {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++)
        {
            cost[i] = i;
        }

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++)
        {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++)
            {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int costReplace = cost[i - 1] + match;
                int costInsert = cost[i] + 1;
                int costDelete = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(costInsert, costDelete), costReplace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return (double) (cost[len0 - 1]) / Math.max(len0, len1);
    }

    /**
     * Returns true if the JVM runs in debug mode
     *
     * @return true if in debug mode
     */
    public static boolean isDebugging()
    {
        return IS_DEBUGGING;
    }
}
