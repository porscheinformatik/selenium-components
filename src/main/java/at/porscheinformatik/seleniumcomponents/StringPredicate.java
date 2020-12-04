package at.porscheinformatik.seleniumcomponents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * Predicate for strings including (regular) expressions (that can handle "null" and invalid patterns and tries to match
 * the expression as a string).
 *
 * @author ham
 */
public abstract class StringPredicate extends BaseMatcher<String> implements Predicate<String>
{

    private static final StringPredicate ANY = new StringPredicate()
    {
        @Override
        public boolean test(String value)
        {
            return true;
        }

        @Override
        public String toString()
        {
            return "any";
        }
    };

    private static final StringPredicate NONE = new StringPredicate()
    {
        @Override
        public boolean test(String value)
        {
            return false;
        }

        @Override
        public String toString()
        {
            return "none";
        }
    };

    /**
     * Matches any string.
     *
     * @return the predicate
     */
    public static StringPredicate any()
    {
        return ANY;
    }

    /**
     * Matches no string.
     *
     * @return the predicate
     */
    public static StringPredicate none()
    {
        return NONE;
    }

    /**
     * String must match each predicate
     *
     * @param predicates the predicates
     * @return the predicate
     */
    public static StringPredicate and(StringPredicate... predicates)
    {
        if ((predicates == null) || (predicates.length == 0))
        {
            return none();
        }

        if (predicates.length == 1)
        {
            return predicates[0];
        }

        return new StringPredicate()
        {
            @Override
            public boolean test(String value)
            {
                for (StringPredicate predicate : predicates)
                {
                    if (!predicate.test(value))
                    {
                        return false;
                    }
                }

                return true;
            }

            @Override
            public String toString()
            {
                return "and" + Arrays.toString(predicates);
            }
        };
    }

    /**
     * String must match at least one predicate
     *
     * @param predicates the predicates
     * @return the predicate
     */
    public static StringPredicate or(StringPredicate... predicates)
    {
        if ((predicates == null) || (predicates.length == 0))
        {
            return none();
        }

        if (predicates.length == 1)
        {
            return predicates[0];
        }

        return new StringPredicate()
        {
            @Override
            public boolean test(String value)
            {
                for (StringPredicate predicate : predicates)
                {
                    if (predicate.test(value))
                    {
                        return true;
                    }
                }

                return false;
            }

            @Override
            public String toString()
            {
                return "or" + Arrays.toString(predicates);
            }
        };
    }

    /**
     * Inverts the result
     *
     * @param predicate the predicate
     * @return the predicate
     */
    public static StringPredicate not(StringPredicate predicate)
    {
        return new StringPredicate()
        {
            @Override
            public boolean test(String value)
            {
                return !predicate.test(value);
            }

            @Override
            public String toString()
            {
                return String.format("not[%s]", predicate);
            }
        };
    }

    /**
     * Creates a predicate that checks the string for equality any the specified patterns.
     *
     * @param patterns the patterns
     * @return the predicate
     */
    public static StringPredicate is(String... patterns)
    {
        return is(false, patterns);
    }

    /**
     * Creates a predicate that checks the string for equality with the specified pattern.
     *
     * @param caseSensitive true to respect the case
     * @param patterns the patterns
     * @return the predicate
     */
    public static StringPredicate is(boolean caseSensitive, String... patterns)
    {
        return new StringPredicate()
        {
            @Override
            public boolean test(String value)
            {
                for (String pattern : patterns)
                {
                    if (value == pattern)
                    {
                        return true;
                    }

                    if (value == null || pattern == null)
                    {
                        return false;
                    }

                    if (caseSensitive ? pattern.equals(value) : pattern.equalsIgnoreCase(value))
                    {
                        return true;
                    }
                }

                return false;
            }

            @Override
            public String toString()
            {
                return String.format("is%s", Arrays.toString(patterns));
            }
        };
    }

    public static StringPredicate wildcard(String... patterns)
    {
        return wildcard(false, patterns);
    }

    static StringPredicate wildcard(boolean caseSensitive, String... patterns)
    {
        return new StringPredicate()
        {
            @Override
            public boolean test(String value)
            {
                for (String pattern : patterns)
                {
                    if (matchesUsingWildcards(caseSensitive, pattern, value))
                    {
                        return true;
                    }
                }

                return false;
            }

            @Override
            public String toString()
            {
                return String.format("wildcard%s", Arrays.toString(patterns));
            }
        };
    }

