package at.porscheinformatik.seleniumcomponents;

/**
 * Handles another frame
 *
 * @author HAM
 */
public class SubFrame implements AutoCloseable
{
    private final SeleniumEnvironment environment;

    public SubFrame(SeleniumEnvironment environment)
    {
        super();

        this.environment = environment;
    }

    @Override
    public void close()
    {
        environment.getDriver().switchTo().defaultContent();
    }
}
