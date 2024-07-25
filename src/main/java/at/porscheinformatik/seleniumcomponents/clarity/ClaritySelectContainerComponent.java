package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.SelectComponent;

public class ClaritySelectContainerComponent extends ClarityFormControlContainer
{
    public static ClaritySelectContainerComponent within(SeleniumComponent parent)
    {
        return new ClaritySelectContainerComponent(parent, selectByTagName("clr-select-container"));
    }

    public static ClaritySelectContainerComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new ClaritySelectContainerComponent(parent, selectByTestId("clr-select-container", testId));
    }

    public static ClaritySelectContainerComponent byText(SeleniumComponent parent, String partialText)
    {
        return new ClaritySelectContainerComponent(parent, selectByText("clr-select-container", partialText));
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String)} instead
     */
    @Deprecated(forRemoval = true)
    public static ClaritySelectContainerComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey)
    {
        return new ClaritySelectContainerComponent(parent, selectBySeleniumKey("clr-select-container", seleniumKey));
    }

    public final SelectComponent select = new SelectComponent(this);

    public ClaritySelectContainerComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getSelectedLabel()
    {
        return select.getSelectedOption().getLabel();
    }

    public String getSelectedValue()
    {
        return select.getSelectedOption().getValue();
    }

    public void selectByValue(String value)
    {
        select.selectByValue(value);
    }

    public void selectByValueContains(String value)
    {
        select.selectByValueContains(value);
    }

    public void selectByLabel(String label)
    {
        select.selectByLabel(label);
    }
}
