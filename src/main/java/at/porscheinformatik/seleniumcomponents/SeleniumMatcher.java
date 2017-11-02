package at.porscheinformatik.seleniumcomponents;

/**
 * A matcher for {@link SeleniumAsserts}.
 *
 * @author ham
 * @param <Any> the type of the item
 */
public interface SeleniumMatcher<Any>
{

    /**
     * Evaluates the matcher for argument <var>item</var>. This matcher has the same erasure type as the Hamcrest
     * matcher.
     *
     * @param item the object against which the matcher is evaluated.
     * @return <code>true</code> if <var>item</var> matches, otherwise <code>false</code>.
     */
    boolean matches(Any item);

}