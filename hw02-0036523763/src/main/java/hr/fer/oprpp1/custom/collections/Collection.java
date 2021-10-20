package hr.fer.oprpp1.custom.collections;


/**
 * Interface for implementing concrete collection classes.
 *
 * @author Lovro Grgurić Mileusnić
 */

public interface Collection {



    /**
     * Checks if collection is empty.
     *
     * @return true if collection contains no objects and false otherwise
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns collection size, default implementation always returns 0.
     *
     * @return returns the number of currently stored objects in this collection
     */
    int size();

    /**
     * Adds object to this collection.
     *
     * @param value element which will be added to the collection.
     */
    void add(Object value);


    /**
     * Checks if collection contains element <code>value</code>.
     *
     * @param value element whose presence is being tested.
     * @return true if collection contains element <code>value</code>, otherwise false
     */
    boolean contains(Object value);

    /**
     * Removes element with the same <code>value</code>.
     *
     * @param value Value of element which should be removed.
     * @return true if an element was removed, otherwise false, default implementation always returns false
     */
    boolean remove(Object value);

    /**
     * Creates an array from all elements of this collection and returns it.
     *
     * @return array filled with elements from this collection
     */
    Object[] toArray();

    /**
     * Applies process method of provided processor to each element, for each element of the collection.
     *
     * @param processor processor used to process collection elements
     */
    default void forEach(Processor processor) {
        ElementsGetter elementsGetter = createElementsGetter();
        while(elementsGetter.hasNextElement()) {
            processor.process(elementsGetter.getNextElement());
        }
    }

    /**
     * Adds all elements from provided collection to this collection.
     *
     * @param other collection from which the elements will be added.
     */
    default void addAll(Collection other) {
        class addCollectionProcessor implements Processor {
            @Override
            public void process(Object value) {
                add(value);
            }
        }
        other.forEach(new addCollectionProcessor());
    }

    /**
     * Removes all elements from this collection.
     */
    void clear();

    /**
     * Creates an {@code ElementsGetter} instance and returns its reference
     * @return ElementsGetter instance reference for this collection
     */
    ElementsGetter createElementsGetter();

    /**
     * Adds all elements from passed collection satisfying the passed {@code Tester}.
     * @param col collection whose elements will be tested
     * @param tester tester which should be satisfied
     */
    default void addAllSatisfying(Collection col, Tester tester) {
        ElementsGetter elementsGetter = col.createElementsGetter();
        Object element;
        while(elementsGetter.hasNextElement()) {
            element = elementsGetter.getNextElement();
            if (tester.test(element)){
                this.add(element);
            }
        }
    }

}
