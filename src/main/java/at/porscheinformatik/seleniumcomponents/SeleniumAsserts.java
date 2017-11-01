package at.porscheinformatik.seleniumcomponents;

import java.util.function.Supplier;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.StringDescription;

/**
 * Asserts for {@link SeleniumComponent}s.
 *
 * @author HAM
 */
public final class SeleniumAsserts
{

    /**
     * A derived value.
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

    /**
     * An assertion that keeps calling the supplier for {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds until
     * the matcher succeeds. The assertion fails after the specified {@link SeleniumGlobals#getShortTimeoutInSeconds()}
     * seconds.
     *
     * @param <Any> the type of the tested value
     * @param supplier the supplier of the tested value
     * @param matcher the matcher for the tested value
     * @return the result of the supplier
     */
    public static <Any> Any assertThatSoon(Supplier<Any> supplier, Matcher<? super Any> matcher)
    {
        return assertThatSoon(SeleniumGlobals.getShortTimeoutInSeconds(), "", supplier, matcher);
    }

    /**
     * An assertion that keeps calling the supplier for the specified amount of seconds until the matcher succeeds. The
     * assertion fails after the specified timeout.
     *
     * @param <Any> the type of the tested value
     * @param timeoutInSeconds the timeout in seconds
     * @param supplier the supplier of the tested value
     * @param matcher the matcher for the tested value
     * @return the result of the supplier
     */
    public static <Any> Any assertThatSoon(double timeoutInSeconds, Supplier<Any> supplier,
        Matcher<? super Any> matcher)
    {
        return assertThatSoon(timeoutInSeconds, "", supplier, matcher);
    }

    /**
     * An assertion that keeps calling the supplier for {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds until
     * the matcher succeeds. The assertion fails after the specified {@link SeleniumGlobals#getShortTimeoutInSeconds()}
     * seconds.
     *
     * @param <Any> the type of the tested value
     * @param reason the reason for the assertion
     * @param supplier the supplier of the tested value
     * @param matcher the matcher for the tested value
     * @return the result of the supplier
     */
    public static <Any> Any assertThatSoon(String reason, Supplier<Any> supplier, Matcher<? super Any> matcher)
    {
        return assertThatSoon(SeleniumGlobals.getShortTimeoutInSeconds(), reason, supplier, matcher);
    }

    /**
     * An assertion that keeps calling the supplier for the specified amount of seconds until the matcher succeeds. The
     * assertion fails after the specified timeout.
     *
     * @param <Any> the type of the tested value
     * @param timeoutInSeconds the timeout in seconds
     * @param reason the reason for the assertion
     * @param supplier the supplier of the tested value
     * @param matcher the matcher for the tested value
     * @return the result of the supplier
     */
    public static <Any> Any assertThatSoon(double timeoutInSeconds, String reason, Supplier<Any> supplier,
        Matcher<? super Any> matcher)
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

            return result.getValue();
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

    /**
     * An assertion using {@link SeleniumActions#isReady(SeleniumComponent)} and expecting true.
     *
     * @param component the component
     */
    public static void assertIsReady(SeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component + " is ready", SeleniumActions.isReady(component));
    }

    /**
     * An assertion using {@link SeleniumActions#isReady(SeleniumComponent)} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     */
    public static void assertIsReady(double timeoutInSeconds, SeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "There exists " + component, () -> SeleniumActions.isReady(component),
            Matchers.is(true));
    }

    /**
     * An assertion using {@link SeleniumActions#isClickable(SeleniumComponent)} and expecting true.
     *
     * @param component the component
     */
    public static void assertIsClickable(SeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component.toString() + " is clickable",
            SeleniumActions.isClickable(component));
    }

    /**
     * An assertion using {@link SeleniumActions#isClickable(SeleniumComponent)} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     */
    public static void assertIsClickable(double timeoutInSeconds, SeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "The component " + component + " is clickable",
            () -> SeleniumActions.isClickable(component), Matchers.is(true));
    }

    /**
     * An assertion using {@link SeleniumActions#isVisible(SeleniumComponent)} and expecting true.
     *
     * @param component the component
     */
    public static void assertIsVisible(SeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component + " is visible", SeleniumActions.isVisible(component));
    }

    /**
     * An assertion using {@link SeleniumActions#isVisible(SeleniumComponent)} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     */
    public static void assertIsVisible(double timeoutInSeconds, SeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "The component " + component + " is visible",
            () -> SeleniumActions.isVisible(component), Matchers.is(true));
    }

    public static void assertContainsDescendant(SeleniumComponent component, WebElementSelector selector)
    {
        MatcherAssert.assertThat("The component \"" + component + "\" has a descendant: " + selector,
            SeleniumActions.containsDescendant(component, selector));
    }
}
