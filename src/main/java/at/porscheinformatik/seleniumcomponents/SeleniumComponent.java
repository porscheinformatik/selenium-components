package at.porscheinformatik.seleniumcomponents;

import java.util.Objects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

/**
 * The base class for components accessible by the Selenium tests. A {@link SeleniumComponent} usually describes a node
 * in the DOM.
 *
 * @author ham
 */
public interface SeleniumComponent extends WebElementContainer
{

    SeleniumLogger LOG = new SeleniumLogger(SeleniumComponent.class);

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

        Objects
            .requireNonNull(parent, () -> String
                .format(
                    "The parent is null which means, that this component is root. Implement this method by supplying a valid environment"));

        return Objects
            .requireNonNull(parent.environment(),
                () -> String.format("The environment of the component defined by %s is null", parent.getClass()));
    }

    /**
     * Returns true if a {@link WebElement} described by this component is ready and available (it must not be visible,
     * though). This method has no timeout, it does not wait for the component to become existent.
     *
     * @return true if the component exists
     */
    boolean isReady();

    /**
     * Waits until the component becomes ready.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilReady(double timeoutInSeconds)
    {
        SeleniumAsserts
            .assertThatSoon(timeoutInSeconds, "Component becomes ready: " + describe(), () -> this,
                SeleniumMatchers.isReady());
    }

    /**
     * Returns true if a {@link WebElement} described by this component is visible. By default it checks, if the element
     * is visible. This method has no timeout, it does not wait for the component to become existent. <br>
     * <br>
     * It it NO good idea, to check for invisibility. If the component is not visible, Selenium always waits for some
     * time. This causes tests to run slowly and timeouts to fail.
     *
     * @return true if the component is visible
     */
    default boolean isVisible()
    {
        try
        {
            return SeleniumUtils.retryOnStale(() -> element().isDisplayed());
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * Waits until the component becomes ready.
     *
     * @param timeoutInSeconds the timeout in seconds
     */
    default void waitUntilVisible(double timeoutInSeconds)
    {
        SeleniumAsserts
            .assertThatSoon(timeoutInSeconds, "Component becomes visible: " + describe(), () -> this,
                SeleniumMatchers.isVisible());
    }

    /**
     * This method is not intended to be used in regular test cases. Its main use is to highlight a element while
     * debugging a test.
     * 
     * <p>
     * Sometime there are multiple elements on the page that match a given selector. Then it is hard to find, why a test
     * breaks. It might use the wrong element. Using the highlight method should make it farily easy to see if the
     * correct element is selected.
     * </p>
     * 
     * <p>
     * This method will set a border and background for the element. In most of the cases this should be enough to see
     * if the correct element is selected. But sometimes elements are not directly visible on screen, because they have
     * no size. For this reason, a "hightlighted=true" attribute is added to the element. You can then execute the
     * following script in the DevTools of your brower to find all highlighted elements.
     * <code>document.querySelectorAll('[highlighted]')</code>
     * </p>
     */
    default void highlight()
    {
        JavascriptExecutor executor = (JavascriptExecutor) environment().getDriver();

        executor
            .executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;'); "
                + "arguments[0].setAttribute('highlighted', 'true')", element());
    }

    @Override
    String toString();
}
