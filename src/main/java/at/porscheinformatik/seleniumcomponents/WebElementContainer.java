package at.porscheinformatik.seleniumcomponents;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * A container holding a {@link WebElement}.
 *
 * @author ham
 */
public interface WebElementContainer extends SearchContext
{

    double DEFAULT_TIMEOUT_IN_SECONDS = 2;

    /**
     * Selects and returns the element of this container from the DOM. Waits {@value #DEFAULT_TIMEOUT_IN_SECONDS} for
     * the element. If you use this method outside of your container, it is generally considered a bad idea, and you
     * should switch to use a {@link SeleniumComponent} instead.
     *
     * @return the element, or null if this container does not represent an element
     * @throws NoSuchElementException if the element was not found
     */
    default WebElement element() throws NoSuchElementException
    {
        return element(DEFAULT_TIMEOUT_IN_SECONDS);
    }

    /**
     * Selects and returns the {@link WebElement} of this container from the DOM. If you use this method outside of your
     * container, it is generally considered a bad idea, and you should switch to use a {@link SeleniumComponent}
     * instead.
     *
     * @param timeoutInSeconds a timeout, in seconds
     * @return the element, or null if this container does not represent an element
     * @throws NoSuchElementException if the element was not found
     */
    WebElement element(double timeoutInSeconds) throws NoSuchElementException;

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
