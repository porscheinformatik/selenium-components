/**
 *
 */
package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author Daniel Furtlehner
 */
public class ImageComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{
    public static ImageComponent within(SeleniumComponent parent)
    {
        return new ImageComponent(parent, selectByTagName("img"));
    }

    public static ImageComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new ImageComponent(parent, WebElementSelector.selectByTestId("img", testId));
    }

    public static ImageComponent byAlt(SeleniumComponent parent, String partialText)
    {
        String xpath = String.format(".//img[contains(@alt, '%s')]", partialText);

        return new ImageComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ImageComponent bySrc(SeleniumComponent parent, String partialSrc)
    {
        String xpath = String.format(".//img[contains(@src, '%s')]", partialSrc);

        return new ImageComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public ImageComponent(SeleniumComponent parent)
    {
        this(parent, selectByTagName("img"));
    }

    /**
     * @deprecated Use {@link #ImageComponent(SeleniumComponent, WebElementSelector)} instead
     */
    @Deprecated(forRemoval = true)
    public ImageComponent(SeleniumComponent parent, String seleniumKey)
    {
        this(parent, selectByTestIdOrSeleniumKey("img", seleniumKey));
    }

    public ImageComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getAlt()
    {
        return getAttribute("alt");
    }

    public String getSrc()
    {
        return getAttribute("src");
    }
}
