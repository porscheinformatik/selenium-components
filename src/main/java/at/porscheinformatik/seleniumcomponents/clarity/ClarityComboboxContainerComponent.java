package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * @author scar
 */
public class ClarityComboboxContainerComponent extends ClarityFormControlContainer
{
    private final ClarityComboboxComponent combobox = new ClarityComboboxComponent(this);

    // ---

    public static ClarityComboboxContainerComponent within(SeleniumComponent parent)
    {
        return new ClarityComboboxContainerComponent(parent, selectByTagName("clr-combobox-container"));
    }

    public static ClarityComboboxContainerComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey)
    {
        return new ClarityComboboxContainerComponent(parent, selectBySeleniumKey(seleniumKey));
    }

    // ---

    public ClarityComboboxContainerComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    // ---

    public void clearBox()
    {
        combobox.clearAll();
    }

    public void selectItem(String label)
    {
        combobox.selectByLabel(label);
    }
}
