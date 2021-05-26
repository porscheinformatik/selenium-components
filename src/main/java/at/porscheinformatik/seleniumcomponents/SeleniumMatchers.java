/**
 *
 */
package at.porscheinformatik.seleniumcomponents;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * @author Daniel Furtlehner
 */
public final class SeleniumMatchers
{

    private SeleniumMatchers()
    {
        super();
    }

    /**
     * @param <ComponentT> type of components in the list
     * @return matcher that checks if the component list has some items.
     */
    public static <ComponentT extends SeleniumComponent> Matcher<SeleniumComponentList<ComponentT>> hasItems()
    {
        return new ComponentListHasItemsMatcher<>();
    }

    /**
     * @param <ComponentT> type of components in the stream
     * @param <AssertionT> type of objects to compare
     * @param itemMapper mapping function that is called for each item in the stream and the return value is compared to
     *            the expected entries
     * @param expectedEntries entries we expect
     * @return A Matcher that checks if exactly the provided entries are in the stream. Not more and not less.
     */
    @SafeVarargs
    public static <ComponentT extends SeleniumComponent, AssertionT> Matcher<Stream<ComponentT>> streamContainingExactly(
        Function<ComponentT, AssertionT> itemMapper, AssertionT... expectedEntries)
    {
        return new StreamContainsItemsMatcher<>(itemMapper, expectedEntries, true, List::contains);
    }

    /**
     * @param <ComponentT> type of components in the list
     * @param <AssertionT> type of objects to compare
     * @param itemMapper mapping function that is called for each item in the list and the return value is compared to
     *            the expected entries
     * @param expectedEntries entries we expect
     * @return A Matcher that checks if exactly the provided entries are in the list. Not more and not less.
     */
    @SafeVarargs
    public static <ComponentT extends SeleniumComponent, AssertionT> Matcher<SeleniumComponentList<ComponentT>> containsExactly(
        Function<ComponentT, AssertionT> itemMapper, AssertionT... expectedEntries)
    {
        return new ComponentListContainsItemsMatcher<>(itemMapper, expectedEntries, true, List::contains);
    }

    /**
     * @param <ComponentT> type of components in the list
     * @param <AssertionT> type of objects to compare
     * @param itemMapper mapping function that is called for each item in the list and the return value is compared to
     *            the expected entries
     * @param contains a function that is called with the actual list of entries for each expected entry. It must return
     *            true when the expected entry is in the list. False otherwise.
     * @param expectedEntries entries we expect
     * @return A Matcher that checks if exactly the provided entries are in the list. Not more and not less.
     */
    @SafeVarargs
    public static <ComponentT extends SeleniumComponent, AssertionT> Matcher<SeleniumComponentList<ComponentT>> containsExactlyWithValidation(
        Function<ComponentT, AssertionT> itemMapper, BiFunction<List<AssertionT>, AssertionT, Boolean> contains,
        AssertionT... expectedEntries)
    {
        return new ComponentListContainsItemsMatcher<>(itemMapper, expectedEntries, true, contains);
    }

    /**
     * @param <ComponentT> type of component to check
     * @return matcher that checks if the component is visible
     */
    public static <ComponentT extends VisibleSeleniumComponent> Matcher<ComponentT> isVisible()
    {
        return new VisibilityMatcher<>(true);
    }

    /**
     * @param <ComponentT> type of component to check
     * @return matcher that checks if the component is not visible
     */
    public static <ComponentT extends VisibleSeleniumComponent> Matcher<ComponentT> isNotVisible()
    {
        return new VisibilityMatcher<>(false);
    }

    /**
     * @param <ComponentT> type of component to check
     * @return matcher that checks if the component is clickable
     */
    public static <ComponentT extends ClickableSeleniumComponent> Matcher<ComponentT> isClickable()
    {
        return new ClickabilityMatcher<>();
    }

    private static class ComponentListHasItemsMatcher<ComponentT extends SeleniumComponent>
        extends BaseMatcher<SeleniumComponentList<ComponentT>>
    {

        @Override
        public boolean matches(Object item)
        {
            if (item == null)
            {
                return false;
            }

            @SuppressWarnings("unchecked")
            SeleniumComponentList<ComponentT> factory = (SeleniumComponentList<ComponentT>) item;

            return factory.size() > 0;
        }

        @Override
        public void describeTo(Description description)
        {
            description.appendText(" should have items");
        }
    }

