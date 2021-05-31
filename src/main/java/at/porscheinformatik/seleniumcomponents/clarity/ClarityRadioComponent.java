/**
 * 
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;
import at.porscheinformatik.seleniumcomponents.component.RadioComponent;

/**
 * @author Daniel Furtlehner
 */
public class ClarityRadioComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{
    private final RadioComponent radio = new RadioComponent(this);
    private final HtmlComponent label = new HtmlComponent(this, selectByTagName("label"));

    public ClarityRadioComponent(SeleniumComponent parent)
    {
        this(parent, selectByClassName("clr-radio-wrapper"));
    }

    public ClarityRadioComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getLabel()
    {
        return label.getText();
    }

    @Override
    public boolean isSelected()
    {
        return radio.isSelected();
    }

    @Override
    public void select()
    {
        if (!isSelected())
        {
            label.click();
        }
    }

    @Override
    public void unselect()
    {
        if (isSelected())
        {
            label.click();
        }
    }
}
