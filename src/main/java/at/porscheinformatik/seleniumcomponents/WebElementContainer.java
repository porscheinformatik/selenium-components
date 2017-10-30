package at.porscheinformatik.seleniumcomponents;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * A container holding a {@link WebElement}.
 *
 * @author ham
 */
public interface WebElementContainer extends SearchContext
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

    @Override
    default WebElement findElement(By by)
    {
        return element().findElement(by);
    }

    @Override
    default List<WebElement> findElements(By by)
    {
        return element().findElements(by);
    }

}
