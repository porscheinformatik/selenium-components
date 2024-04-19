package at.porscheinformatik.seleniumcomponents.component;

import org.hamcrest.Matchers;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.ActiveSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumAsserts;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.Utils;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

/**
 * An (text) input field.
 *
 * @author cet
 */
public class InputComponent extends AbstractSeleniumComponent implements ActiveSeleniumComponent
{

    public static InputComponent byName(SeleniumComponent parent, String name)
    {
        return new InputComponent(parent, WebElementSelector.selectByName(name));
    }

    public static InputComponent byTestId(SeleniumComponent parent, String testId)
    {
        return new InputComponent(parent, WebElementSelector.selectByTestId(testId));
    }

    /**
     * @deprecated Use {@link #byTestId(SeleniumComponent, String)} instead
     */
    @Deprecated(forRemoval = true)
    public static InputComponent bySeleniumKey(SeleniumComponent parent, String seleniumKey)
    {
        return new InputComponent(parent, WebElementSelector.selectBySeleniumKey(seleniumKey));
    }

    public InputComponent(SeleniumComponent parent)
    {
        this(parent, WebElementSelector.selectByTagName("input"));
    }

    public InputComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    /**
     * Clears the input field an types all specified values. Checks, that the input field finally contains the value. If
     * the input field has a special behavior (e.g. auto typing or character restrictions) use the
     * {@link #type(CharSequence...)} method instead.
     *
     * @param values one or more values to enter
     */
    public void enter(CharSequence... values)
    {
        clear();
        sendKeys(values);

        String jointValues = String.join("", values);

        SeleniumAsserts.assertThatSoon(String.format("Enter \"%s\"", jointValues), () -> Utils.simplify(getValue()),
            Matchers.is(Utils.simplify(jointValues)));
    }

    /**
     * Types the value into the input field. Does not verify, that the field contains the value after typing.
     *
     * @param values one or more values to enter
     */
    public void type(CharSequence... values)
    {
        sendKeys(values);
    }

    public String getValue()
    {
        return getAttribute("value");
    }

    public String getPlaceholder()
    {
        return getAttribute("placeholder");
    }

}
