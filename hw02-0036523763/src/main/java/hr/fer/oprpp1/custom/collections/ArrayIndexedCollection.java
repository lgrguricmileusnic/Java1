package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementation of a collection with non-null elements and allowed duplicates.
 *
 * @author Lovro Grgurić Mileusnić
 */
public class ArrayIndexedCollection implements List {
    /**
     * number of elements stored in the collection
     */
    private int size;
    /**
     * Object array used to store the collection in memory.
     */
    private Object[] elements;

    /**
     * number of modifications made to this collection since its creation
     */
    private long modificationCount;

    /**
     * {@code ElementsGetter} implementation for {@code ArrayIndexedCollection}.
     * Supplies the user with methods to get this collection's elements sequentially and check for its remaining elements.
     */
    private static class ArrayIndexedCollectionElementsGetter implements ElementsGetter {
        /**
         * reference to this {@code ElementsGetter}'s collection
         */
        private ArrayIndexedCollection collection;

        /**
         * index of the next element which should be returned
         */
        private int nextIndex;

        /**
         * Saves the number of modifications made to this collection up to the point of creation of this {@code ElementsGetter},
         * used to ensure the collection has not been modified since
         */
        private long savedModificationCount;

        /**
         * Constructs an instance of {@code ArrayIndexedCollectionElementsGetter} for the passed {@code ArrayIndexedCollection}.
         * @param collection collection which this {@code ArrayIndexedCollectionElementsGetter} will get elements from
         */
        private ArrayIndexedCollectionElementsGetter(ArrayIndexedCollection collection) {
            this.collection = collection;
            savedModificationCount = collection.modificationCount;
            nextIndex = 0;
        }

        /**
         * Checks if there are any elements remaining in the collection, which were not already returned to the user by this {@code ElementsGetter}.
         *
         * @return true if there are elements which were not iterated over, false otherwise
         * @throws ConcurrentModificationException if collection was modified since creation of this {@code ElementsGetter}
         */
        @Override
        public boolean hasNextElement() {
            if(savedModificationCount != collection.modificationCount) throw new ConcurrentModificationException();
            return collection.isInBounds(nextIndex );
        }

        /**
         * Gets next element for this {@code ElementsGetter} and returns it
         * @return next element
         * @throws NoSuchElementException if all collection elements were passed;
         * @throws ConcurrentModificationException if collection was modified since creation of this {@code ElementsGetter}
         */
        @Override
        public Object getNextElement() {
            if (!hasNextElement()) throw new NoSuchElementException();
            return collection.get(nextIndex++);
        }
    }

    /**
     * Default constructor, creates an empty instance of this collection with an initial capacity of 16 elements
     */
    public ArrayIndexedCollection() {
        this.size = 0;
        this.elements = new Object[16];
    }

    /**
     * Constructor creates an empty instance of this collection with the provided {@code initialCapacity}.
     *
     * @param initialCapacity initial capacity of collection
     */
    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Initial capacity must be greater than 0.");
        }
        this.size = 0;
        this.elements = new Object[initialCapacity];
    }

    /**
     * Creates an instance of this collection with elements and capacity provided by the {@code other} collection.
     *
     * @param other collection from which the objects will be copied to the created collection
     */
    public ArrayIndexedCollection(Collection other) {
        Objects.requireNonNull(other);
        elements = new Object[other.size()];
        addAll(other);
    }

    /**
     * Creates an instance of this collection with elements provided by the {@code other} collection and with the provided {@code initial capacity}.
     * If the provided initial capacity is smaller than the provided collection, the provided collection's size will be used as initial capacity.
     *
     * @param other           collection from which the objects will be copied to the created collection
     * @param initialCapacity required initial capacity
     */
    public ArrayIndexedCollection(Collection other, int initialCapacity) {
        this(initialCapacity);
        Objects.requireNonNull(other);
        if (initialCapacity < other.size()) {
            elements = new Object[other.size()];
        }
        size = other.size();
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
     * Adds object to this collection, doubles the capacity if the collection is full.
     *
     * @param value element which will be added to the collection
     */
    @Override
    public void add(Object value) {
        Objects.requireNonNull(value);
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
            modificationCount++;
        }
        elements[size] = value;
        size++;
    }

    /**
     * Tests element presence in collection.
     *
     * @param value element whose presence is being tested.
     * @return true if element is present, false otherwise
     */
    @Override
    public boolean contains(Object value) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                return true;
            }
        }
        return false;
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
     * Removes element at provided index.
     *
     * @param index index of element which should be removed
     */
    public void remove(int index) {
        if (!isInBounds(index)) throw new IndexOutOfBoundsException();
        for (int j = index; j < size - 1; j++) {
            elements[j] = elements[j + 1];
        }
        elements[size - 1] = null;
        size--;
        /* If the removed element was the last element in the collection,
         modification count shouldn't change since it doesn't impact ArrayIndexedCollectionElementGetter workflow
         */
        if (index != size) modificationCount++;
    }

    /**
     * Creates an array from all elements of this collection and returns it.
     *
     * @return array filled with elements from this collection
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    /**
     * Removes all elements from this collection.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
        modificationCount++;
    }

    /**
     * Returns object at provided index
     *
     * @param index index of object which should be returned
     * @return object at the provided index
     * @throws IndexOutOfBoundsException if provided index is invalid
     */
    public Object get(int index) {
        if (!isInBounds(index)) throw new IndexOutOfBoundsException();
        return elements[index];
    }

    /**
     * Inserts given element at the provided index.
     *
     * @param value    element to be inserted
     * @param position index at which the element should be inserted
     */
    public void insert(Object value, int position) {
        if (position == size) {
            add(value);
            return;
        }
        if (!isInBounds(position)) throw new IndexOutOfBoundsException();
        Object[] helperArray = Arrays.copyOf(elements, size);
        clear();
        if (position != 0) {
            elements = Arrays.copyOfRange(helperArray, 0, position);
            size = elements.length;
        }
        add(value);
        for (int i = position; i < helperArray.length; i++) {
            add(helperArray[i]);
        }
        modificationCount++;
    }

    /**
     * Finds the first element equal to the provided {@code value}, determined using the {@code equals} method and returns its index.
     *
     * @param value sought element
     * @return index of sought element
     */
    public int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) return i;
        }
        return -1;
    }

    /**
     * Checks if the passed object is an instance of ArrayIndexedCollection
     * and whether this collection and the passed collection have the same size, and elements in the same order.
     *
     * @param other object being compared with this collection
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof ArrayIndexedCollection) {
            return this.size == ((ArrayIndexedCollection) other).size() &&
                    Arrays.equals(this.toArray(), ((ArrayIndexedCollection) other).toArray());
        }
        return false;
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
     * Creates an {@code ArrayIndexedCollectionElementsGetter} instance and returns its reference
     *
     * @return ArrayIndexedCollectionElementsGetter instance reference for this collection
     */
    @Override
    public ElementsGetter createElementsGetter() {
        return new ArrayIndexedCollectionElementsGetter(this);
    }
}