    /**
     * Creates a predicate to match any given regular expression.
     *
     * @param caseSensitive true for caseSensitive
     * @param patterns the expressions
     * @return the predicate
     */
    public static StringPredicate regExp(boolean caseSensitive, String... patterns)
    {
        return regExp(Arrays.stream(patterns).map(pattern -> {
            int flags = Pattern.DOTALL;

            if (!caseSensitive)
            {
                flags |= Pattern.CASE_INSENSITIVE;
            }

            return Pattern.compile(pattern, flags);
        }).toArray(size -> new Pattern[size]));
    }

    /**
     * Creates a predicate to match any given regular expression.
     *
     * @param patterns the expressions
     * @return the predicate
     */
    public static StringPredicate regExp(Pattern... patterns)
    {
        return new StringPredicate()
        {
            @Override
            public boolean test(String value)
            {
                for (Pattern pattern : patterns)
                {
                    if (value == null && pattern == null)
                    {
                        return true;
                    }

                    if (value == null || pattern == null)
                    {
                        return false;
                    }

                    if (pattern.matcher(value).matches())
                    {
                        return true;
                    }
                }

                return false;
            }

            @Override
            public String toString()
            {
                return String.format("regExp%s", Arrays.toString(patterns));
            }
        };
    }

    /**
     * Creates a predicate for a string, wildcard pattern or a regular expression. The pattern may be null to match null
     * (or blank) values. The expression is matched as string, too. If the expression is no valid regular expression
     * pattern only the string match is performed. Matches are case-insensitive.
     *
     * @param caseSensitive true for caseSensitive
     * @param patterns the patterns
     * @return the predicate
     */
    public static StringPredicate like(boolean caseSensitive, String... patterns)
    {
        List<Pattern> regExpPatterns = new ArrayList<>();

        for (String pattern : patterns)
        {
            if (pattern != null)
            {
                try
                {
                    int flags = Pattern.DOTALL;

                    if (!caseSensitive)
                    {
                        flags |= Pattern.CASE_INSENSITIVE;
                    }

                    regExpPatterns.add(Pattern.compile(pattern, flags));
                }
                catch (PatternSyntaxException e)
                {
                    // ignore
                }
            }
        }

        return new StringPredicate()
        {
            @Override
            public boolean test(String value)
            {
                for (String pattern : patterns)
                {
                    if (matchesUsingWildcards(caseSensitive, pattern, value))
                    {
                        return true;
                    }
                }

                for (Pattern regExpPattern : regExpPatterns)
                {
                    if (value == null && regExpPattern == null)
                    {
                        return true;
                    }

                    if (value == null || regExpPattern == null)
                    {
                        return false;
                    }

                    if (regExpPattern.matcher(value).matches())
                    {
                        return true;
                    }
                }

                return false;
            }

            @Override
            public String toString()
            {
                return String.format("like%s", Arrays.toString(patterns));
            }
        };
    }

    /**
     * Creates a predicate for a list of expressions. The expressions are comma-separated and will be trimmed. If an
     * expression starts with an exclamation mark it will be wrapped by a {@link StringPredicate#not(StringPredicate)}
     * and concatenated with {@link StringPredicate#and(StringPredicate...)}, otherwise it will be concatenated with
     * {@link StringPredicate#or(StringPredicate...)}. If the string to parse is null or empty, it returns an
     * {@link StringPredicate#any()} predicate. Any other expression will be passed to
     * {@link #like(boolean, String...)}.
     *
     * @param s the string to parse
     * @return the predicate
     */
    public static StringPredicate parse(String s)
    {
        return parse(false, s);
    }

    /**
     * Creates a predicate for a list of expressions. The expressions are comma-separated and will be trimmed. If an
     * expression starts with an exclamation mark it will be wrapped by a {@link StringPredicate#not(StringPredicate)}
     * and concatenated with {@link StringPredicate#and(StringPredicate...)}, otherwise it will be concatenated with
     * {@link StringPredicate#or(StringPredicate...)}. If the string to parse is null or empty, it returns an
     * {@link StringPredicate#any()} predicate. Any other expression will be passed to
     * {@link #like(boolean, String...)}.
     *
     * @param caseSensitive true for caseSensitive
     * @param s the string to parse
     * @return the predicate
     */
    public static StringPredicate parse(boolean caseSensitive, String s)
    {
        if (Utils.isEmpty(s))
        {
            return any();
        }

        return parse(caseSensitive, Arrays.asList(s.split(",")));
    }

