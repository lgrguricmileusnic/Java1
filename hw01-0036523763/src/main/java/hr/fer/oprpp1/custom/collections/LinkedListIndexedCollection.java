package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class is a collection implementation similar in nature to the LinkedList class,
 * with allowed duplicates and no null values.
 * The collection is stored in nodes, one element per node, each node having references to its
 * successor and predecessor.
 */

public class LinkedListIndexedCollection extends Collection {
    /**
     * number of items stored in the collection
     */
    private int size;
    /**
     * reference to the first item node in the {@code LinkedListIndexedCollection}
     */
    private LinkedNode first;
    /**
     * reference to the last item node in the {@code LinkedListIndexedCollection}
     */
    private LinkedNode last;

    /**
     * Class representation of a node.
     */
    private static class LinkedNode {
        private LinkedNode previous;
        private LinkedNode next;
        private Object value;

        /**
         * Default constructor, initialises all LinkedNode data members as null
         */
        private LinkedNode() {
            previous = next = null;
            value = null;
        }

        /**
         * LinkedNode Constructor which stores passed {@code value} and sets up a one way link to the {@code previous} node.
         * To complete the link, a reference to this node must be stored in the previous node's {@code next} data member
         *
         * @param value    value to be stored in the node
         * @param previous reference to the previous node
         */
        private LinkedNode(Object value, LinkedNode previous) {
            Objects.requireNonNull(value);
            this.value = value;
            this.previous = previous;
            this.next = null;
        }
    }

    /**
     * Default constructor, creates empty collection.
     */
    public LinkedListIndexedCollection() {
        first = last = null;
        size = 0;
    }

    /**
     * Creates an instance of LinkedListIndexedCollection, containing elements from passed collection.
     *
     * @param collection collection from which the elements will be copied
     */
    public LinkedListIndexedCollection(Collection collection) {
        collection.forEach(new Processor() {
            @Override
            public void process(Object value) {
                add(value);
            }
        });
        this.size = collection.size();
    }

    /**
     * Creates node for passed value and links it to the last node in this collection.
     *
     * @param value element which will be added to the collection
     */
    public void add(Object value) {
        Objects.requireNonNull(value);
        if (size == 0) {
            first = last = new LinkedNode(value, null);
        } else {
            LinkedNode current = new LinkedNode(value, last);
            last.next = current;
            last = current;
        }
        size++;
    }

    /**
     * Returns value of element at passed index.
     * Never has greater complexity than n/2.
     *
     * @param index index of the sought object
     * @return value stored at the passed index
     */
    public Object get(int index) {
        if (!isInBounds(index)) throw new IndexOutOfBoundsException();
        return getNode(index).value;
    }

    /**
     * Inserts passed value at passed index. Shifts old value and its successors one position.
     *
     * @param value    value which will be inserted
     * @param position position at which the passed value will be inserted
     */
    public void insert(Object value, int position) {
        Objects.requireNonNull(value);
        if (position == size || size == 0) {
            add(value);
            return;
        }

        LinkedNode current = getNode(position);
        LinkedNode newNode = new LinkedNode(value, current.previous);
        newNode.next = current;
        current.previous = newNode;
        if (position == 0) {
            first = newNode;
        } else {
            newNode.previous.next = newNode;
        }


        size++;
    }

    /**
     * Searches the collection for the first element equal to the passed value and returns its index.
     *
     * @param value value of the sought element
     * @return index of the sought element if found, -1 otherwise
     */
    public int indexOf(Object value) {
        LinkedNode current = first;
        int index = 0;
        while (current != null) {
            if (current.value.equals(value)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    /**
     * Removes element from collection.
     *
     * @param index index of the element which should be removed
     * @throws IndexOutOfBoundsException if index is less than zero or greater than collection size - 1
     */
    public void remove(int index) {
        if (!isInBounds(index)) throw new IndexOutOfBoundsException();
        LinkedNode node = getNode(index);
        if (index == 0) {
            first = node.next;
            first.previous = null;
        } else if (index == size - 1) {
            last = node.previous;
            last.next = null;
        } else {
            node.next.previous = node.previous; //Link previous and next node
            node.previous.next = node.next;
        }
        node.value = null;
        size--;
    }

    /**
     * Removes first element in index order with the provided {@code value}.
     *
     * @param value Value of element which should be removed.
     * @return true if element was found and removed, otherwise false
     */
    @Override
    public boolean remove(Object value) {
        int foundObjectIndex = indexOf(value);
        if (foundObjectIndex == -1) return false;
        remove(foundObjectIndex);
        return true;
    }

    /**
     * Checks if collection is empty.
     *
     * @return true if collection has no elements, otherwise false
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in this collection.
     *
     * @return returns the number of currently stored objects in this collection
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if collection contains element of value <code>value</code>.
     *
     * @param value element whose presence is being tested.
     * @return true if collection contains element of value <code>value</code>, otherwise false
     */
    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    /**
     * Creates an array from all elements of this collection and returns it.
     *
     * @return array filled with elements from this collection
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        LinkedNode current = first;
        int i = 0;
        while (current != null) {
            array[i] = current.value;
            current = current.next;
            i++;
        }
        return array;
    }

    /**
     * Applies process method of provided processor to each element, for each element of the collection.
     *
     * @param processor processor used to process each collection element
     */
    @Override
    public void forEach(Processor processor) {
        LinkedNode current = first;
        while (current != null) {
            processor.process(current.value);
            current = current.next;
        }
    }

    /**
     * Removes all elements from this collection.
     */
    @Override
    public void clear() {
        first = last = null;
        size = 0;
    }

    /**
     * Checks if provided index is in collection bounds.
     *
     * @param index index which is being tested
     * @return true if index is in bounds, otherwise false
     */
    private boolean isInBounds(int index) {
        return index >= 0 && index < size();
    }

    /**
     * Gets node representation of the element stored at the passed index.
     *
     * @param index index at which the sought element is stored
     * @return node representation of the element stored at the passed index
     */
    private LinkedNode getNode(int index) {
        int indexCounter;
        LinkedNode current;
        if (!isInBounds(index)) throw new IndexOutOfBoundsException();
        if (index < ((Double.valueOf(size - 1)) / 2)) {
            current = first;
            indexCounter = 0;
            while (indexCounter != index) {
                current = current.next;
                indexCounter++;
            }
        } else {
            current = last;
            indexCounter = size - 1;
            while (indexCounter != index) {
                current = current.previous;
                indexCounter--;
            }
        }
        return current;
    }

    /**
     * Checks if the passed object is an instance of LinkedListIndexedCollection
     * and whether this collection and the passed collection have the same size, and elements in the same order.
     *
     * @param other object being compared with this collection
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof LinkedListIndexedCollection) {
            return this.size == ((LinkedListIndexedCollection) other).size() &&
                    Arrays.equals(this.toArray(), ((LinkedListIndexedCollection) other).toArray());
        }
        return false;
    }

}
