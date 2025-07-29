/**
 *
 */
package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;
import static org.hamcrest.Matchers.*;

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
public abstract class ClarityWizardComponent extends AbstractSeleniumComponent implements AnimatedSeleniumComponent {

    private final HtmlComponent wizardTitle = new HtmlComponent(this, selectByTagName("clr-wizard-title"));
    private final HtmlComponent pageTitle = new HtmlComponent(this, selectByClassName("modal-title-text"));

    private final SeleniumComponentListFactory<ClarityWizardStepnavEntry> stepNav = new SeleniumComponentListFactory<>(
        this,
        WebElementSelector.selectByClassName("clr-wizard-stepnav-item"),
        ClarityWizardStepnavEntry::new
    );

    private final HtmlComponent wizardContent = new HtmlComponent(this, selectByClassName("clr-wizard-content"));

    protected final HtmlComponent buttonWrapper = new HtmlComponent(
        this,
        selectByClassName("clr-wizard-footer-buttons-wrapper")
    );

    private final ButtonComponent nextButton = new ButtonComponent(buttonWrapper, selectByAttribute("type", "next"));
    private final ButtonComponent prevButton = new ButtonComponent(
        buttonWrapper,
        selectByAttribute("type", "previous")
    );
    private final ButtonComponent finishButton = new ButtonComponent(
        buttonWrapper,
        selectByAttribute("type", "finish")
    );

    public ClarityWizardComponent(SeleniumComponent parent) {
        this(parent, selectByTagName("clr-wizard"));
    }

    public ClarityWizardComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    public String getTitle() {
        return wizardTitle.getText();
    }

    @Override
    public boolean isVisible() {
        // Have to override the visible method. The host element is visible before the modal gets rendered.
        return wizardTitle.isVisible();
    }

    /**
     * @return the list of navigation steps for this component
     */
    public SeleniumComponentList<ClarityWizardStepnavEntry> getStepNavItems() {
        return stepNav.findAll();
    }

    /**
     * Click the "next" button and expect the page title to change.
     */
    public void next() {
        String pageTitle = getPageTitle();

        clickNext();

        SeleniumAsserts.assertThatSoon("Page title should change", this::getPageTitle, not(equalTo(pageTitle)));
    }

    /**
     * Click the "next" button.
     */
    public void clickNext() {
        nextButton.click();
    }

    /**
     * Click the "previous" button and expect the page title to change.
     */
    public void previous() {
        String pageTitle = getPageTitle();

        clickPrevious();

        SeleniumAsserts.assertThatSoon("Page title should change", this::getPageTitle, not(equalTo(pageTitle)));
    }

    /**
     * Click the "previous" button.
     */
    public void clickPrevious() {
        prevButton.click();
    }

    /**
     * Click the "finish" button and expect the wizard to close itself.
     */
    public void finish() {
        clickFinish();

        SeleniumAsserts.assertThatSoon("Wizard should be closed", () -> this, SeleniumMatchers.isNotVisible());
    }

    /**
     * Click the "finish" button.
     */
    public void clickFinish() {
        finishButton.click();
    }

    public boolean isNextClickable() {
        return nextButton.isClickable();
    }

    public boolean isPreviousClickable() {
        return prevButton.isClickable();
    }

    public boolean isFinishClickable() {
        return finishButton.isClickable();
    }

    public String getPageTitle() {
        return pageTitle.getText();
    }

    /**
     * page component
     */
    public static class ClarityWizardPageComponent extends AbstractSeleniumComponent {

        public static ClarityWizardPageComponent byTestId(ClarityWizardComponent wizard, String testId) {
            return new ClarityWizardPageComponent(wizard, selectByTestId(testId));
        }

        private final ClarityWizardComponent wizard;

        /**
         * @deprecated Use {@link #ClarityWizardPageComponent(ClarityWizardComponent, WebElementSelector)} instead
         */
        @Deprecated(forRemoval = true)
        public ClarityWizardPageComponent(ClarityWizardComponent wizard, String seleniumKey) {
            this(wizard, WebElementSelector.selectByTestIdOrSeleniumKey(seleniumKey));
        }

        public ClarityWizardPageComponent(ClarityWizardComponent wizard, WebElementSelector selector) {
            super(wizard.wizardContent, selector);
            this.wizard = wizard;
        }

        public ClarityWizardComponent getWizard() {
            return wizard;
        }

        public boolean isActive() {
            return containsClassName("active");
        }
    }

    /**
     * stepnav entry
     */
    public static class ClarityWizardStepnavEntry extends AbstractSeleniumComponent {

        private final ButtonComponent button = new ButtonComponent(this);
        private final HtmlComponent label = new HtmlComponent(
            button,
            selectByClassName("clr-wizard-stepnav-link-title")
        );

        public ClarityWizardStepnavEntry(SeleniumComponent parent, WebElementSelector selector) {
            super(parent, selector);
        }

        public String getLabel() {
            return label.getText();
        }

        public void click() {
            button.click();
        }
    }
}
