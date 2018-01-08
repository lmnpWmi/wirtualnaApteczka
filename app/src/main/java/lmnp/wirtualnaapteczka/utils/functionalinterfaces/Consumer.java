package lmnp.wirtualnaapteczka.utils.functionalinterfaces;

/**
 * Functional interface that invokes action accepting Context as parameter and without returning anything.
 *
 * @author Sebastian Nowak
 * @createdAt 26.12.2017
 */
public interface Consumer<T> {
    void accept(T t);
}
