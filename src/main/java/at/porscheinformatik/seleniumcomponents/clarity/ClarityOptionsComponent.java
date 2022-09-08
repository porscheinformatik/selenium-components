package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;

public class ClarityOptionsComponent extends AbstractSeleniumComponent
{
    private final SeleniumComponentListFactory<ClarityOptionComponent> options =
        new SeleniumComponentListFactory<>(this, selectByTagName("clr-option"), ClarityOptionComponent::new);

    // ---

    public ClarityOptionsComponent(SeleniumComponent parent)
    {
        // clr-options is not child of the clr-combobox in the DOM, but appended to the end
        // there should only be one active dropdown at a time, so this should always work (considering a timeout)
        super(parent, selectByXPath("//clr-options"));
    }

    // ---

    protected ClarityOptionComponent findOption(String label)
    {
        return options.find(option -> option.getLabel().contains(label));
    }
}
