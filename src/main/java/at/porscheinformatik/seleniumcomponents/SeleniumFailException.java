package at.porscheinformatik.seleniumcomponents;

/**
 * {@link RuntimeException} raised on failure.
 *
 * @author Family
 */
public class SeleniumFailException extends SeleniumException
{

    private static final long serialVersionUID = -8794168869932496290L;

    public SeleniumFailException(String message, Throwable t)
    {
        super(message, t);
    }

    public SeleniumFailException(String s)
    {
        super(s);
    }

}
