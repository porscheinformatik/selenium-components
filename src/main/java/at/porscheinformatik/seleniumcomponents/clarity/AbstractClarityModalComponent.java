/**
 *
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.SeleniumAsserts.*;
import static at.porscheinformatik.seleniumcomponents.SeleniumMatchers.*;
import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.AnimatedSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

/**
 * @author Daniel Furtlehner
 */
public abstract class AbstractClarityModalComponent extends AbstractSeleniumComponent
    implements AnimatedSeleniumComponent
{
    protected final HtmlComponent modalHeader =
        new HtmlComponent(this, selectByClassName("modal-header", "modal-header--accessible"));
    protected final HtmlComponent modalBody = new HtmlComponent(this, selectByClassName("modal-body"));

    private final HtmlComponent closeButton = new HtmlComponent(modalHeader, selectByClassName("close"));
    private final HtmlComponent title = new HtmlComponent(modalHeader, selectByClassName("modal-title"));

    public AbstractClarityModalComponent(SeleniumComponent parent)
    {
        this(parent, selectByTagName("clr-modal"));
    }

    public AbstractClarityModalComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
    }

    public String getTitle()
    {
        return title.getText();
    }

    public void close()
    {
        closeButton.click();

        assertComponent(this, isNotVisible());
    }

    @Override
    public boolean isVisible()
    {
        // Wait until the title is shown. The modal itself is visible before.
        return title.isVisible();
    }
}
