package at.porscheinformatik.seleniumcomponents;

import java.util.Objects;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The base class for components accessible by the Selenium tests. A {@link SeleniumComponent} usually describes a node
 * in the DOM.
 *
 * @author ham
 */
public interface SeleniumComponent extends WebElementContainer
{

    Logger LOG = LoggerFactory.getLogger(SeleniumComponent.class);

    /**
     * Returns the parent of this component.
     *
     * @return the parent, may be null if the component is root (like a page).
     */
    SeleniumComponent parent();

    /**
     * Returns the {@link SeleniumEnvironment} of this component, which is, by default, the environment of the parent.
     * If this component is root, it must implement this method and return a valid environment.
     *
     * @return the environment, never null
     */
    default SeleniumEnvironment environment()
    {
        SeleniumComponent parent = parent();

        Objects.requireNonNull(parent, () -> String.format(
            "The parent is null which means, that this component is root. Implement this method by supplying a valid environment"));

        return Objects.requireNonNull(parent.environment(),
            () -> String.format("The environment of the component defined by %s is null", parent.getClass()));
    }

    /**
     * Returns true if a {@link WebElement} described by this component is ready and available (it must not be visible,
     * though). This method has no timeout, it does not wait for the component to become existent.
     *
     * @return true if the component exists
     */
    boolean isReady();

    @Override
    String toString();
}
