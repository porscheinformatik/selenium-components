package at.porscheinformatik.seleniumcomponents;

import java.util.Locale;

import org.openqa.selenium.WebDriver;

/**
 * Implementation of a {@link SeleniumContext}, that extends an existing context but uses a different driver.
 *
 * @author HAM
 */
public class DerivedSeleniumContext implements SeleniumContext
{

    private final SeleniumContext parent;
    private final WebDriver driver;

    public DerivedSeleniumContext(SeleniumContext parent, WebDriver driver)
    {
        super();

        this.parent = parent;
        this.driver = driver;
    }

    public SeleniumContext getParent()
    {
        return parent;
    }

    @Override
    public WebDriver getDriver()
    {
        return driver;
    }

    @Override
    public Locale getLanguage()
    {
        return parent.getLanguage();
    }

    @Override
    public String getMessage(String key, Object... args)
    {
        return parent.getMessage(key, args);
    }

    @Override
    public void restart()
    {
        parent.restart();
    }

}
