package at.porscheinformatik.seleniumcomponents;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Factory for {@link SeleniumComponent}s
 *
 * @author ham
 * @param <AnySeleniumComponent> the type of {@link SeleniumComponent}
 */
@FunctionalInterface
public interface SeleniumComponentFactory<AnySeleniumComponent extends SeleniumComponent>
{

    /**
     * Creates a {@link SeleniumComponentFactory}, that uses the specified type as component. The component must provide
     * the {@link AbstractSeleniumComponent#AbstractSeleniumComponent(SeleniumComponent, WebElementSelector)}
     * constructor.
     *
     * @param <AnySeleniumComponent> the type of the component
     * @param type the type of the component
     * @return a list factory
     */
    @SuppressWarnings("unchecked")
    static <AnySeleniumComponent extends SeleniumComponent> SeleniumComponentFactory<AnySeleniumComponent> of(
        Class<? extends SeleniumComponent> type)
    {
        Constructor<? extends SeleniumComponent> constructor;

        try
        {
            constructor = type.getConstructor(SeleniumComponent.class, WebElementSelector.class);
        }
        catch (NoSuchMethodException | SecurityException e1)
        {
            throw new SeleniumTestException(String.format("The $s has no constructor with types (%s, %s)", type,
                SeleniumUtils.toClassName(SeleniumComponent.class),
                SeleniumUtils.toClassName(WebElementSelector.class)));
        }

        return (parent, selector) -> {
            try
            {
                return (AnySeleniumComponent) constructor.newInstance(parent, selector);
            }
            catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e)
            {
                throw new SeleniumTestException("Failed to create clone", e);
            }
        };
    }

    /**
     * Create a new component.
     *
     * @param parent the parent
     * @param selector the selector
     * @return the component
     */
    AnySeleniumComponent create(SeleniumComponent parent, WebElementSelector selector);

}
