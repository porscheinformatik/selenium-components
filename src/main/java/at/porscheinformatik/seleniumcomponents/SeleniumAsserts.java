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
    public static <Any> Any assertThatSoon(FailableSupplier<Any> supplier, Matcher<? super Any> matcher)
    {
        return assertThatSoon(SeleniumGlobals.getShortTimeoutInSeconds() + 1, "", supplier, matcher);
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
     */
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
     */
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
                    Any value = supplier.get();

                    result.setValue(value);

                    Boolean r = matcher.matches(value) ? true : null;
                    return r;
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
            // We set the exception here. When the result has stored an exception, it is overriden later on
            Exception exception = e;

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

            description
                .appendText(reason)
                .appendText("\nExpected: ")
                .appendDescriptionOf(matcher)
                .appendText("\n     but: ");
            matcher.describeMismatch(actual, description);

            throw new AssertionError(description.toString(), exception);
        }
    }

    /**
     * An assertion using {@link SeleniumComponent#isReady()} and expecting true.
     *
     * @param component the component
     */
    public static void assertIsReady(SeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component + " is ready", component.isReady());
    }

    /**
     * An assertion using {@link SeleniumComponent#isReady()} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     */
    public static void assertIsReady(double timeoutInSeconds, SeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "There exists " + component, () -> component.isReady(), Matchers.is(true));
    }

    /**
     * An assertion using {@link ClickableSeleniumComponent#isClickable()} and expecting true.
     *
     * @param component the component
     */
    public static void assertIsClickable(ClickableSeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component.toString() + " is clickable", component.isClickable());
    }

    /**
     * An assertion using {@link ClickableSeleniumComponent#isClickable()} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     */
    public static void assertIsClickable(double timeoutInSeconds, ClickableSeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "The component " + component + " is clickable", component::isClickable,
            Matchers.is(true));
    }

    /**
     * An assertion using {@link VisibleSeleniumComponent#isVisible()} and expecting true.
     *
     * @param component the component
     */
    public static void assertIsVisible(VisibleSeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component + " is visible", component.isVisible());
    }

    /**
     * An assertion using {@link VisibleSeleniumComponent#isVisible()} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     */
    public static void assertIsVisible(double timeoutInSeconds, VisibleSeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "The component " + component + " is visible", () -> component.isVisible(),
            Matchers.is(true));
    }

    /**
     * An assertion using {@link EditableSeleniumComponent#isEnabled()} and expecting true.
     *
     * @param component the component
     */
    public static void assertIsEnabled(EditableSeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component + " is enabled", component.isEnabled());
    }

    /**
     * An assertion using {@link EditableSeleniumComponent#isEnabled()} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     */
    public static void assertIsEnabled(double timeoutInSeconds, EditableSeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "The component " + component + " is enabled", component::isEnabled,
            Matchers.is(true));
    }

    /**
     * An assertion using {@link SelectableSeleniumComponent#isSelected()} and expecting true.
     *
     * @param component the component
     */
    public static void assertIsSelected(SelectableSeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component + " is selected", component.isSelected());
    }

    /**
     * An assertion using {@link SelectableSeleniumComponent#isSelected()} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     */
    public static void assertIsSelected(double timeoutInSeconds, SelectableSeleniumComponent component)
    {
        assertThatSoon(timeoutInSeconds, "The component " + component + " is selected", component::isSelected,
            Matchers.is(true));
    }

    /**
     * An assertion using {@link EditableSeleniumComponent#isEditable()} and expecting true.
     *
     * @param component the component
     */
    public static void assertIsEditable(EditableSeleniumComponent component)
    {
        MatcherAssert.assertThat("The component " + component + " is editable", component.isEditable());
    }

    /**
     * An assertion using {@link EditableSeleniumComponent#isEditable()} and expecting true.
     *
     * @param timeoutInSeconds the timeout
     * @param component the component
     */
    public static void assertIsEditable(double timeoutInSeconds, EditableSeleniumComponent component)
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
