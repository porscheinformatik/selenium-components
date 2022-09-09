package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.Objects;
import java.util.function.Predicate;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A clr-radio-container.
 *
 * @author ham, TechScar
 */
public class ClarityRadioContainerComponent extends ClarityFormControlContainer
{
    private final SeleniumComponentListFactory<ClarityRadioComponent> radios =
        new SeleniumComponentListFactory<>(this, selectByTagName("clr-radio-wrapper"), ClarityRadioComponent::new);

    // ---

    public ClarityRadioContainerComponent(SeleniumComponent parent)
    {
        super(parent, selectByTagName("clr-radio-container"));
    }

    public ClarityRadioContainerComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    // ---

    public SeleniumComponentListFactory<ClarityRadioComponent> getRadioComponentFactory()
    {
        return radios;
    }

    public ClarityRadioComponent getRadioComponentAtIndex(int index)
    {
        return new ClarityRadioComponent(this, selectByIndex("clr-radio-wrapper", index));
    }

    public SeleniumComponentList<ClarityRadioComponent> getRadioComponents()
    {
        return radios.findAll();
    }

    public void selectByValue(String value)
    {
        findRadioComponent(radio -> Objects.equals(radio.getValue(), value)).select();
    }

    public ClarityRadioComponent findRadioComponent(Predicate<ClarityRadioComponent> predicate)
    {
        return radios.find(predicate);
    }

    public ClarityRadioComponent getSelectedRadioComponent()
    {
        return findRadioComponent(ClarityRadioComponent::isSelected);
    }
}
