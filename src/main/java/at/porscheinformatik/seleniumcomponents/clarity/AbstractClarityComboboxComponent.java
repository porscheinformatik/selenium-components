package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.SeleniumAsserts.*;
import static org.hamcrest.Matchers.*;

import org.openqa.selenium.Keys;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumUtils;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityComboboxOptionsComponent.AbstractClarityComboboxOptionComponent;
import at.porscheinformatik.seleniumcomponents.component.InputComponent;

/**
 * @author TechScar
 */
public abstract class AbstractClarityComboboxComponent<OPTION_TYPE extends AbstractClarityComboboxOptionComponent>
    extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{
    protected final InputComponent input = new InputComponent(this);

    protected final ClarityComboboxOptionsComponent<OPTION_TYPE> options;

    protected AbstractClarityComboboxComponent(SeleniumComponent parent, WebElementSelector selector,
        SeleniumComponentFactory<OPTION_TYPE> optionFactory)
    {
        super(parent, selector);

        options = new ClarityComboboxOptionsComponent<>(SeleniumUtils.root(this), optionFactory);
    }

    @Override
    abstract public void clear();

    public void selectByLabel(String partialText)
    {
        input.clear();
        input.enter(partialText);

        assertThatSoon(options::isVisible, is(true));

        options.selectOptionByLabel(partialText);

        // blur in order to make sure, that the options are closed
        input.type(Keys.TAB);
    }

    public abstract void assertSelected(String partialText);

    @Override
    public boolean isEnabled()
    {
        return input.isEnabled();
    }

    @Override
    public boolean isEditable()
    {
        return input.isEditable();
    }

    @Override
    public boolean isDisabled()
    {
        return input.isDisabled();
    }
}
