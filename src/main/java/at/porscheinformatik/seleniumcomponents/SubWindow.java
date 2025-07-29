package at.porscheinformatik.seleniumcomponents;

/**
 * Handles another window
 *
 * @author HAM
 */
public class SubWindow implements AutoCloseable {

    private final SeleniumEnvironment environment;
    private final String parentWindowHandle;
    private final String windowHandle;

    public SubWindow(SeleniumEnvironment environment, String parentWindowHandle, String windowHandle) {
        super();
        this.environment = environment;
        this.parentWindowHandle = parentWindowHandle;
        this.windowHandle = windowHandle;
    }

    public String getParentWindowHandle() {
        return parentWindowHandle;
    }

    public String getWindowHandle() {
        return windowHandle;
    }

    @SuppressWarnings("resource")
    @Override
    public void close() {
        environment.switchToWindow(parentWindowHandle);
    }
}
