package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;

public interface ClarityWizardComponentMixin extends SeleniumComponent {
    default ClarityWizardComponent wizardComponent() {
        return ClarityWizardComponent.within(this);
    }

    /**
     * Click the "next" button and expect the page title to change.
     */
    default void nextWizardPage() {
        wizardComponent().next();
    }

    default void clickNextWizardPage() {
        wizardComponent().clickNext();
    }

    /**
     * Click the "previous" button and expect the page title to change.
     */
    default void previousWizardPage() {
        wizardComponent().previous();
    }

    default void clickPreviousWizardPage() {
        wizardComponent().clickPrevious();
    }

    /**
     * Click the "finish" button and expect the wizard to close itself.
     */
    default void finishWizard() {
        wizardComponent().finish();
    }

    default void clickFinishWizard() {
        wizardComponent().clickFinish();
    }
}
