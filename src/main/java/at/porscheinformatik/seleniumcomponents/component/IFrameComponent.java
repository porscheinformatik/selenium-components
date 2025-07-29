package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumEnvironment;
import at.porscheinformatik.seleniumcomponents.SeleniumGlobals;
import at.porscheinformatik.seleniumcomponents.SeleniumUtils;
import at.porscheinformatik.seleniumcomponents.SubFrame;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;

public class IFrameComponent extends AbstractSeleniumComponent {

    public IFrameComponent(SeleniumComponent parent) {
        this(parent, WebElementSelector.selectByTagName("iframe"));
    }

    public IFrameComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    /**
     * Tries to switch to the frame of the specified element. After this, the {@link SeleniumEnvironment} and it's web
     * driver points to the frame, until the {@link SubFrame} will be closed.
     *
     * @return an {@link AutoCloseable} to reset the environment
     */
    public SubFrame inFrame() {
        return SeleniumUtils.keepTrying(SeleniumGlobals.getLongTimeoutInSeconds(), () ->
            environment().switchToFrame(element())
        );
    }
}
