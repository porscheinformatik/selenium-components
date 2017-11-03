package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumPage;
import at.porscheinformatik.seleniumcomponents.SeleniumEnvironment;

/**
 * A basic HTML page.
 *
 * @author ham
 */
public class HtmlPage extends AbstractSeleniumPage
{

    private final String url;

    public HtmlPage(SeleniumEnvironment environment, String url)
    {
        super(environment);
        this.url = url;
    }

    @Override
    public String getUrl()
    {
        return url;
    }

}
