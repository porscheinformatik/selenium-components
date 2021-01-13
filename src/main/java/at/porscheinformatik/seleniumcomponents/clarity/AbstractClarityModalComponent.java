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

    protected final HtmlComponent modalDialog = new HtmlComponent(this, selectByClassName("modal-dialog"));

    protected final HtmlComponent modalHeader =
        new HtmlComponent(modalDialog, selectByClassName("modal-header", "modal-header--accessible"));
    protected final HtmlComponent modalBody = new HtmlComponent(modalDialog, selectByClassName("modal-body"));
    protected final HtmlComponent modalFooter = new HtmlComponent(modalDialog, selectByClassName("modal-footer"));

    private final HtmlComponent title = new HtmlComponent(modalHeader, selectByClassName("modal-title"));
    private final HtmlComponent closeIcon = new HtmlComponent(modalHeader, selectByClassName("close"));

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
        closeIcon.click();

        assertComponent(this, isNotVisible());
    }

    @Override
    public boolean isVisible()
    {
        return modalDialog.isVisible();
    }
}
