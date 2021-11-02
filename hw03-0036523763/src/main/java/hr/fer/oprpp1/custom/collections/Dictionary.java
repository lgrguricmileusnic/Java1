package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * {@code Dictionary} is an adaptor class adapting {@code ArrayIndexedCollection} to provide its users with methods natural for a dictionary.
 * @param <K> key type
 * @param <V> value type
 */
public class Dictionary<K, V> {
    private ArrayIndexedCollection<KeyValuePair<K,V>> collection;

    /**
     * {@code KeyValuePair} is an inner class representing a pair consisting of a key and its value.
     */
    private static class KeyValuePair<K,V> {
        /**
         * value of key
         */
        private K key;
        /**
         * value of value
         */
        private V value;

        /**
         * Constructs {@code KeyValuePair} with provided key and value.
         * @param key key
         * @param value value
         * @throws NullPointerException if passed key is null
         */
        public KeyValuePair(K key, V value) {
            Objects.requireNonNull(key);
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || (getClass() != o.getClass())) return false;
            KeyValuePair<?, ?> that = (KeyValuePair<?, ?>) o;
            return key.equals(that.key) && Objects.equals(value, that.value);
        }

    }

    /**
     * Default constructor.
     */
    public Dictionary() {
        collection = new ArrayIndexedCollection<>();
    }
    /**
     * Checks if stack is empty.
     *
     * @return true if stack contains no {@code KeyValuePair} objects and false otherwise
     */
    public boolean isEmpty(){
        return size() == 0;
    };

    /**
     * Returns number of dictionary entries.
     * @return number of dictionary entries
     */
    public int size() {
        return collection.size();
    };

    /**
     * Removes all dictionary entries.
     */
    public void clear() {
        collection.clear();
    };

    /**
     * Puts key-value pair in dictionary. Overwrites old value if there is already an existing entry for passed key.
     * @param key key
     * @param value value for this key
     * @return old value if exists, null otherwise
     * @throws NullPointerException if passed key is null
     */
    public V put(K key, V value){
        Objects.requireNonNull(key);
        KeyValuePair<K,V> newKeyValuePair = new KeyValuePair(key, value);
        KeyValuePair<K,V> oldKeyValuePair = getKeyValuePair(key);
        if(oldKeyValuePair == null) {
            collection.add(newKeyValuePair);
            return null;
        }
        collection.remove(oldKeyValuePair);
        collection.add(newKeyValuePair);
        return oldKeyValuePair.value;
    };

    /**
     * Gets value for passed key.
     * @param key key
     * @return value of entry for passed key.
     * @throws NullPointerException if passed key is null
     */
    public V get(Object key) {
        Objects.requireNonNull(key);
        KeyValuePair<K,V> keyValuePair = getKeyValuePair(key);
        if(keyValuePair == null) return null;
        return keyValuePair.value;
    };

    /**
     * Removes entry from dictionary with passed key.
     * @param key key of the entry which should be removed
     * @return value of removed key, null if key doesn't exist
     * @throws NullPointerException if passed key is null
     */
    public V remove(K key) {
        Objects.requireNonNull(key);
        KeyValuePair<K,V> keyValuePair = getKeyValuePair(key);
        if(keyValuePair == null) return null;
        collection.remove(keyValuePair);
        return keyValuePair.value;
    };

    /**
     * Gets {@code KeyValuePair} for passed key
     * @param key key of the entry which should be returned
     * @return {@code KeyValuePair} if key exists in this dictionary, null otherwise
     * @throws NullPointerException if passed key is null
     */
    private KeyValuePair<K,V> getKeyValuePair(Object key) {
        Objects.requireNonNull(key);
        ElementsGetter<KeyValuePair<K,V>> elementsGetter = collection.createElementsGetter();
        KeyValuePair<K,V> keyValuePair;
        while(elementsGetter.hasNextElement()){
            keyValuePair = elementsGetter.getNextElement();
            if (keyValuePair.key.equals(key)) {
                return keyValuePair;
            }
        }
        return null;
    }

}
