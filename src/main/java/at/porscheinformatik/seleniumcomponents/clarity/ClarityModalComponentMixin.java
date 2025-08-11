package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.SeleniumComponent;

public interface ClarityModalComponentMixin extends SeleniumComponent {
    default ClarityModalComponent modalComponent() {
        return ClarityModalComponent.within(this);
    }

    default String getModalTitle() {
        return modalComponent().getTitle();
    }

    default void closeModal() {
        modalComponent().close();
    }

    default void clickModalButtonByLabel(String label) {
        modalComponent().clickButtonByLabel(label);
    }
}