    /**
     * Creates a predicate for a list of expressions. The expressions will be trimmed. If an expression starts with an
     * exclamation mark it will be wrapped by a {@link StringPredicate#not(StringPredicate)} and concatenated with
     * {@link StringPredicate#and(StringPredicate...)}, otherwise it will be concatenated with
     * {@link StringPredicate#or(StringPredicate...)}. If the string to parse is null or empty, it returns an
     * {@link StringPredicate#any()} predicate. Any other expression will be passed to
     * {@link #like(boolean, String...)}.
     *
     * @param values the list of values, may be null or empty
     * @return the predicate
     */
    public static StringPredicate parse(List<String> values)
    {
        return parse(true, values);
    }

    /**
     * Creates a predicate for a list of expressions. The expressions will be trimmed. If an expression starts with an
     * exclamation mark it will be wrapped by a {@link StringPredicate#not(StringPredicate)} and concatenated with
     * {@link StringPredicate#and(StringPredicate...)}, otherwise it will be concatenated with
     * {@link StringPredicate#or(StringPredicate...)}. If the string to parse is null or empty, it returns an
     * {@link StringPredicate#any()} predicate. Any other expression will be passed to
     * {@link #like(boolean, String...)}.
     *
     * @param caseSensitive true for caseSensitive
     * @param values the list of values, may be null or empty
     * @return the predicate
     */
    public static StringPredicate parse(boolean caseSensitive, List<String> values)
    {
        if (Utils.isEmpty(values))
        {
            return any();
        }

        List<StringPredicate> positivePredicates = new ArrayList<>();
        List<StringPredicate> negativePredicates = new ArrayList<>();

        for (String value : values)
        {
            value = value.trim();

            if (value.startsWith("!"))
            {
                negativePredicates.add(not(like(caseSensitive, value.substring(1))));
            }
            else
            {
                positivePredicates.add(like(caseSensitive, value));
            }
        }

        if (positivePredicates.isEmpty())
        {
            return and(negativePredicates.toArray(new StringPredicate[negativePredicates.size()]));
        }

        if (negativePredicates.isEmpty())
        {
            return or(positivePredicates.toArray(new StringPredicate[positivePredicates.size()]));
        }

        return and(and(negativePredicates.toArray(new StringPredicate[negativePredicates.size()])),
            or(positivePredicates.toArray(new StringPredicate[positivePredicates.size()])));
    }

    @Override
    public boolean matches(Object actual)
    {
        return actual == null ? test(null) : test(String.valueOf(actual));
    }

    @Override
    public void describeTo(Description description)
    {
        description.appendValue(toString());
    }

    @Override
    public void describeMismatch(Object actual, Description mismatchDescription)
    {
        mismatchDescription.appendValue(actual).appendText(" does not match ").appendDescriptionOf(this);
    }

    @Override
    public boolean test(String t)
    {
        return false;
    }

    private static boolean matchesUsingWildcards(boolean caseSensitive, String pattern, String value)
    {
        if (!caseSensitive)
        {
            pattern = pattern.toLowerCase();
            value = value.toLowerCase();
        }

        return matchesUsingWildcards(pattern, 0, value, 0);
    }

    // CHECKSTYLE:OFF
    private static boolean matchesUsingWildcards(String pattern, int patternIndex, String value, int valueIndex)
    {
        if (pattern == null && value == null)
        {
            return true;
        }

        if (pattern == null || value == null)
        {
            return false;
        }

        if (Objects.equals(pattern, value))
        {
            return true;
        }

        int tmpPatternIndex = patternIndex;
        int tmpValueIndex = valueIndex;

        while (tmpPatternIndex < pattern.length())
        {
            if ('?' == pattern.charAt(tmpPatternIndex))
            {
                tmpPatternIndex += 1;

                if (tmpValueIndex < value.length())
                {
                    tmpValueIndex += 1;
                }
                else
                {
                    return false;
                }
            }
            else if ('*' == pattern.charAt(tmpPatternIndex))
            {
                while ((tmpPatternIndex < pattern.length()) && ('*' == pattern.charAt(tmpPatternIndex)))
                {
                    tmpPatternIndex += 1;
                }

                if (tmpPatternIndex >= pattern.length())
                {
                    return true;
                }

                while (tmpValueIndex < value.length())
                {
                    if (matchesUsingWildcards(pattern, tmpPatternIndex, value, tmpValueIndex))
                    {
                        return true;
                    }

                    tmpValueIndex += 1;
                }
            }
            else if ((tmpValueIndex < value.length())
                && (pattern.charAt(tmpPatternIndex) == value.charAt(tmpValueIndex)))
            {
                tmpPatternIndex += 1;
                tmpValueIndex += 1;
            }
            else
            {
                return false;
            }
        }

        return ((tmpPatternIndex >= pattern.length()) && (tmpValueIndex >= value.length()));
    }
    // CHECKSTYLE:ON
}
