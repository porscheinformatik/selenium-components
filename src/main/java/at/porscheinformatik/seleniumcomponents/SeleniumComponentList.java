package at.porscheinformatik.seleniumcomponents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Holds multiple {@link SeleniumComponent}s
 *
 * @author ham
 * @param <COMPONENT_TYPE> the type of the components
 */
public class SeleniumComponentList<COMPONENT_TYPE extends SeleniumComponent> implements Iterable<COMPONENT_TYPE> {

    private final List<COMPONENT_TYPE> components;

    public SeleniumComponentList(List<COMPONENT_TYPE> components) {
        super();
        this.components = components;
    }

    public COMPONENT_TYPE get(int index) {
        return components.get(index);
    }

    public boolean isEmpty() {
        return components.isEmpty();
    }

    public int size() {
        return components.size();
    }

    public boolean containsAny(Predicate<COMPONENT_TYPE> predicate) {
        return components.stream().anyMatch(predicate);
    }

    public boolean containsOnly(Predicate<COMPONENT_TYPE> predicate) {
        return components.stream().allMatch(predicate);
    }

    /**
     * Returns the first component that matches the predicate.
     *
     * @param predicate the predicate
     * @return the component, null if not found
     */
    public COMPONENT_TYPE find(Predicate<COMPONENT_TYPE> predicate) {
        return components.stream().filter(predicate).findFirst().orElse(null);
    }

    public SeleniumComponentList<COMPONENT_TYPE> filter(Predicate<COMPONENT_TYPE> predicate) {
        return new SeleniumComponentList<>(components.stream().filter(predicate).collect(Collectors.toList()));
    }

    public <Any> Stream<Any> map(Function<COMPONENT_TYPE, Any> function) {
        return components.stream().map(function);
    }

    public Optional<COMPONENT_TYPE> findAny() {
        return components.stream().findAny();
    }

    public Optional<COMPONENT_TYPE> findFirst() {
        return components.stream().findFirst();
    }

    @Override
    public Iterator<COMPONENT_TYPE> iterator() {
        return components.iterator();
    }

    public Stream<COMPONENT_TYPE> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    public List<COMPONENT_TYPE> toList() {
        return new ArrayList<>(components);
    }

    /**
     * @param mapping mapping to apply to each entry
     * @return list of transformed types
     * @param <ResultT> type of transformed object
     */
    public <ResultT> List<ResultT> toList(Function<COMPONENT_TYPE, ResultT> mapping) {
        return stream().map(mapping).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "SeleniumComponentList [components=" + components + "]";
    }
}
