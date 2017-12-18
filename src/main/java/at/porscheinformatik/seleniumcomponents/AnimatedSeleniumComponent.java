package at.porscheinformatik.seleniumcomponents;

import java.util.Objects;

import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

/**
 * A {@link SeleniumComponent} that is animated.
 *
 * @author ham
 */
public interface AnimatedSeleniumComponent extends VisibleSeleniumComponent
{

    default void waitUntilAnimationFinished()
    {
        SeleniumUtils.keepTrying(SeleniumGlobals.getLongTimeoutInSeconds(), () -> {
            WebElement element = element();

            if (element.isDisplayed())
            {
                return false;
            }

            Rectangle bounds = element.getRect();

            SeleniumUtils.waitForSeconds(0.1);

            return Objects.equals(element.getRect(), bounds) ? true : null;
        });
    }

}
