package at.porscheinformatik.seleniumcomponents;

/**
 * {@link RuntimeException} raised on process interruption.
 *
 * @author Family
 */
public class SeleniumInterruptedException extends SeleniumException
{

    private static final long serialVersionUID = -8852863537587389106L;

    public SeleniumInterruptedException(String message, Throwable t)
    {
        super(message, t);
    }

    public SeleniumInterruptedException(String s)
    {
        super(s);
    }

}
