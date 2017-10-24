package at.porscheinformatik.seleniumcomponents;

import java.util.function.BiFunction;

/**
 * Factory for {@link SeleniumComponent}s
 *
 * @author ham
 * @param <AnySeleniumComponent> the type of {@link SeleniumComponent}
 */
@FunctionalInterface
public interface SeleniumComponentFactory<AnySeleniumComponent extends SeleniumComponent>
    extends BiFunction<SeleniumComponent, WebElementSelector, AnySeleniumComponent>
{

    // intentionally left blank

}
