package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import java.util.List;
import java.util.stream.Collectors;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

/**
 * A clr-form-control.
 *
 * @author ham
 */
public class ClarityFormControlContainer extends AbstractSeleniumComponent
{
    public static ClarityFormControlContainer within(SeleniumComponent parent)
    {
        return new ClarityFormControlContainer(parent, selectByClassName("clr-form-control"));
    }

    public static ClarityFormControlContainer byTestId(SeleniumComponent parent, String testId)
    {
        return new ClarityFormControlContainer(parent, selectByTestId("clr-form-control", testId));
    }

    public static ClarityFormControlContainer byText(SeleniumComponent parent, String partialText)
    {
        return new ClarityFormControlContainer(parent, selectByText("clr-form-control", partialText));
    }

    public final HtmlComponent labelComponent = new HtmlComponent(this, selectByClassName("clr-control-label"));

    private final SeleniumComponentListFactory<HtmlComponent> validationErrors =
        new SeleniumComponentListFactory<>(this, selectByTagName("clr-control-error"), HtmlComponent::new);

    public ClarityFormControlContainer(SeleniumComponent parent)
    {
        this(parent, selectByClassName("clr-form-control"));
    }

    public ClarityFormControlContainer(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getLabel()
    {
        return labelComponent.getText();
    }

    public List<String> getValidationErrors()
    {
        return validationErrors //
            .findAll().map(HtmlComponent::getText).collect(Collectors.toList());
    }
}
