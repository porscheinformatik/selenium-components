package at.porscheinformatik.seleniumcomponents;

import java.util.Locale;

import org.openqa.selenium.WebDriver;

/**
 * Implementation of a {@link SeleniumEnvironment}, that extends an existing environment but uses a different driver.
 *
 * @author HAM
 */
public class DerivedSeleniumEnvironment implements SeleniumEnvironment
{

    private final SeleniumEnvironment parent;
    private final WebDriver driver;

    public DerivedSeleniumEnvironment(SeleniumEnvironment parent, WebDriver driver)
    {
        super();

        this.parent = parent;
        this.driver = driver;
    }

    public SeleniumEnvironment getParent()
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
