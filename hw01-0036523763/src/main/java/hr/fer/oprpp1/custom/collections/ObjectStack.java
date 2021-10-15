package hr.fer.oprpp1.custom.collections;

/**
 * {@code ObjectStack} is an adaptor class adapting {@code ArrayIndexedCollection} to provide its users with methods natural for a stack.
 * {@code ArrayIndexedCollection} is used for storage of the "stack's" elements.
 */

public class ObjectStack {
    private ArrayIndexedCollection storage;

    public ObjectStack() {
        storage = new ArrayIndexedCollection();
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    public int size() {
        return storage.size();
    }

    public void push(Object value) {
        storage.add(value);
    }

    public Object pop() {
        if (isEmpty()) throw new EmptyStackException();
        Object element = storage.get(storage.size() - 1);
        storage.remove(storage.size() - 1);
        return element;
    }

    public Object peek() {
        if (isEmpty()) throw new EmptyStackException();
        return storage.get(storage.size() - 1);
    }

    public void clear() {
        storage.clear();
    }
}
