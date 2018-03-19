package at.porscheinformatik.seleniumcomponents;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Utilities for handling of Threads
 *
 * @author ham
 */
public final class ThreadUtils
{

    private static final ThreadLocal<Supplier<String>> PARENT_THREAD_CALL_LINE_SUPPLIER = new ThreadLocal<>();
    private static final List<Pattern> INCLUDED_CALL_ELEMENTS = new ArrayList<>();
    private static final List<Pattern> EXCLUDED_CALL_ELEMENTS = new ArrayList<>();

    private ThreadUtils()
    {
        super();
    }

    /**
     * Adds an item to the list of {@link Pattern}s for method names, that should be included in call stacks. This
     * patterns are used in the {@link ThreadUtils#describeCallLine()} methods to find the caller excluding the
     * framework itself.
     *
     * @param pattern the pattern
     */
    public static void includeCallElement(Pattern pattern)
    {
        if (INCLUDED_CALL_ELEMENTS.contains(pattern))
        {
            return;
        }

        INCLUDED_CALL_ELEMENTS.add(Objects.requireNonNull(pattern));
    }

    /**
     * Returns a list of {@link Pattern}s for method names, that should be included in call stacks. This patterns are
     * used in the {@link ThreadUtils#describeCallLine()} methods to find the caller excluding the framework itself.
     *
     * @return the patterns
     */
    public static List<Pattern> getIncludeCallElements()
    {
        return INCLUDED_CALL_ELEMENTS;
    }

    /**
     * Adds an item to the list of {@link Pattern}s for method names, that should be excluded in call stacks. This
     * patterns are used in the {@link ThreadUtils#describeCallLine()} methods to find the caller excluding the
     * framework itself.
     *
     * @param pattern the pattern
     */
    public static void excludeCallElement(Pattern pattern)
    {
        if (EXCLUDED_CALL_ELEMENTS.contains(pattern))
        {
            return;
        }

        EXCLUDED_CALL_ELEMENTS.add(Objects.requireNonNull(pattern));
    }

    /**
     * Returns a list of {@link Pattern}s for method names, that should be excluded in call stacks. This patterns are
     * used in the {@link ThreadUtils#describeCallLine()} methods to find the caller excluding the framework itself.
     *
     * @return the patterns
     */
    public static List<Pattern> getExcludedCallElements()
    {
        return EXCLUDED_CALL_ELEMENTS;
    }

    /**
     * When redirecting a call to another thread the {@link #describeCallLine()} method cannot determine the call line
     * anymore. This method can be used to create a callable (wrapper) that stores a supplier for the call line to avoid
     * this issue.
     *
     * @param <T> the type of result
     * @param callable the callable
     * @return the enhanced callable
     */
    public static <T> Callable<T> persistCallLine(Callable<T> callable)
    {
        Thread parentThread = Thread.currentThread();

        return () -> {
            Supplier<String> callLineSupplierBackup = PARENT_THREAD_CALL_LINE_SUPPLIER.get();

            PARENT_THREAD_CALL_LINE_SUPPLIER.set(() -> describeCallLine(parentThread));

            try
            {
                return callable.call();
            }
            finally
            {
                PARENT_THREAD_CALL_LINE_SUPPLIER.set(callLineSupplierBackup);
            }
        };
    }

    /**
     * Describes the call line determined from the current threads stack trace.
     *
     * @return the call line
     */
    public static String describeCallLine()
    {
        return describeCallLine(Thread.currentThread());
    }

    private static String describeCallLine(Thread thread)
    {
        String result = toCallLine(findCallElement(thread, INCLUDED_CALL_ELEMENTS, EXCLUDED_CALL_ELEMENTS));

        if ("(?:?)".equals(result))
        {
            Supplier<String> supplier = PARENT_THREAD_CALL_LINE_SUPPLIER.get();

            if (supplier != null)
            {
                result = supplier.get();
            }
        }

        return result;
    }

    private static StackTraceElement findCallElement(Thread thread, List<Pattern> includedCallElements,
        List<Pattern> excludedCallElements)
    {
        StackTraceElement[] stackTrace = thread.getStackTrace();

        outerLoop: for (StackTraceElement element : stackTrace)
        {
            String methodName = element.getClassName() + "." + element.getMethodName();

            if (methodName.startsWith(SeleniumUtils.class.getName()))
            {
                // skip myself
                continue;
            }

            if (includedCallElements != null && includedCallElements.size() > 0)
            {
                boolean hit = false;

                for (Pattern pattern : includedCallElements)
                {
                    if (pattern.matcher(methodName).matches())
                    {
                        hit = true;
                        break;
                    }
                }

                if (!hit)
                {
                    continue;
                }
            }

            for (Pattern pattern : excludedCallElements)
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

    private static String toCallLine(StackTraceElement element)
    {
        return element != null ? element.getClassName()
            + "."
            + element.getMethodName()
            + "("
            + element.getFileName()
            + ":"
            + element.getLineNumber()
            + ")" : "(?:?)";
    }

}
