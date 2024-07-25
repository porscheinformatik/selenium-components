/**
 *
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumUtils;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;
import at.porscheinformatik.seleniumcomponents.component.RadioComponent;

/**
 * @author Daniel Furtlehner
 */
public class ClarityRadioComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{
    public static ClarityRadioComponent within(SeleniumComponent parent)
    {
        return new ClarityRadioComponent(parent, selectByTagName("clr-radio-wrapper"));
    }

    public static ClarityRadioComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new ClarityRadioComponent(parent, selectByTestId("clr-radio-wrapper", testId));
    }

    public static ClarityRadioComponent byLabel(SeleniumComponent parent, String partialLabel)
    {
        return new ClarityRadioComponent(parent, selectByTagNameContainingLabel("clr-radio-wrapper", partialLabel));
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String)} instead
     */
    @Deprecated(forRemoval = true)
    public static ClarityRadioComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey)
    {
        return new ClarityRadioComponent(parent, selectBySeleniumKey("clr-radio-wrapper", seleniumKey));
    }

    public static ClarityRadioComponent byTestIdOfInput(SeleniumComponent parent, String testId)
    {
        String xpath = String.format(".//clr-radio-wrapper[.//input[@data-testid='%s']]", testId);

        return new ClarityRadioComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    /**
     * @deprecated Use {@link #byTestIdOfInput(SeleniumComponent, String)} instead
     */
    @Deprecated(forRemoval = true)
    public static ClarityRadioComponent bySeleniumKeyOfInput(SeleniumComponent parent, String seleniumKey)
    {
        String xpath = String.format(".//clr-radio-wrapper[.//input[@selenium-key='%s']]", seleniumKey);

        return new ClarityRadioComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public static ClarityRadioComponent byFormControlName(SeleniumComponent parent, String formControlName)
    {
        String xpath = String.format(".//clr-radio-wrapper[.//input[@formcontrolname='%s']]", formControlName);

        return new ClarityRadioComponent(parent, WebElementSelector.selectByXPath(xpath));
    }

    public final RadioComponent radio = new RadioComponent(this);
    public final HtmlComponent label = new HtmlComponent(this, selectByTagName("label"));

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

    public String getValue()
    {
        return SeleniumUtils.getAttribute(radio, "value");
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

    @Override
    public boolean isEnabled()
    {
        return radio.isEnabled();
    }

    @Override
    public boolean isEditable()
    {
        return radio.isEditable();
    }

    @Override
    public boolean isDisabled()
    {
        return radio.isDisabled();
    }
}
