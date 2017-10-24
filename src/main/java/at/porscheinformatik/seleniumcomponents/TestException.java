package at.porscheinformatik.seleniumcomponents;

/**
 * A {@link RuntimeException} for tests
 * 
 * @author ham
 */
public class TestException extends RuntimeException
{

    private static final long serialVersionUID = -2439556835339621020L;

    public TestException(String s)
    {
        super(s);
    }

    public TestException(String message, Throwable t)
    {
        super(message, t);
    }
}
