/**
 *
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.AnimatedSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumAsserts;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentList;
import at.porscheinformatik.seleniumcomponents.SeleniumComponentListFactory;
import at.porscheinformatik.seleniumcomponents.SeleniumMatchers;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import at.porscheinformatik.seleniumcomponents.component.ButtonComponent;
import at.porscheinformatik.seleniumcomponents.component.HtmlComponent;

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

    private final SeleniumComponentListFactory<ClarityWizardStepnavEntry> stepNav = new SeleniumComponentListFactory<>(
        this, WebElementSelector.selectByClassName("clr-wizard-stepnav-item"), ClarityWizardStepnavEntry::new);

    private final HtmlComponent wizardContent = new HtmlComponent(this, selectByClassName("clr-wizard-content"));

    protected final HtmlComponent buttonWrapper =
        new HtmlComponent(this, selectByClassName("clr-wizard-footer-buttons-wrapper"));

    private final ButtonComponent nextButton = new ButtonComponent(buttonWrapper, selectByAttribute("type", "next"));
    private final ButtonComponent prevButton =
        new ButtonComponent(buttonWrapper, selectByAttribute("type", "previous"));
    private final ButtonComponent finishButton =
        new ButtonComponent(buttonWrapper, selectByAttribute("type", "finish"));

    public ClarityWizardComponent(SeleniumComponent parent)
    {
        this(parent, selectByTagName("clr-wizard"));
    }

    public ClarityWizardComponent(SeleniumComponent parent, WebElementSelector selector)
    {
        super(parent, selector);
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
    public SeleniumComponentList<ClarityWizardStepnavEntry> getStepNavItems()
    {
        return stepNav.findAll();
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

        SeleniumAsserts.assertThatSoon(() -> this, SeleniumMatchers.isNotVisible());
    }

    public boolean isNextClickable()
    {
        return nextButton.isClickable();
    }

    public boolean isPreviousClickable()
    {
        return prevButton.isClickable();
    }

    public boolean isFinishClickable()
    {
        return finishButton.isClickable();
    }

    public String getPageTitle()
    {
        return pageTitle.getText();
    }

    /**
     * page component
     */
    public static class ClarityWizardPageComponent extends AbstractSeleniumComponent
    {
        private final ClarityWizardComponent wizard;

        public ClarityWizardPageComponent(ClarityWizardComponent wizard, String seleniumKey)
        {
            this(wizard, WebElementSelector.selectBySeleniumKey(seleniumKey));
        }

        public ClarityWizardPageComponent(ClarityWizardComponent wizard, WebElementSelector selector)
        {
            super(wizard.wizardContent, selector);

            this.wizard = wizard;
        }

        public boolean isActive()
        {
            return containsClassName("active");
        }
    }

    /**
     * stepnav entry
     */
    public static class ClarityWizardStepnavEntry extends AbstractSeleniumComponent
    {
        private final ButtonComponent button = new ButtonComponent(this);
        private final HtmlComponent label =
            new HtmlComponent(button, selectByClassName("clr-wizard-stepnav-link-title"));

        public ClarityWizardStepnavEntry(SeleniumComponent parent, WebElementSelector selector)
        {
            super(parent, selector);
        }

        public String getLabel()
        {
            return label.getText();
        }

        public void click()
        {
            button.click();
        }
    }
}
