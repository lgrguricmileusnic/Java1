package hr.fer.oprpp1.custom.collections;

/**
 * Functional interface which represents one operation to be performed with passed object.
 * @param <T> type of elements which will be processed
 */
@FunctionalInterface
public interface Processor<T> {
    /**
     * Operation to be performed on the passed object.
     *
     * @param value reference of the object on which the action will be performed
     */
    void process(T value);


}
