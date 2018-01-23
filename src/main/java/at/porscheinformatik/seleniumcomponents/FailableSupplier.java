package at.porscheinformatik.seleniumcomponents;

import java.util.function.Supplier;

/**
 * A {@link Supplier}, that may fail with an exception
 *
 * @author ham
 * @param <T> the type of the supplied value
 */
@FunctionalInterface
public interface FailableSupplier<T>
{

    T get() throws Exception;

}