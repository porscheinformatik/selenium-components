package at.porscheinformatik.seleniumcomponents.angular;

import java.util.Objects;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.OptionComponent;
import at.porscheinformatik.seleniumcomponents.component.SelectComponent;

/**
 * This component differs to the {@link SelectComponent} by the way, it interprets the value. Angular tends to add ids
 * to the value, like "1: MY_OPTION", this class respects this. <br>
 * <b>Warning: Do not use the ng-value, it is missing when compiled in production mode!</b>
 *
 * @author HAM
 */
public class AngularSelectComponent extends SelectComponent
{

    public AngularSelectComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public AngularSelectComponent(SeleniumComponent parent)
    {
        super(parent);
    }

    @Override
    public void selectByValue(String value)
    {
        LOG.interaction("Selecting \"%s\" of %s by value", value, describe());

        click();

        OptionComponent selectedOption = optionsFactory.find(option -> {
            String optionValue = option.getValue();

            if (Objects.equals(value, optionValue))
            {
                return true;
            }

            if (optionValue == null)
            {
                return false;
            }

            if (optionValue.contains(":"))
            {
                String valuePart = optionValue.split(":")[1].trim();

                return Objects.equals(valuePart, value);
            }

            return false;
        });

        if (selectedOption == null)
        {
            throw new AssertionError(String.format("Options with value \"%s\" not found", value));
        }

        selectedOption.click();
    }

}
