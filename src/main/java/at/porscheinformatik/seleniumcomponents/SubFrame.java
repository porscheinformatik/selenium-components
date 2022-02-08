package at.porscheinformatik.seleniumcomponents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles another frame
 *
 * @author HAM
 */
public class SubFrame implements AutoCloseable
{
    private static final Logger LOG = LoggerFactory.getLogger(SubFrame.class);

    private final SeleniumEnvironment environment;

    public SubFrame(SeleniumEnvironment environment)
    {
        super();

        this.environment = environment;
    }

    @Override
    public void close()
    {
        try
        {
            environment.getDriver().switchTo().defaultContent();
        }
        catch (Exception e)
        {
            LOG.info("Failed to switch to default content. The browser may have died already", e);
        }
    }
}
