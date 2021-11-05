package at.porscheinformatik.seleniumcomponents.clarity;

import java.util.function.Predicate;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A clr-radio-container.
 *
 * @author ham
 */
public class ClarityRadioContainerComponent extends ClarityFormControlContainer
{

    private final SeleniumComponentListFactory<ClarityRadioComponent> radioComponentFactory =
        new SeleniumComponentListFactory<>(this, WebElementSelector.selectByClassName("clr-radio-wrapper"),
            ClarityRadioComponent::new);

    public ClarityRadioContainerComponent(SeleniumComponent parent)
    {
        super(parent);
    }

    public ClarityRadioContainerComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public SeleniumComponentListFactory<ClarityRadioComponent> getRadioComponentFactory()
    {
        return radioComponentFactory;
    }

    public ClarityRadioComponent getRadioComponentAtIndex(int index)
    {
        return new ClarityRadioComponent(this, WebElementSelector.selectByIndex("clr-radio-wrapper", index));
    }

    public SeleniumComponentList<ClarityRadioComponent> getRadioComponents()
    {
        return radioComponentFactory.findAll();
    }

    public ClarityRadioComponent findRadioComponent(Predicate<ClarityRadioComponent> predicate)
    {
        return radioComponentFactory.find(predicate);
    }

    public ClarityRadioComponent getSelectedRadioComponent()
    {
        return findRadioComponent(ClarityRadioComponent::isSelected);
    }
}
