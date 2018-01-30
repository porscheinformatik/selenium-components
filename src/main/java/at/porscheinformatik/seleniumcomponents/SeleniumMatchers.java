/**
 * 
 */
package at.porscheinformatik.seleniumcomponents;

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
        return new ComponentListHasItemsMatcher<ComponentT>();
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
            SeleniumComponentList<ComponentT> list = (SeleniumComponentList<ComponentT>) item;

            return list.size() > 0;
        }

        @Override
        public void describeTo(Description description)
        {
            description.appendText(" should have items");
        }

    }
}
