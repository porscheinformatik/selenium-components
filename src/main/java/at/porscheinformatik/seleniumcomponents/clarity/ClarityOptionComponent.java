package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import javax.annotation.Nullable;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

public class ClarityOptionComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{
    private final ClarityIconComponent icon = new ClarityIconComponent(this);
    private final HtmlComponent optionText = new HtmlComponent(this, selectByClassName("option-text"));
    private final HtmlComponent optionSubtext = new HtmlComponent(this, selectByClassName("option-subtext"));

    // ---

    public ClarityOptionComponent(SeleniumComponent parent)
    {
        this(parent, selectByTagName("clr-option"));
    }

    public ClarityOptionComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    // ---

    @Nullable
    public String getIcon()
    {
        return icon.getShape();
    }

    public String getLabel()
    {
        return optionText.getText();
    }

    @Nullable
    public String getSubtext()
    {
        return optionSubtext.getText();
    }
}
