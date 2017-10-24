package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * An input field.
 *
 * @author cet
 */
public class TextAreaComponent extends InputComponent
{

    public TextAreaComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("textarea"));
    }

    public TextAreaComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

}
