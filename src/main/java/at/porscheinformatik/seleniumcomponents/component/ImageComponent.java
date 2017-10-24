/**
 *
 */
package at.porscheinformatik.seleniumcomponents.component;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;

/**
 * @author Daniel Furtlehner
 */
public class ImageComponent extends HtmlComponent
{

    public ImageComponent(SeleniumComponent parent)
    {
        super(parent, selectByTagName("img"));
    }

}
