package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

/**
 * A clr-form-control.
 *
 * @author ham
 */
public class ClarityFormControlContainer extends AbstractSeleniumComponent
{

    public final HtmlComponent labelComponent =
        new HtmlComponent(this, WebElementSelector.selectByClassName("clr-control-label"));

    public ClarityFormControlContainer(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByClassName("clr-form-control"));
    }

    public ClarityFormControlContainer(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getLabel()
    {
        return labelComponent.getText();
    }
}