    private static class ComponentListContainsItemsMatcher<ComponentT extends SeleniumComponent, AssertionT>
        extends BaseMatcher<SeleniumComponentList<ComponentT>>
    {
        private final Function<ComponentT, AssertionT> mapping;
        private final AssertionT[] expectedValues;
        private final boolean exactMatch;
        private final BiFunction<List<AssertionT>, AssertionT, Boolean> contains;

        private List<AssertionT> actualValues;

        ComponentListContainsItemsMatcher(Function<ComponentT, AssertionT> mapping, AssertionT[] expectedValues,
            boolean exactMatch, BiFunction<List<AssertionT>, AssertionT, Boolean> contains)
        {
            super();

            this.mapping = mapping;
            this.expectedValues = expectedValues;
            this.exactMatch = exactMatch;
            this.contains = contains;
        }

        @Override
        public boolean matches(Object item)
        {
            if (item == null)
            {
                return false;
            }

            @SuppressWarnings("unchecked")
            SeleniumComponentList<ComponentT> list = (SeleniumComponentList<ComponentT>) item;

            actualValues = parseActualValues(list);

            if (exactMatch && actualValues.size() != expectedValues.length)
            {
                return false;
            }

            for (AssertionT expected : expectedValues)
            {
                if (!contains.apply(actualValues, expected))
                {
                    return false;
                }
            }

            return true;
        }

        @Override
        public void describeTo(Description description)
        {
            description.appendText("List containing ").appendValue(expectedValues);

            description.appendText("\n\n But mapped values where: ").appendValue(actualValues);
        }

        private List<AssertionT> parseActualValues(SeleniumComponentList<ComponentT> list)
        {
            return list.toList(mapping);
        }

    }

    private static class StreamContainsItemsMatcher<ComponentT extends SeleniumComponent, AssertionT>
        extends BaseMatcher<Stream<ComponentT>>
    {
        private final Function<ComponentT, AssertionT> mapping;
        private final AssertionT[] expectedValues;
        private final boolean exactMatch;
        private final BiFunction<List<AssertionT>, AssertionT, Boolean> contains;

        private List<AssertionT> actualValues;

        StreamContainsItemsMatcher(Function<ComponentT, AssertionT> mapping, AssertionT[] expectedValues,
            boolean exactMatch, BiFunction<List<AssertionT>, AssertionT, Boolean> contains)
        {
            super();

            this.mapping = mapping;
            this.expectedValues = expectedValues;
            this.exactMatch = exactMatch;
            this.contains = contains;
        }

        @Override
        public boolean matches(Object item)
        {
            if (item == null)
            {
                return false;
            }

            @SuppressWarnings("unchecked")
            Stream<ComponentT> stream = (Stream<ComponentT>) item;

            actualValues = parseActualValues(stream);

            if (exactMatch && actualValues.size() < expectedValues.length)
            {
                return false;
            }

            for (AssertionT expected : expectedValues)
            {
                if (!contains.apply(actualValues, expected))
                {
                    return false;
                }
            }

            return true;
        }

        @Override
        public void describeTo(Description description)
        {
            description.appendText("Stream containing ").appendValue(expectedValues);

            description.appendText("\n\n But mapped values where: ").appendValue(actualValues);
        }

        private List<AssertionT> parseActualValues(Stream<ComponentT> stream)
        {
            return stream //
                .map(mapping)
                .collect(Collectors.toList());
        }
    }

    private static class VisibilityMatcher<ComponentT extends VisibleSeleniumComponent> extends BaseMatcher<ComponentT>
    {
        private final boolean shouldBeVisible;

        VisibilityMatcher(boolean shouldBeVisible)
        {
            super();
            this.shouldBeVisible = shouldBeVisible;
        }

        @Override
        public boolean matches(Object item)
        {
            if (item == null)
            {
                return false;
            }

            VisibleSeleniumComponent component = (VisibleSeleniumComponent) item;

            boolean actual = component.isVisible();

            return actual == shouldBeVisible;
        }

        @Override
        public void describeTo(Description description)
        {
            if (shouldBeVisible)
            {
                description.appendText("A component that is visible");
            }
            else
            {
                description.appendText("A component that is invisible");
            }
        }
    }

    private static class ClickabilityMatcher<ComponentT extends ClickableSeleniumComponent>
        extends BaseMatcher<ComponentT>
    {
        @Override
        public boolean matches(Object item)
        {
            if (item == null)
            {
                return false;
            }

            ClickableSeleniumComponent component = (ClickableSeleniumComponent) item;

            boolean actual = component.isClickable();

            return actual;
        }

        @Override
        public void describeTo(Description description)
        {
            description.appendText("A component that is clickable");
        }
    }
}
