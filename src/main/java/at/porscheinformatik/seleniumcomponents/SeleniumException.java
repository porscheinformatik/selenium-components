package at.porscheinformatik.seleniumcomponents;

/**
 * A {@link RuntimeException} for tests.
 *
 * @author ham
 */
public class SeleniumException extends RuntimeException
{

    private static final long serialVersionUID = -2439556835339621020L;

    public SeleniumException(String s)
    {
        super(s);
    }

    public SeleniumException(String message, Throwable t)
    {
        super(message, t);
    }
}
