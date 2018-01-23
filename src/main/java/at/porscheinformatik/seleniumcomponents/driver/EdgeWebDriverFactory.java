package at.porscheinformatik.seleniumcomponents.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import at.porscheinformatik.seleniumcomponents.AbstractWebDriverFactory;
import at.porscheinformatik.seleniumcomponents.WebDriverFactory;

/**
 * A {@link WebDriverFactory} for Edge
 *
 * @author ham
 */
public class EdgeWebDriverFactory extends AbstractWebDriverFactory
{

    @Override
    public WebDriver createWebDriver(String sessionName)
    {
        return new EdgeDriver(createOptions());
    }

    protected EdgeOptions createOptions()
    {
        return new EdgeOptions();
    }
}
