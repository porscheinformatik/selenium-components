package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

public class DefaultClarityComboboxLabelPillComponent extends AbstractClarityComboboxLabelPillComponent
{
    public DefaultClarityComboboxLabelPillComponent(SeleniumComponent parent)
    {
        super(parent);
    }

    public DefaultClarityComboboxLabelPillComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getLabel()
    {
        return content.getText();
    }
}
