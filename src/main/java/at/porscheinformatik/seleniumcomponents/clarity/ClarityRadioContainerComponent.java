package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.SeleniumAsserts.*;

import java.util.Objects;
import java.util.function.Predicate;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumMatchers;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * A clr-radio-container.
 *
 * @author ham, TechScar
 */
public class ClarityRadioContainerComponent extends ClarityFormControlContainer
{
    public static ClarityRadioContainerComponent within(SeleniumComponent parent)
    {
        return new ClarityRadioContainerComponent(parent, WebElementSelector.selectByTagName("clr-radio-container"));
    }

    public static ClarityRadioContainerComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new ClarityRadioContainerComponent(parent,
            WebElementSelector.selectByTestId("clr-radio-container", testId));
    }

    public static ClarityRadioContainerComponent byLabel(SeleniumComponent parent, String partialLabel)
    {
        return new ClarityRadioContainerComponent(parent,
            WebElementSelector.selectByTagNameContainingLabel("clr-radio-container", partialLabel));
    }

    private final SeleniumComponentListFactory<ClarityRadioComponent> radios =
        new SeleniumComponentListFactory<>(this, WebElementSelector.selectByTagName("clr-radio-wrapper"),
            ClarityRadioComponent::new);

    public ClarityRadioContainerComponent(SeleniumComponent parent)
    {
        super(parent, WebElementSelector.selectByTagName("clr-radio-container"));
    }

    public ClarityRadioContainerComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public SeleniumComponentListFactory<ClarityRadioComponent> getRadioComponentFactory()
    {
        return radios;
    }

    public ClarityRadioComponent getRadioComponentAtIndex(int index)
    {
        return new ClarityRadioComponent(this, WebElementSelector.selectByIndex("clr-radio-wrapper", index));
    }

    public ClarityRadioComponent getRadioComponentByTestId(String testId)
    {
        return ClarityRadioComponent.byTestId(this, testId);
    }

    public ClarityRadioComponent getRadioComponentByLabel(String label)
    {
        return ClarityRadioComponent.byLabel(this, label);
    }

    public SeleniumComponentList<ClarityRadioComponent> getRadioComponents()
    {
        return radios.findAll();
    }

    public void selectByIndex(int index)
    {
        ClarityRadioComponent component = getRadioComponentAtIndex(index);

        assertThatSoon(() -> {
            component.select();

            return component;
        }, SeleniumMatchers.isSelected());
    }

    public void selectByTestId(String testId)
    {
        ClarityRadioComponent component = getRadioComponentByTestId(testId);

        assertThatSoon(() -> {
            component.select();

            return component;
        }, SeleniumMatchers.isSelected());
    }

    public void selectByLabel(String label)
    {
        ClarityRadioComponent component = getRadioComponentByLabel(label);

        assertThatSoon(() -> {
            component.select();

            return component;
        }, SeleniumMatchers.isSelected());
    }

    public void selectByValue(String value)
    {
        ClarityRadioComponent component = findRadioComponent(radio -> Objects.equals(radio.getValue(), value));

        assertThatSoon(() -> {
            component.select();

            return component;
        }, SeleniumMatchers.isSelected());
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
