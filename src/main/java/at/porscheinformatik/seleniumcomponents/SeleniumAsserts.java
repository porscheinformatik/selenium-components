package at.porscheinformatik.seleniumcomponents;

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
    //    private static final SeleniumLogger LOG = new SeleniumLogger(SeleniumAsserts.class);

    /**
     * A derived value.
     *
     * @author ham
     * @param <Any> the type of the value
     */
    private static class Result<Any>
    {
        private Any value = null;
        private Throwable exception = null;

        public Any aquire() throws Throwable
        {
            if (exception != null)
            {
                throw exception;
            }

            return value;
        }

        public Any getValue()
        {
            return value;
        }

        public void setValue(Any value)
        {
            this.value = value;
            this.exception = null;
        }

        public Throwable getException()
        {
            return exception;
        }

        public void setException(Throwable exception)
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
    public static <Any> Any assertThatSoon(FailableSupplier<Any> supplier, Matcher<? super Any> matcher)
    {
        return assertThatSoon(SeleniumGlobals.getShortTimeoutInSeconds() + 1, "", supplier, matcher);
    }

    /**
     * Same as {@link #assertThatSoon(FailableSupplier, Matcher)} but keeps trying for
     * {@link SeleniumGlobals#getLongTimeoutInSeconds()}
     *
     * @param <Any> the type of the tested value
     * @param supplier the supplier of the tested value
     * @param matcher the matcher for the tested value
     * @see #assertThatSoon(FailableSupplier, Matcher)
     * @return the result of the supplier
     */
    public static <Any> Any assertThatLater(FailableSupplier<Any> supplier, Matcher<? super Any> matcher)
    {
        return assertThatSoon(SeleniumGlobals.getLongTimeoutInSeconds(), "", supplier, matcher);
    }

    /**
     * An assertion that checks the component for {@link SeleniumGlobals#getShortTimeoutInSeconds()} seconds until the
     * matcher succeeds. The assertion fails after the specified {@link SeleniumGlobals#getShortTimeoutInSeconds()}
     * seconds.
     *
     * @param <ComponentT> the type of the tested component
     * @param component the component to test
     * @param matcher the matcher for the tested value
     * @return the component itself
     * @deprecated use {@link #assertThatSoon(FailableSupplier, Matcher)} with "() -&gt; component" as parameter in
     *             order to simplify the API
     */
    @Deprecated
    public static <ComponentT extends SeleniumComponent> ComponentT assertComponent(ComponentT component,
        Matcher<? super ComponentT> matcher)
    {
        return assertThatSoon(() -> component, matcher);
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
    public static <Any> Any assertThatSoon(double timeoutInSeconds, FailableSupplier<Any> supplier,
        Matcher<? super Any> matcher)
    {
        return assertThatSoon(timeoutInSeconds, "", supplier, matcher);
    }

    /**
     * An assertion that checks the component for the specified amount of seconds until the matcher succeeds. The
     * assertion fails after the specified timeout.
     *
     * @param <ComponentT> the type of the tested component
     * @param timeoutInSeconds the timeout in seconds
     * @param component the component to test
     * @param matcher the matcher for the tested value
     * @return the result of the supplier
     * @deprecated use {@link #assertThatSoon(double, FailableSupplier, Matcher)} with "() -&gt; component" as parameter
     *             in order to simplify the API
     */
    @Deprecated
    public static <ComponentT extends SeleniumComponent> ComponentT assertComponent(double timeoutInSeconds,
        ComponentT component, Matcher<? super ComponentT> matcher)
    {
        return assertThatSoon(timeoutInSeconds, (FailableSupplier<ComponentT>) () -> component, matcher);
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
    public static <Any> Any assertThatSoon(String reason, FailableSupplier<Any> supplier, Matcher<? super Any> matcher)
    {
        return assertThatSoon(SeleniumGlobals.getShortTimeoutInSeconds() + 1, reason, supplier, matcher);
    }

    /**
     * Same as {@link #assertThatSoon(String, FailableSupplier, Matcher)} but keeps trying for
     * {@link SeleniumGlobals#getLongTimeoutInSeconds()}
     *
     * @param <Any> the type of the tested value
     * @param reason the reason for the assertion
     * @param supplier the supplier of the tested value
     * @param matcher the matcher for the tested value
     * @see #assertThatSoon(String, FailableSupplier, Matcher)
     * @return the result of the supplier
     */
    public static <Any> Any assertThatLater(String reason, FailableSupplier<Any> supplier, Matcher<? super Any> matcher)
    {
        return assertThatSoon(SeleniumGlobals.getLongTimeoutInSeconds(), reason, supplier, matcher);
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
    public static <Any> Any assertThatSoon(double timeoutInSeconds, String reason, FailableSupplier<Any> supplier,
        Matcher<? super Any> matcher)
    {
        Result<Any> result = new Result<>();

        try
        {
            SeleniumUtils.keepTrying(timeoutInSeconds, () -> {
                try
                {
                    Any actual = supplier.get();

                    result.setValue(actual);

                    if (matcher.matches(actual))
                    {
                        return true;
                    }
                }
                catch (Throwable e)
                {
                    result.setException(e);
                }

                return null;
            });

            return result.aquire();
        }
        catch (Throwable e)
        {
            Object actual = result.getException();

            if (actual == null)
            {
                actual = result.getValue();
            }

            Description description = new StringDescription();

            description
                .appendText(reason)
                .appendText(System.lineSeparator())
                .appendText("Expected: ")
                .appendDescriptionOf(matcher)
                .appendText(System.lineSeparator())
                .appendText("     but: ");

            matcher.describeMismatch(actual, description);

            AssertionError error;

            if (result.getException() != null)
            {
                error = new AssertionError(description.toString(), result.getException());
                error.addSuppressed(e);
            }
            else
            {
                error = new AssertionError(description.toString(), e);
            }

            throw error;
        }
    }

    /**
     * An assertion using {@link SeleniumComponent#isReady()} and expecting true.
     *
     * @param component the component
     * @deprecated replace with {@link SeleniumMatchers#isReady()}
     */
    @Deprecated
    public static void assertIsReady(SeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component + " is ready", component.isReady());
    }

    /**
     * An assertion using {@link SeleniumComponent#isReady()} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     * @deprecated replace with {@link SeleniumMatchers#isReady()}
     */
    @Deprecated
    public static void assertIsReady(double timeoutInSeconds, SeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "There exists " + component, () -> component.isReady(), Matchers.is(true));
    }

    /**
     * An assertion using {@link ActiveSeleniumComponent#isClickable()} and expecting true.
     *
     * @param component the component
     * @deprecated replace with {@link SeleniumMatchers#isClickable()}
     */
    @Deprecated
    public static void assertIsClickable(ActiveSeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component.toString() + " is clickable", component.isClickable());
    }

    /**
     * An assertion using {@link ActiveSeleniumComponent#isClickable()} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     * @deprecated replace with {@link SeleniumMatchers#isClickable()}
     */
    @Deprecated
    public static void assertIsClickable(double timeoutInSeconds, ActiveSeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "The component " + component + " is clickable", component::isClickable,
            Matchers.is(true));
    }

    /**
     * An assertion using {@link SeleniumComponent#isVisible()} and expecting true.
     *
     * @param component the component
     * @deprecated replace with {@link SeleniumMatchers#isVisible()}
     */
    @Deprecated
    public static void assertIsVisible(SeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component + " is visible", component.isVisible());
    }

    /**
     * An assertion using {@link SeleniumComponent#isVisible()} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     * @deprecated replace with {@link SeleniumMatchers#isVisible()}
     */
    @Deprecated
    public static void assertIsVisible(double timeoutInSeconds, SeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "The component " + component + " is visible", () -> component.isVisible(),
            Matchers.is(true));
    }

    /**
     * An assertion using {@link ActiveSeleniumComponent#isEnabled()} and expecting true.
     *
     * @param component the component
     * @deprecated replace with {@link SeleniumMatchers#isEnabled()}
     */
    @Deprecated
    public static void assertIsEnabled(ActiveSeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component + " is enabled", component.isEnabled());
    }

    /**
     * An assertion using {@link ActiveSeleniumComponent#isEnabled()} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     * @deprecated replace with {@link SeleniumMatchers#isEnabled()}
     */
    @Deprecated
    public static void assertIsEnabled(double timeoutInSeconds, ActiveSeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "The component " + component + " is enabled", component::isEnabled,
            Matchers.is(true));
    }

    /**
     * An assertion using {@link ActiveSeleniumComponent#isSelected()} and expecting true.
     *
     * @param component the component
     * @deprecated replace with {@link SeleniumMatchers#isSelected()}
     */
    @Deprecated
    public static void assertIsSelected(ActiveSeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component + " is selected", component.isSelected());
    }

    /**
     * An assertion using {@link ActiveSeleniumComponent#isSelected()} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     * @deprecated replace with {@link SeleniumMatchers#isSelected()}
     */
    @Deprecated
    public static void assertIsSelected(double timeoutInSeconds, ActiveSeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "The component " + component + " is selected", component::isSelected,
            Matchers.is(true));
    }

    /**
     * An assertion using {@link ActiveSeleniumComponent#isEditable()} and expecting true.
     *
     * @param component the component
     * @deprecated replace with {@link SeleniumMatchers#isEditable()}
     */
    @Deprecated
    public static void assertIsEditable(ActiveSeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component + " is editable", component.isEditable());
    }

    /**
     * An assertion using {@link ActiveSeleniumComponent#isEditable()} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     * @deprecated replace with {@link SeleniumMatchers#isEditable()}
     */
    @Deprecated
    public static void assertIsEditable(double timeoutInSeconds, ActiveSeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "The component " + component + " is editable", component::isEditable,
            Matchers.is(true));
    }

    /**
     * An assertion using {@link SeleniumUtils#getTagName(SeleniumComponent)} and expects the specified name.
     *
     * @param component the component
     * @param tagName the name
     */
    public static void assertTagName(SeleniumComponent component, String tagName)
    {
        MatcherAssert
            .assertThat(String.format("The component \"%s\" has the tag name \"%s\"", component, tagName),
                SeleniumUtils.getTagName(component), Matchers.is(tagName));
    }

    /**
     * An assertion using {@link SeleniumUtils#getTagName(SeleniumComponent)} and expects the specified name.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     * @param tagName the name
     */
    public static void assertTagName(double timeoutInSeconds, SeleniumComponent component, String tagName)
    {
        assertThatSoon(timeoutInSeconds,
            String.format("The component \"%s\" has the tag name \"%s\"", component, tagName),
            () -> SeleniumUtils.getTagName(component), Matchers.is(tagName));
    }

    /**
     * An assertion using {@link SeleniumUtils#getAttribute(SeleniumComponent, String)} and expects the specified value.
     *
     * @param component the component
     * @param name the name of the attribute
     * @param value the expected value
     */
    public static void assertAttribute(SeleniumComponent component, String name, String value)
    {
        MatcherAssert
            .assertThat(String
                .format("The component \"%s\" has the attribute \"%s\" with the value \"%s\"", component, name, value),
                SeleniumUtils.getAttribute(component, name), Matchers.is(value));
    }

    /**
     * An assertion using {@link SeleniumUtils#getAttribute(SeleniumComponent, String)} and expects the specified value.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     * @param name the name of the attribute
     * @param value the expected value
     */
    public static void assertAttribute(double timeoutInSeconds, SeleniumComponent component, String name, String value)
    {
        assertThatSoon(timeoutInSeconds,
            String
                .format("The component \"%s\" has the attribute \"%s\" with the value \"%s\"", component, name, value),
            () -> SeleniumUtils.getAttribute(component, name), Matchers.is(value));
    }

    /**
     * An assertion using {@link SeleniumUtils#getText(SeleniumComponent)} and expects the specified text.
     *
     * @param component the component
     * @param text the text
     */
    public static void assertText(SeleniumComponent component, String text)
    {
        MatcherAssert
            .assertThat(String.format("The component \"%s\" has the tag name \"%s\"", component, text),
                SeleniumUtils.getText(component), Matchers.is(text));
    }

    /**
     * An assertion using {@link SeleniumUtils#getText(SeleniumComponent)} and expects the specified text.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     * @param text the text
     */
    public static void assertText(double timeoutInSeconds, SeleniumComponent component, String text)
    {
        assertThatSoon(timeoutInSeconds, String.format("The component \"%s\" has the tag name \"%s\"", component, text),
            () -> SeleniumUtils.getText(component), Matchers.is(text));
    }

    public static void assertContainsDescendant(SeleniumComponent component, WebElementSelector selector)
    {
        MatcherAssert
            .assertThat("The component \"" + component + "\" has a descendant: " + selector,
                SeleniumUtils.containsDescendant(component, selector));
    }
}
