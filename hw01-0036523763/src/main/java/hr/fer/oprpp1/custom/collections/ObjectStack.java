package hr.fer.oprpp1.custom.collections;

/**
 * {@code ObjectStack} is an adaptor class adapting {@code ArrayIndexedCollection} to provide its users with methods natural for a stack.
 * {@code ArrayIndexedCollection} is used for storage of the "stack's" elements.
 */

public class ObjectStack {
    /**
     * Data member of class ArrayIndexedCollection used for storage.
     */
    private ArrayIndexedCollection storage;

    /**
     * Default constructor, creates empty collection which will be used as stack storage
     */
    public ObjectStack() {
        storage = new ArrayIndexedCollection();
    }

    /**
     * Checks if collection is empty.
     * @return true if collection contains no objects and false otherwise
     */
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    /**
     * Returns stack size.
     * @return number of elements currently stored objects on the stack
     */
    public int size() {
        return storage.size();
    }

    /**
     * Puts a value on the stack.
     * @param value value to be put on the stack
     */
    public void push(Object value) {
        storage.add(value);
    }

    /**
     * Removes element from the top of this stack and returns it.
     * @return popped element
     */
    public Object pop() {
        if (isEmpty()) throw new EmptyStackException();
        Object element = storage.get(storage.size() - 1);
        storage.remove(storage.size() - 1);
        return element;
    }

    /**
     * Returns element from the top of this stack, but does not remove it.
     * @return element from the top of this stack
     */
    public Object peek() {
        if (isEmpty()) throw new EmptyStackException();
        return storage.get(storage.size() - 1);
    }

    /**
     * Removes all elements from the stack.
     */
    public void clear() {
        storage.clear();
    }
}
