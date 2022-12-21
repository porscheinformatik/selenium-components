package at.porscheinformatik.seleniumcomponents;

/**
 * A {@link AbstractSeleniumPage}, that may open itself.
 *
 * @author Daniel Furtlehner
 */
public abstract class AbstractSeleniumStandalonePage extends AbstractSeleniumPage
{
    public AbstractSeleniumStandalonePage(SeleniumEnvironment environment)
    {
        super(environment);
    }

    /**
     * Returns the URL to open.
     *
     * @return the url
     */
    protected abstract String getUrl();

    public void open()
    {
        environment().open(getUrl(), this);
    }

    public void close()
    {
        environment().quit();
    }

    @Override
    public String toString()
    {
        return String.format(getUrl());
    }
}
