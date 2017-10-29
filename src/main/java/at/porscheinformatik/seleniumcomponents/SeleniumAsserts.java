package at.porscheinformatik.seleniumcomponents;

import java.util.function.Supplier;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.StringDescription;

/**
 * Asserts for {@link SeleniumComponent}s.
 *
 * @author HAM
 */
public final class SeleniumAsserts extends MatcherAssert
{

    /**
     * A derived value
     *
     * @author ham
     * @param <Any> the type of the value
     */
    private static class Result<Any>
    {
        private Any value = null;
        private Exception exception = null;

        public Any getValue() throws Exception
        {
            if (exception != null)
            {
                throw exception;
            }

            return value;
        }

        public void setValue(Any value)
        {
            this.value = value;
            this.exception = null;
        }

        public void setException(Exception exception)
        {
            this.exception = exception;
        }
    }

    private SeleniumAsserts()
    {
        super();
    }

    public static <Any> void assertThatSoon(Supplier<Any> supplier, Matcher<Any> matcher)
    {
        assertThatSoon(1, "", supplier, matcher);
    }

    public static <Any> void assertThatSoon(double timeoutInSeconds, Supplier<Any> supplier, Matcher<Any> matcher)
    {
        assertThatSoon(timeoutInSeconds, "", supplier, matcher);
    }

    public static <Any> void assertThatSoon(String reason, Supplier<Any> supplier, Matcher<Any> matcher)
    {
        assertThatSoon(1, reason, supplier, matcher);
    }

    public static <Any> void assertThatSoon(double timeoutInSeconds, String reason, Supplier<Any> supplier,
        Matcher<Any> matcher)
    {
        Result<Any> result = new Result<>();

        try
        {
            SeleniumUtils.keepTrying(timeoutInSeconds, () -> {
                try
                {
                    Any value = supplier.get();

                    result.setValue(value);

                    return matcher.matches(value) ? true : null;
                }
                catch (Exception e)
                {
                    result.setException(e);

                    return null;
                }
            });
        }
        catch (Exception e)
        {
            Object actual;
            Exception exception = null;

            try
            {
                actual = result.getValue();
            }
            catch (Exception e1)
            {
                actual = e1.getMessage();
                exception = e1;
            }

            Description description = new StringDescription();

            description.appendText(reason).appendText("\nExpected: ").appendDescriptionOf(matcher).appendText(
                "\n     but: ");
            matcher.describeMismatch(actual, description);

            throw new AssertionError(description.toString(), exception);
        }
    }

    public static void assertExists(SeleniumComponent component)
    {
        assertThat("There exists " + component.describe(), SeleniumActions.exists(component));
    }

    public static void assertExists(double timeoutInSeconds, SeleniumComponent component)
    {
        assertThat("There exists " + component.describe(), SeleniumActions.exists(timeoutInSeconds, component));
    }

    public static void assertIsClickable(SeleniumComponent component)
    {
        assertThat("The component " + component.describe() + " is clickable", SeleniumActions.isClickable(component));
    }

    public static void assertIsClickable(double timeoutInSeconds, SeleniumComponent component)
    {
        assertThat("The component " + component.describe() + " is clickable",
            SeleniumActions.isClickable(timeoutInSeconds, component));
    }

    public static void assertIsVisible(SeleniumComponent component)
    {
        assertThat("The component " + component.describe() + " is visible", SeleniumActions.isVisible(component));
    }

    public static void assertIsVisible(double timeoutInSeconds, SeleniumComponent component)
    {
        assertThat("The component " + component.describe() + " is visible",
            SeleniumActions.isVisible(timeoutInSeconds, component));
    }

    public static void assertContainsDescendant(SeleniumComponent component, WebElementSelector selector)
    {
        assertThat("The component \"" + component.describe() + "\" has a descendant: " + selector,
            SeleniumActions.containsDescendant(component, selector));
    }
}
