package at.porscheinformatik.seleniumcomponents.angular;

import java.util.function.Predicate;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
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
    public String getValue()
    {
        return sanitizeValue(super.getValue());
    }

    @Override
    public void selectByValue(Predicate<String> valuePredicate)
    {
        select(option -> {
            String value = option.getValue();

            return valuePredicate.test(value) || valuePredicate.test(sanitizeValue(value));
        });
    }

    public static String sanitizeValue(String value)
    {
        if (value == null)
        {
            return value;
        }

        int index = value.indexOf(":");

        return index >= 0 ? value.substring(index + 1).trim() : value;
    }
}
