package at.porscheinformatik.seleniumcomponents;

/**
 * @author Daniel Furtlehner
 */
public interface SeleniumPage extends SeleniumComponent
{

    @Override
    default SeleniumComponent parent()
    {
        return null;
    }

    void open();

    void close();

    /**
     * @return the screenshot Base64 encoded.
     */
    String getScreenshotFromActualPage();

}
