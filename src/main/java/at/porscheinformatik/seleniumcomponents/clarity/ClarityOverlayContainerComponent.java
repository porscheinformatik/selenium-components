package at.porscheinformatik.seleniumcomponents.clarity;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumUtils;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

public class ClarityOverlayContainerComponent extends AbstractSeleniumComponent {

    protected ClarityOverlayContainerComponent(SeleniumComponent parent) {
        super(SeleniumUtils.root(parent), WebElementSelector.selectByClassName("cdk-overlay-container"));
    }
}
