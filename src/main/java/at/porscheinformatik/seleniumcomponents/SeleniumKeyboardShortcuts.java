package at.porscheinformatik.seleniumcomponents;

import org.openqa.selenium.Keys;

public class SeleniumKeyboardShortcuts {

    private static final boolean IS_MAC = System.getProperty("os.name").toLowerCase().contains("mac");

    private static final Keys COMMAND_OR_CONTROL_KEY = IS_MAC ? Keys.COMMAND : Keys.CONTROL;

    private SeleniumKeyboardShortcuts() {
        // Utility class
    }

    public static CharSequence selectAll() {
        return Keys.chord(COMMAND_OR_CONTROL_KEY, "A");
    }

    public static CharSequence[] deleteAll() {
        return new CharSequence[] { selectAll(), Keys.BACK_SPACE };
    }

    public static CharSequence cut() {
        return Keys.chord(COMMAND_OR_CONTROL_KEY, "X");
    }

    public static CharSequence copy() {
        return Keys.chord(COMMAND_OR_CONTROL_KEY, "C");
    }

    public static CharSequence paste() {
        return Keys.chord(COMMAND_OR_CONTROL_KEY, "V");
    }
}
