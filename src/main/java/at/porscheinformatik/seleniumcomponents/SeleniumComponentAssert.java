package at.porscheinformatik.seleniumcomponents;

import static org.hamcrest.MatcherAssert.*;

/**
 * Asserts for components
 *
 * @author HAM
 */
public final class SeleniumComponentAssert
{

    private SeleniumComponentAssert()
    {
        super();
    }

    public static void assertExists(AbstractSeleniumComponent component)
    {
        assertThat("There exists " + component.describe(), component.exists());
    }

    public static void assertContainsDescendant(AbstractSeleniumComponent component, WebElementSelector selector)
    {
        assertThat("The component \"" + component.describe() + "\" has a descendant: " + selector,
            component.containsDescendant(selector));
    }
}
