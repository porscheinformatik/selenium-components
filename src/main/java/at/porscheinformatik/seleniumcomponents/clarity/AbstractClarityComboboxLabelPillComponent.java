package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.ButtonComponent;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

public abstract class AbstractClarityComboboxLabelPillComponent extends AbstractSeleniumComponent
{
    protected final HtmlComponent content = new HtmlComponent(this, selectByClassName("clr-combobox-pill-content"));

    private final ButtonComponent deleteButton =
        new ButtonComponent(this, selectByClassName("clr-combobox-remove-btn"));

    protected AbstractClarityComboboxLabelPillComponent(SeleniumComponent parent)
    {
        this(parent, selectByClassName("label-combobox-pill"));
    }

    protected AbstractClarityComboboxLabelPillComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    protected void delete()
    {
        deleteButton.click();
    }
}
