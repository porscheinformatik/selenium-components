package at.porscheinformatik.seleniumcomponents;

/**
 * Handles another window
 *
 * @author HAM
 */
public class SubWindow implements AutoCloseable
{
    private final SeleniumEnvironment environment;
    private final String parentWindowHandle;

    public SubWindow(SeleniumEnvironment environment, String parentWindowHandle)
    {
        super();

        this.environment = environment;
        this.parentWindowHandle = parentWindowHandle;
    }

    @Override
    public void close()
    {
        environment.getDriver().switchTo().window(parentWindowHandle);
    }
}
