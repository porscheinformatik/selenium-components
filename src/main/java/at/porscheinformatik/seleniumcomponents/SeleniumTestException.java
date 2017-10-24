package at.porscheinformatik.seleniumcomponents;

/**
 * A {@link RuntimeException} for tests
 * 
 * @author ham
 */
public class SeleniumTestException extends RuntimeException
{

    private static final long serialVersionUID = -2439556835339621020L;

    public SeleniumTestException(String s)
    {
        super(s);
    }

    public SeleniumTestException(String message, Throwable t)
    {
        super(message, t);
    }
}
