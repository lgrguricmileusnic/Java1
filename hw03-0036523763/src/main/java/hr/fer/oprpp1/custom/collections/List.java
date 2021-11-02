package hr.fer.oprpp1.custom.collections;

/**
 * Interface describes an ordered collection with allowed duplicates which provides the user with methods
 * that grant the user access, removal and insertion of elements by their index.
 * @param <T> type of elements in this list
 */
public interface List<T> extends Collection<T> {
    /**
     * Returns object at provided index
     *
     * @param index index of object which should be returned
     * @return object at the provided index
     */
    T get(int index);

    /**
     * Inserts given element at the provided index.
     *
     * @param value    element to be inserted
     * @param position index at which the element should be inserted
     */
    void insert(T value, int position);

    /**
     * Finds the first element equal to the provided {@code value}, determined using the {@code equals} method, and returns its index.
     *
     * @param value sought element
     * @return index of sought element
     */
    int indexOf(T value);

    /**
     * Removes element at provided index.
     *
     * @param index index of element which should be removed
     */
    void remove(int index);
}
