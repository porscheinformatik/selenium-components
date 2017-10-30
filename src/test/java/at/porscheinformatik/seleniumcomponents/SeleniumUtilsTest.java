package at.porscheinformatik.seleniumcomponents;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link SeleniumUtils}.
 *
 * @author ham
 */
public class SeleniumUtilsTest
{

    @Test
    public void callWithoutTimeout()
    {
        assertThat(SeleniumUtils.callWithTimeout(0, () -> 42), is(42));

        assertThat(SeleniumUtils.callWithTimeout(0, () -> {
            SeleniumUtils.waitForSeconds(0.2);
            return 42;
        }), is(42));
    }

    @Test
    public void callWithTimeout()
    {
        assertThat(SeleniumUtils.callWithTimeout(0.1, () -> 42), is(42));

        try
        {
            int result = SeleniumUtils.callWithTimeout(0.1, () -> {
                SeleniumUtils.waitForSeconds(0.2);
                return 42;
            });

            Assert.fail("Exception expected, but got " + result);
        }
        catch (Exception e)
        {
            assertThat(e, instanceOf(SeleniumTimeoutException.class));
        }
    }

    @Test
    public void callWithTimeoutAndException()
    {
        try
        {
            int result = SeleniumUtils.callWithTimeout(0.1, () -> {
                throw new NullPointerException();
            });

            Assert.fail("Exception expected, but got " + result);
        }
        catch (Exception e)
        {
            assertThat(e, instanceOf(SeleniumException.class));
            assertThat(e.getCause(), instanceOf(NullPointerException.class));
        }
    }

    @Test
    public void keepTryingWithoutTimeout()
    {
        try
        {
            int result = SeleniumUtils.keepTrying(0, () -> null);

            Assert.fail("Exception expected, but got " + result);
        }
        catch (Exception e)
        {
            assertThat(e, instanceOf(SeleniumFailException.class));
        }

        assertThat(SeleniumUtils.keepTrying(0, () -> 42), is(42));

        assertThat(SeleniumUtils.keepTrying(0, () -> Optional.of(42)).get(), is(42));

        try
        {
            boolean result = SeleniumUtils.keepTrying(0, () -> Optional.empty()).isPresent();

            Assert.fail("Exception expected, but got " + result);
        }
        catch (Exception e)
        {
            assertThat(e, instanceOf(SeleniumFailException.class));
        }

        assertThat(SeleniumUtils.keepTrying(0, () -> {
            SeleniumUtils.waitForSeconds(0.2);
            return 42;
        }), is(42));
    }

    @Test
    public void keepTryingWithTimeout()
    {
        try
        {
            int result = SeleniumUtils.keepTrying(0.1, () -> null);

            Assert.fail("Exception expected, but got " + result);
        }
        catch (Exception e)
        {
            assertThat(e, instanceOf(SeleniumFailException.class));
        }

        assertThat(SeleniumUtils.keepTrying(0.1, () -> 42), is(42));

        assertThat(SeleniumUtils.keepTrying(0.1, () -> Optional.of(42)).get(), is(42));

        try
        {
            boolean result = SeleniumUtils.keepTrying(0.1, () -> Optional.empty()).isPresent();

            Assert.fail("Exception expected, but got " + result);
        }
        catch (Exception e)
        {
            assertThat(e, instanceOf(SeleniumFailException.class));
        }

        try
        {
            assertThat(SeleniumUtils.keepTrying(0.1, () -> {
                SeleniumUtils.waitForSeconds(0.2);
                return 42;
            }), is(42));
        }
        catch (Exception e)
        {
            assertThat(e, instanceOf(SeleniumFailException.class));
        }

        AtomicInteger count = new AtomicInteger(3);

        assertThat(SeleniumUtils.keepTrying(1, () -> count.decrementAndGet() > 0 ? null : 42), is(42));
    }

}
