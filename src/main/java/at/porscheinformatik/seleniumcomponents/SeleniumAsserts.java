package at.porscheinformatik.seleniumcomponents;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
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
            exception = null;
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
}
