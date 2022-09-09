package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityComboboxOptionsComponent.AbstractClarityComboboxOptionComponent;

/**
 * @author TechScar
 */
public class ClaritySingleSelectComboboxComponent<OPTION_TYPE extends AbstractClarityComboboxOptionComponent>
    extends AbstractClarityComboboxComponent<OPTION_TYPE>
{
    public ClaritySingleSelectComboboxComponent(SeleniumComponent parent,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory)
    {
        super(parent, selectByTagName("clr-combobox"), optionFactory);
    }

    public ClaritySingleSelectComboboxComponent(SeleniumComponent parent, WebElementSelector selector,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory)
    {
        super(parent, selector, optionFactory);
    }

    // ---

    @Override
    public void clear()
    {
        input.clear();
    }

    public String getSelectedLabel()
    {
        return input.getValue();
    }
}
