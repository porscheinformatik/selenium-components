package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.SeleniumAsserts;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.clarity.ClarityWizardComponent.ClarityWizardPageComponent;
import org.hamcrest.Matchers;

public interface ClarityWizardComponentMixin extends SeleniumComponent {
    default ClarityWizardComponent wizardComponent() {
        return ClarityWizardComponent.within(this);
    }

    default ClarityWizardPageComponent wizardPageByTestId(String testId) {
        return ClarityWizardComponent.ClarityWizardPageComponent.byTestId(wizardComponent(), testId);
    }

    default String getWizardTitle() {
        return wizardComponent().getTitle();
    }

    default String getWizardPageTitle() {
        return wizardComponent().getPageTitle();
    }

    default void assertWizardPageActiveSoon(String title) {
        SeleniumAsserts.assertThatLater(this::getWizardPageTitle, Matchers.equalTo(title));
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
