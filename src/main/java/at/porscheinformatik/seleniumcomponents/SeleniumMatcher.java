package at.porscheinformatik.seleniumcomponents;

/**
 * A matcher for {@link SeleniumAsserts}.
 *
 * @author ham
 * @param <T> the type of the item
 */
public interface SeleniumMatcher<T>
{

    /**
     * Evaluates the matcher for argument <var>item</var>. <br>
     * This method matches against Object, instead of the generic type T. This is because the caller of the Matcher does
     * not know at runtime what the type is (because of type erasure with Java generics). It is down to the
     * implementations to check the correct type.
     *
     * @param item the object against which the matcher is evaluated.
     * @return <code>true</code> if <var>item</var> matches, otherwise <code>false</code>.
     */
    boolean matches(T item);

}