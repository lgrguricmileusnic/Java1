package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * Implementation of a collection with non-null elements and allowed duplicates.
 *
 * @author Lovro Grgurić Mileusnić
 */
public class ArrayIndexedCollection extends Collection {
    private int size;
    private Object[] elements;

    /**
     * Default constructor, creates an empty instance of this collection with an initial capacity of 16 elements
     */
    public ArrayIndexedCollection() {
        this.size = 0;
        this.elements = new Object[16];
    }

    /**
     * Constructor creates an empty instance of this collection with the provided {@code initialCapacity}.
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
     * @param other collection from which the objects will be copied to the created collection
     */
    public ArrayIndexedCollection(Collection other) {
        Objects.requireNonNull(other);
        this.size = other.size();
        elements = new Object[size];
        addAll(other);
    }

    /**
     * Creates an instance of this collection with elements provided by the {@code other} collection and with the provided {@code initial capacity}.
     * If the provided initial capacity is smaller than the provided collection, the provided collection's size will be used as initial capacity.
     * @param other collection from which the objects will be copied to the created collection
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
     * @return returns the number of currently stored objects in this collection
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Adds object to this collection, doubles the capacity if the collection is full.
     * @param value element which will be added to the collection.
     */
    @Override
    public void add(Object value) {
        Objects.requireNonNull(value);
        if (size == elements.length) {
            Arrays.copyOf(elements, elements.length * 2);
        }
        elements[size] = value;
        size++;
    }

    /**
     * Tests element presence in collection.
     * @param value element whose presence is being tested.
     * @return true if element is present, false otherwise
     */
    @Override
    public boolean contains(Object value) {
        for(int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes first element in index order with the provided {@code value}.
     * @param value Value of element which should be removed.
     * @return true if element was found and removed, otherwise false
     */
    @Override
    public boolean remove(Object value) {
        Object[] helperArray = new Object[elements.length];
        int helperArrayIndex = 0;
        for(int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                helperArray[helperArrayIndex] = elements[i];
                helperArrayIndex++;
            }
        }
        if(elements.)
    }

    public boolean remove(int index){

    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public Object[] toArray() throws UnsupportedOperationException {
        return super.toArray();
    }

    @Override
    public void forEach(Processor processor) {
        super.forEach(processor);
    }

    @Override
    public void clear() {
        super.clear();
    }
}
