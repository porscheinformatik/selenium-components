package at.porscheinformatik.seleniumcomponents;

/**
 * A template is a {@link SeleniumComponentFactory}, that contains a {@link WebElementSelector}, too.
 *
 * @author ham
 * @param <AnySeleniumComponent> the type of Selenium components to create
 */
public interface SeleniumComponentTemplate<AnySeleniumComponent extends SeleniumComponent>
    extends SeleniumComponentFactory<AnySeleniumComponent>
{

    /**
     * Creates a {@link SeleniumComponentTemplate}, that uses the specified component as template. The child instance
     * must provide the
     * {@link AbstractSeleniumComponent#AbstractSeleniumComponent(SeleniumComponent, WebElementSelector)} constructor.
     * If the specified component is a {@link SeleniumComponentTemplate}, it returns the component unmodified.
     *
     * @param <AnySeleniumComponent> the type of the component
     * @param template a component instance used as template
     * @return a list factory
     */
    @SuppressWarnings("unchecked")
    static <AnySeleniumComponent extends SeleniumComponent> SeleniumComponentTemplate<AnySeleniumComponent> of(
        AnySeleniumComponent template)
    {
        if (template instanceof SeleniumComponentTemplate)
        {
            return (SeleniumComponentTemplate<AnySeleniumComponent>) template;
        }

        return of(template.selector(), SeleniumComponentFactory.of(template.getClass()));
    }

    /**
     * Creates a {@link SeleniumComponentTemplate} of the specified selector and the specified factory.
     *
     * @param <AnySeleniumComponent> the type of the component
     * @param selector the selector
     * @param factory the factory
     * @return the template
     */
    static <AnySeleniumComponent extends SeleniumComponent> SeleniumComponentTemplate<AnySeleniumComponent> of(
        WebElementSelector selector, SeleniumComponentFactory<AnySeleniumComponent> factory)
    {
        return new SeleniumComponentTemplate<AnySeleniumComponent>()
        {
            @Override
            public WebElementSelector selector()
            {
                return selector;
            }

            @Override
            public AnySeleniumComponent create(SeleniumComponent parent, WebElementSelector selector)
            {
                return factory.create(parent, selector);
            }
        };
    }

    /**
     * Returns the {@link WebElementSelector} for components described by this template.
     *
     * @return the selector
     */
    WebElementSelector selector();

}
