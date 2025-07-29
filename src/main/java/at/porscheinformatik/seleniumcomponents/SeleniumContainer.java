package at.porscheinformatik.seleniumcomponents;

/**
 * A container is a component that contains multiple other components, like a UL, that contains LIs.
 *
 * @author ham
 * @param <AnyChildComponent> the type of the children
 */
public interface SeleniumContainer<AnyChildComponent extends SeleniumComponent> extends SeleniumComponent {
    /**
     * Returns a {@link SeleniumComponentList} of all childs.
     *
     * @return a list, never null
     */
    SeleniumComponentList<AnyChildComponent> findAllChilds();
}
