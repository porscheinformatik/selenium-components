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
import org.hamcrest.Matchers;

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
     * @return matcher that checks if the component is ready
     */
    public static <ComponentT extends SeleniumComponent> Matcher<ComponentT> isReady()
    {
        return new GenericSeleniumComponentMatcher<>("A component, that is ready", SeleniumComponent::isReady,
            Matchers.equalTo(true));
    }

    /**
     * @param <ComponentT> type of component to check
     * @return matcher that checks if the component is visible
     */
    public static <ComponentT extends SeleniumComponent> Matcher<ComponentT> isVisible()
    {
        return new GenericSeleniumComponentMatcher<>("A component, that is visible", SeleniumComponent::isVisible,
            Matchers.equalTo(true));
    }

    /**
     * <b>It's no good practice to use this method</b>, but sometimes, it is necessary. It verifies, that the component
     * is not visible. Since it depends on the {@link SeleniumComponent#isVisible()} method, it will always wait for the
     * default timeout - the component may appear during that time. In the end, it will return false, if the component
     * did not appear. This means, that calling this method always takes time and performs multiple roundtrips from the
     * test runner to the browser, hence, do not use it unless it is really necessary.<br>
     * <br>
     * The method compensates the fact, that when using debug mode, this call would wait forever.
     *
     * @param <ComponentT> type of component to check
     * @return matcher that checks if the component is not visible
     */
    public static <ComponentT extends SeleniumComponent> Matcher<ComponentT> isNotVisible()
    {
        return new GenericSeleniumComponentMatcher<>("A component, that is not visible",
            component -> SeleniumGlobals.ignoreDebug(component::isVisible), Matchers.equalTo(false));
    }

    /**
     * @param <ComponentT> type of component to check
     * @return matcher that checks if the component is clickable
     */
    public static <ComponentT extends ActiveSeleniumComponent> Matcher<ComponentT> isClickable()
    {
        return new GenericSeleniumComponentMatcher<>("A component, that is clickable",
            ActiveSeleniumComponent::isClickable, Matchers.equalTo(true));
    }

    /**
     * @param <ComponentT> type of component to check
     * @return matcher that checks if the component is selected
     */
    public static <ComponentT extends ActiveSeleniumComponent> Matcher<ComponentT> isSelected()
    {
        return new GenericSeleniumComponentMatcher<>("A component, that is selected",
            ActiveSeleniumComponent::isSelected, Matchers.equalTo(true));
    }

    /**
     * @param <ComponentT> type of component to check
     * @return matcher that checks if the component is editable
     */
    public static <ComponentT extends ActiveSeleniumComponent> Matcher<ComponentT> isEditable()
    {
        return new GenericSeleniumComponentMatcher<>("A component, that is selected",
            ActiveSeleniumComponent::isEditable, Matchers.equalTo(true));
    }

    /**
     * @param <ComponentT> type of component to check
     * @return matcher that checks if the component is enabled
     */
    public static <ComponentT extends ActiveSeleniumComponent> Matcher<ComponentT> isEnabled()
    {
        return new GenericSeleniumComponentMatcher<>("A component, that is enabled", ActiveSeleniumComponent::isEnabled,
            Matchers.equalTo(true));
    }

    /**
     * @param <ComponentT> type of component to check
     * @return matcher that checks if the component is disabled
     */
    public static <ComponentT extends ActiveSeleniumComponent> Matcher<ComponentT> isDisabled()
    {
        return new GenericSeleniumComponentMatcher<>("A component, that is disabled",
            ActiveSeleniumComponent::isDisabled, Matchers.equalTo(true));
    }

    /**
     * @param <ComponentT> type of component to check
     * @return matcher that checks if the component has the specified tagName
     */
    public static <ComponentT extends SeleniumComponent> Matcher<ComponentT> hasTagName(String tagName)
    {
        return new GenericSeleniumComponentMatcher<>("A component with \"" + tagName + "\" as tagName",
            SeleniumUtils::getTagName, Matchers.equalTo(tagName));
    }

    /**
     * @param <ComponentT> type of component to check
     * @return matcher that checks if the component contains the specified text (case insensitive)
     */
    public static <ComponentT extends SeleniumComponent> Matcher<ComponentT> containsText(String text)
    {
        return new GenericSeleniumComponentMatcher<>("A component that contains \"" + text + "\" as text",
            SeleniumUtils::getText, Matchers.containsStringIgnoringCase(text));
    }

    //    /**
    //     * @param <ComponentT> type of component to check
    //     * @return matcher that checks if the component has the specified attribute
    //     */
    //    public static <ComponentT extends SeleniumComponent> Matcher<ComponentT> hasAttribute(String attributeName, String value)
    //    {
    //        return new GenericSeleniumComponentMatcher<>("A component with \"" + tagName + "\" as tagName",
    //            SeleniumUtils::getTagName, Matchers.equalTo(tagName));
    //    }

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

    private static class GenericSeleniumComponentMatcher<T extends SeleniumComponent, V> extends BaseMatcher<T>
    {
        private final String expectation;
        private final Function<T, V> valueAccessor;
        private final Matcher<V> valueMatchcher;

        private V value;
        private boolean valueAvailable;
        private Throwable exception;

        public GenericSeleniumComponentMatcher(String expectation, Function<T, V> valueAccessor,
            Matcher<V> valueMatchcher)
        {
            super();

            this.expectation = expectation;
            this.valueAccessor = valueAccessor;
            this.valueMatchcher = valueMatchcher;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean matches(Object item)
        {
            value = null;
            valueAvailable = false;
            exception = null;

            if (item == null)
            {
                return false;
            }

            if (!(item instanceof SeleniumComponent))
            {
                return false;
            }

            try
            {
                value = valueAccessor.apply((T) item);
            }
            catch (Throwable e)
            {
                exception = e;

                return false;
            }

            valueAvailable = true;

            return valueMatchcher.matches(value);
        }

        @Override
        public void describeTo(Description description)
        {
            description.appendText(expectation);

            if (valueAvailable)
            {
                description.appendText(", but the accessor returned : ").appendValue(value);
            }

            if (exception != null)
            {
                description.appendText(", \nbut the accessor caused a ").appendText(Utils.getStackTrace(exception));
            }
        }
    }
}