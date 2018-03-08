/**
 * 
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.AnimatedSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.ButtonComponent;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;
import at.porscheinformatik.seleniumcomponents.component.OrderedListComponent;

/**
 * Implementation of the clarity wizard component. This class is abstract because you create a subclass of it and define
 * the concrete implementations of the pages in your subclass.
 * 
 * @author Daniel Furtlehner
 */
public abstract class ClarityWizardComponent extends AbstractSeleniumComponent implements AnimatedSeleniumComponent
{
    private final HtmlComponent wizardTitle = new HtmlComponent(this, selectByTagName("clr-wizard-title"));
    private final HtmlComponent pageTitle = new HtmlComponent(this, selectByClassName("modal-title-text"));
    private final OrderedListComponent<ClarityWizardStepnavEntry> stepNav =
        new OrderedListComponent<>(this, selectByClassName("clr-wizard-stepnav-list"), ClarityWizardStepnavEntry::new);
    private final HtmlComponent wizardContent = new HtmlComponent(this, selectByClassName("clr-wizard-content"));
    private final HtmlComponent buttonWrapper =
        new HtmlComponent(this, selectByClassName("clr-wizard-footer-buttons-wrapper"));
    private final ButtonComponent nextButton = new ButtonComponent(buttonWrapper, selectByClassName("btn-primary"));
    private final ButtonComponent prevButton = new ButtonComponent(buttonWrapper, selectByClassName("btn-outline"));
    private final ButtonComponent finishButton = new ButtonComponent(buttonWrapper, selectByClassName("btn-success"));

    public ClarityWizardComponent(SeleniumComponent parent)
    {
        super(parent, selectByTagName("clr-wizard"));
    }

    public String getTitle()
    {
        return wizardTitle.getText();
    }

    @Override
    public boolean isVisible()
    {
        // Have to override the visible method. The host element is visible before the modal gets rendered.
        return wizardTitle.isVisible();
    }

    /**
     * @return the list of navigation steps for this component
     */
    public OrderedListComponent<ClarityWizardStepnavEntry> getStepNav()
    {
        return stepNav;
    }

    public void next()
    {
        nextButton.click();
    }

    public void previous()
    {
        prevButton.click();
    }

    public void finish()
    {
        finishButton.click();
    }

    private String getPageTitle()
    {
        return pageTitle.getText();
    }

    public static abstract class AbstractClarityWizardPageComponent extends AbstractSeleniumComponent
    {
        private final ClarityWizardComponent wizard;

        public AbstractClarityWizardPageComponent(ClarityWizardComponent wizard, WebElementSelector selector)
        {
            super(wizard.wizardContent, selector);

            this.wizard = wizard;
        }

        public String getTitle()
        {
            return wizard.getPageTitle();
        }
    }

    public static class ClarityWizardStepnavEntry extends AbstractSeleniumComponent
    {
        private final ButtonComponent button = new ButtonComponent(this);

        public ClarityWizardStepnavEntry(SeleniumComponent parent, WebElementSelector selector)
        {
            super(parent, selector);
        }

        public String getLabel()
        {
            return button.getLabel();
        }

        public void click()
        {
            button.click();
        }
    }
}
