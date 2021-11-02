package hr.fer.oprpp1.custom.collections;

import com.sun.source.util.TaskListener;

import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.abs;

/**
 * Simple map implementation using dispersed addressing.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{
    private static final double MAX_LOAD_FACTOR = 0.75;
    /**
     * Hash table
     */
    private TableEntry<K, V>[] table;
    /**
     * Number of stored entries, not equal to {@code table.length}.
     */
    private int size;

    /**
     *
     */
    private int modificationCount;

    /**
     * Returns an iterator over elements of type {@code TableEntry}.
     *
     * @return {@code SimpleHashtable} Iterator.
     */
    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new SimpleHashtableIterator();
    }

    private class SimpleHashtableIterator implements Iterator<SimpleHashtable.TableEntry<K, V>> {
        private TableEntry<K,V> nextEntry;
        private TableEntry<K,V> lastEntry;
        private int tableRow;
        private int savedModificationCount;

        public SimpleHashtableIterator() {
            tableRow = 0;
            while(table[tableRow] == null && tableRow != table.length - 1) tableRow++;
            nextEntry = table[tableRow];
            savedModificationCount = modificationCount;
        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            if(savedModificationCount != modificationCount) throw new ConcurrentModificationException();
            if (nextEntry == null && tableRow == table.length - 1) {
                return false;
            }
            return true;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public TableEntry<K,V> next() {
            if(savedModificationCount != modificationCount) throw new ConcurrentModificationException();
            if(!hasNext()) throw new NoSuchElementException();
            if(nextEntry == null) {
                tableRow++;
                while(table[tableRow] == null && tableRow != table.length - 1) tableRow++;
                if(table[tableRow] == null) throw new NoSuchElementException();
                nextEntry = table[tableRow].next;
                return lastEntry = table[tableRow];
            }
            lastEntry = nextEntry;
            nextEntry = nextEntry.next;
            return lastEntry;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator. This method can be called only once per call to {@code next}.
         *
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         */
        @Override
        public void remove() {
            if(savedModificationCount != modificationCount) throw new ConcurrentModificationException();
            if (lastEntry == null) throw new IllegalStateException();
            SimpleHashtable.this.remove(lastEntry.key);
            savedModificationCount++;
            lastEntry = null;
        }
    }

    /**
     * Represents one table entry, consists of a key and a value.
     *
     * @param <K> key type
     * @param <V> value type
     */
    public static class TableEntry<K, V> {
        /**
         * key of this entry
         */
        private K key;

        /**
         * value of this entry
         */
        private V value;

        /**
         * pointer to next entry with the same hash
         */
        private TableEntry<K, V> next;

        /**
         * Constructs {@code TableEntry} with passed key and value.
         *
         * @param key   key, non-null
         * @param value value
         * @throws NullPointerException if key is null
         */
        public TableEntry(K key, V value) {
            Objects.requireNonNull(key);
            this.key = key;
            this.value = value;
        }

        /**
         * Gets key of this entry
         *
         * @return key of this entry
         */
        public K getKey() {
            return key;
        }

        /**
         * Gets value of this entry
         *
         * @return value of this entry
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets the value of this entry.
         *
         * @param value new value
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * Creates string representation of this {@code TableEntry} as "key=value".
         *
         * @return string representation of this {@code TableEntry}
         */
        @Override
        public String toString() {
            if (value == null) return key.toString() + "=";
            return key.toString() + "=" + value.toString();
        }

        /**
         * Checks if passed object is equal to this object.
         *
         * @param obj other object
         * @return true if equal, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TableEntry<?, ?> other = (TableEntry<?, ?>) obj;
            return key.equals(other.key) && Objects.equals(value, other.value);
        }
    }

    /**
     * Constructs {@code SimpleHashtable} with default hash table length 16.
     */
    public SimpleHashtable() {
        table = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, 16);
        size = 0;
    }

    /**
     * Constructs {@code SimpleHashtable} with passed capacity.
     *
     * @param capacity capacity of hash table
     */
    public SimpleHashtable(int capacity) {
        if (capacity < 1) throw new IllegalArgumentException("Capacity must be greater than 0");
        table = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, roundToNextPower2(capacity));
        size = 0;
    }


    /**
     * Creates a new entry with passed key and value. Overwrites old entry with the same key if it exists.
     *
     * @param key   entry key
     * @param value entry value
     * @return old value if exists, otherwise null
     * @throws NullPointerException if key is null
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key);
        int slot = calculateTableSlot(key);
        //case first in slot
        if (table[slot] == null) {
            if (calculateLoadFactor() >= MAX_LOAD_FACTOR) {
                reallocateTable();
                slot = calculateTableSlot(key);
            }
            table[slot] = new TableEntry<>(key, value);
            size++;
            modificationCount++;
            return null;
        }
        //case entry with same key already exists
        TableEntry<K, V> entry = table[slot];
        V oldValue;
        while(true){
            if (entry.getKey().equals(key)) {
                oldValue = entry.value;
                entry.setValue(value);
                return oldValue;
            }
            if(entry.next == null) break;

            entry = entry.next;
        }
        boolean needsReallocation = calculateLoadFactor() >= MAX_LOAD_FACTOR; //calculate before adding new element
        entry.next = new TableEntry<>(key, value);
        modificationCount++;
        size++;
        if(needsReallocation) reallocateTable();
        return null;
    }


    /**
     * Gets value of entry with the passed key.
     *
     * @param key entry key
     * @return value of entry with passed key
     */
    public V get(Object key) {
        if (key == null) return null;
        int slot = calculateTableSlot(key);
        TableEntry<K, V> entry = table[slot];

        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    /**
     * Returns number of stored entries.
     *
     * @return number of stored entries.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if this {@code SimpleHashtable} contains entry with passed key.
     *
     * @param key key whose presence in this {@code SimpleHashtable} is being tested
     * @return true if key is present, false otherwise
     */
    public boolean containsKey(Object key) {
        if (key == null) return false;
        int slot = calculateTableSlot(key);
        TableEntry<K, V> entry = table[slot];

        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    /**
     * Checks if this {@code SimpleHashtable} contains entry with passed value.
     *
     * @param value whose presence in this {@code SimpleHashtable} is being tested
     * @return true if value is present, false otherwise
     */
    public boolean containsValue(Object value) {
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                if (Objects.equals(entry.value, value)) return true;
                entry = entry.next;
            }
        }
        return false;
    }

    /**
     * Removes entry with passed key.
     *
     * @param key key of entry which should be removed
     * @return value of removed entry, null if no entry was removed
     */
    public V remove(Object key) {
        if (key == null) return null;
        int slot = calculateTableSlot(key);
        TableEntry<K, V> entry = table[slot];
        TableEntry<K, V> predecessor = null;
        V value;
        modificationCount++;
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                if (predecessor != null) {
                    predecessor.next = entry.next;
                } else {
                    table[slot] = entry.next;
                }
                value = entry.value;
                size--;
                return value;
            }
            predecessor = entry;
            entry = entry.next;
        }
        return null;
    }

    /**
     * Checks if {@code SimpleHashtable} has 0 entries.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns string representation of this {@code SimpleHashtable} in format [key1=value1, key2=value2, key3=value3...]
     *
     * @return string representation of this {@code SimpleHashtable}
     */
    public String toString() {
        TableEntry<K, V>[] array = toArray();
        String outputString = "[";
        for (TableEntry<K, V> entry : array) {
            outputString += entry.toString() + ", ";
        }
        outputString = outputString.substring(0, outputString.length() - 2);
        return outputString + "]";
    }

    /**
     * Creates array which contains entries from this {@code SimpleHashtable} and returns it.
     *
     * @return array of entries from this {@code SimpleHashtable}
     */
    public TableEntry<K, V>[] toArray() {
        int index = 0;
        TableEntry<K, V>[] array = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, size);
        for (TableEntry<K, V> entry : table) {
            while (entry != null) {
                array[index] = entry;
                index++;
                entry = entry.next;
            }
        }

        return array;
    }

    /**
     * Removes all entries from this {@code SimpleHashTable}.
     */
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
        modificationCount++;
    }

    /**
     * Calculates load factor according to expression {@code size/table.length}.
     *
     * @return load factor of this {@code SimpleHashtable}
     */
    private double calculateLoadFactor() {
        return (double) size / (double) table.length;
    }

    /**
     * Doubles table capacity and rearranges elements according to the new table capacity.
     */
    private void reallocateTable() {
        TableEntry<K, V>[] allEntries = toArray();
        this.table = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, table.length * 2);
        size = 0;
        for (TableEntry<K, V> entry : allEntries) {
            put(entry.key, entry.value);
        }
        modificationCount++;
    }

    /**
     * Rounds to the next power of 2 for the passed number.
     * Works by shifting the highest set bit to all lower bits and then adding 1, resulting in only the next highest bit being set.
     * Since each bit represents a power of two, the next power of two is the result.
     * The passed number is first decreased by 1, to ensure that if a power of 2 is passed, the same number will be returned.
     *
     * @param x number to be rounded to the next power of 2
     * @return next power of 2, or the same number if it is already a power of 2
     */
    private int roundToNextPower2(int x) { // x = 10010011
        x--; // x = 10010010 (146)
        x |= x >> 1; // 10010010 | 01001001 = 11011011
        x |= x >> 2; // 11011011 | 00110110 = 11111111
        x |= x >> 4; // ...
        x |= x >> 8; // ...
        x |= x >> 16; // ...
        x++; // 11111111(255) + 1 = 100000000 (256)
        return x;
    }

    /**
     * Calculates slot index from passed objects hashcode, according to expression {@code |k.hashcode()| % table.length}
     *
     * @param k object whose slot index will be calculated
     * @return slot index
     */
    private int calculateTableSlot(Object k) {
        return abs(k.hashCode()) % table.length;
    }


}