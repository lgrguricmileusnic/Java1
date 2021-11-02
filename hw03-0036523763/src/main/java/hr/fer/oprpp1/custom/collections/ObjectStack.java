package hr.fer.oprpp1.custom.collections;

/**
 * {@code ObjectStack} is an adaptor class adapting {@code ArrayIndexedCollection} to provide its users with methods natural for a stack.
 * {@code ArrayIndexedCollection} is used for storage of the "stack's" elements.
 * @param <T> type of elements stored on this stack
 */

public class ObjectStack<T> {
    /**
     * Data member of class ArrayIndexedCollection used for storage.
     */
    private ArrayIndexedCollection<T> storage;

    /**
     * Default constructor, creates empty collection which will be used as stack storage
     */
    public ObjectStack() {
        storage = new ArrayIndexedCollection<>();
    }

    /**
     * Checks if stack is empty.
     *
     * @return true if stack contains no objects and false otherwise
     */
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    /**
     * Returns stack size.
     *
     * @return number of elements currently stored objects on the stack
     */
    public int size() {
        return storage.size();
    }

    /**
     * Puts a value on the stack.
     *
     * @param value value to be put on the stack
     */
    public void push(T value) {
        storage.add(value);
    }

    /**
     * Removes element from the top of this stack and returns it.
     *
     * @return popped element
     */
    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        T element = storage.get(storage.size() - 1);
        storage.remove(storage.size() - 1);
        return element;
    }

    /**
     * Returns element from the top of this stack, but does not remove it.
     *
     * @return element from the top of this stack
     */
    public T peek() {
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
