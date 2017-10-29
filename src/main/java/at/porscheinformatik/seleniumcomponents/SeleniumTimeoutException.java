package at.porscheinformatik.seleniumcomponents;

/**
 * {@link RuntimeException} raised on timeout.
 * 
 * @author Family
 */
public class SeleniumTimeoutException extends SeleniumException
{

    private static final long serialVersionUID = -8852863537587389106L;

    public SeleniumTimeoutException(String message, Throwable t)
    {
        super(message, t);
    }

    public SeleniumTimeoutException(String s)
    {
        super(s);
    }

}
