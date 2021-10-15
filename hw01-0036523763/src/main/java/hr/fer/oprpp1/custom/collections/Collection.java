package hr.fer.oprpp1.custom.collections;


/**
 * Default implementation of a collection,
 * to be used in a similar manner as an interface,
 * for implementing concrete collection classes.
 *
 * @author Lovro Grgurić Mileusnić
 */

public class Collection {


    protected Collection() {
    }

    /**
     * @return true if collection contains no objects and false otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns collection size, default implementation always returns 0.
     *
     * @return returns the number of currently stored objects in this collection, default implementation always returns 0
     */
    public int size() {
        return 0;
    }

    /**
     * Adds object to this collection.
     * No default implementation.
     *
     * @param value element which will be added to the collection.
     */
    public void add(Object value) {
    }


    /**
     * Checks if collection contains element <code>value</code>.
     * Default implementation always returns false.
     *
     * @param value element whose presence is being tested.
     * @return true if collection contains element <code>value</code>, otherwise false, default implementation always returns false
     */
    public boolean contains(Object value) {
        return false;
    }

    /**
     * Removes element with the same <code>value</code>.
     * Default implementation always returns false;
     *
     * @param value Value of element which should be removed.
     * @return true if an element was removed, othewise false, default implementation always returns false
     */
    public boolean remove(Object value) {
        return false;
    }

    /**
     * Creates an array from all elements of this collection and returns it.
     * Default implementation throws <code>UnsupportedOperationException</code>.
     *
     * @return array filled with elements from this collection
     * @throws UnsupportedOperationException
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Applies process method of provided processor to each element, for each element of the collection.
     * No default implementation.
     *
     * @param processor processor used to process collection elements
     */
    public void forEach(Processor processor) {
    }

    /**
     * Adds all elements from provided collection to this collection.
     *
     * @param other collection from which the elements will be added.
     */
    public void addAll(Collection other) {
        class addCollectionProcessor extends Processor {
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
    public void clear() {
    }

}
