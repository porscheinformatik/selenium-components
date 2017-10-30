package at.porscheinformatik.seleniumcomponents.component;

import at.porscheinformatik.seleniumcomponents.AbstractSeleniumPage;
import at.porscheinformatik.seleniumcomponents.SeleniumContext;

/**
 * A basic HTML page.
 *
 * @author ham
 */
public class HtmlPage extends AbstractSeleniumPage
{

    private final String url;

    public HtmlPage(SeleniumContext context, String url)
    {
        super(context);
        this.url = url;
    }

    @Override
    public String getUrl()
    {
        return url;
    }

}
