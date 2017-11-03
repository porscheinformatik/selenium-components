package at.porscheinformatik.seleniumcomponents;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * A container holding a {@link WebElement}.
 *
 * @author ham
 */
public interface WebElementContainer
{

    /**
     * Selects and returns the {@link WebElement} of this container from the DOM. If you use this method outside of your
     * container, it is generally considered a bad idea, and you should switch to use a {@link SeleniumComponent}
     * instead. Usually this call waits {@link SeleniumGlobals#getShortTimeoutInSeconds()} for the element to become
     * available but this may differ per implementation.
     *
     * @return the element, or null if this container does not represent an element
     * @throws NoSuchElementException if the element was not found
     */
    WebElement element() throws NoSuchElementException;

    /**
     * Returns the {@link SearchContext} for this component. By default, this is the {@link #element()} itself.
     *
     * @return the {@link SearchContext}, never null
     */
    default SearchContext searchContext()
    {
        return element();
    }

    /**
     * Describes the element selector of this component
     *
     * @return the description of the selector
     */
    String describe();

}
