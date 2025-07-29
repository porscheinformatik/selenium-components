package at.porscheinformatik.seleniumcomponents.clarity;

import static at.porscheinformatik.seleniumcomponents.WebElementSelector.*;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumComponent;
import at.porscheinformatik.seleniumcomponents.SeleniumComponent;
import at.porscheinformatik.seleniumcomponents.WebElementSelector;
import java.util.Objects;

public class ClarityIconComponent extends AbstractSeleniumComponent {

    public static ClarityIconComponent in(SeleniumComponent parent) {
        return new ClarityIconComponent(parent, WebElementSelector.selectByTagName("cds-icon"));
    }

    public static ClarityIconComponent withShape(SeleniumComponent parent, String shape) {
        return new ClarityIconComponent(parent, selectByAttributeContains("cds-icon", "shape", shape));
    }

    // ---

    public ClarityIconComponent(SeleniumComponent parent) {
        this(parent, selectByTagName("cds-icon"));
    }

    public ClarityIconComponent(SeleniumComponent parent, WebElementSelector selector) {
        super(parent, selector);
    }

    // ---

    public String getShape() {
        return this.getAttribute("shape");
    }

    public ClarityIconDirection getDirection() {
        String direction = this.getAttribute("direction");

        return ClarityIconDirection.fromDirection(direction);
    }

    // ===

    public enum ClarityIconDirection {
        UP("up"),
        DOWN("down"),
        LEFT("left"),
        RIGHT("right"),
        UNSET(null);

        private final String direction;

        public static ClarityIconDirection fromDirection(String direction) {
            for (ClarityIconDirection val : values()) {
                if (Objects.equals(val.getDirection(), direction)) {
                    return val;
                }
            }

            return UNSET;
        }

        // ---

        private ClarityIconDirection(String direction) {
            this.direction = direction;
        }

        // ---

        public String getDirection() {
            return direction;
        }
    }
}
