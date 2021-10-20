package hr.fer.oprpp1.custom.collections;

/**
 * An elements getter to be used with implementations of the {@code Collection} interface.
 * Supplies the user with methods to get elements sequentially and check for remaining elements.
 */
public interface ElementsGetter {
    /**
     * Checks if there are any elements remaining in the collection, which were not already returned to the user by this {@code ElementsGetter}.
     * @return true if there are elements which were not iterated over, false otherwise
     */
    boolean hasNextElement();

    /**
     * Gets next element for this {@code ElementsGetter} and returns it
     * @return next element
     */
    Object getNextElement();

    /**
     * Processes the remaining elements with passed {@code Processor}
     * @param p {@code Processor} to be used for processing
     */
    default void processRemaining(Processor p) {
        while(this.hasNextElement()) {
            p.process(getNextElement());
        }
    }
}
