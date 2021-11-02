package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleHashtableTest {
    SimpleHashtable<String, Integer> simpleHashtable;

    @BeforeEach
    public void setup() {
        simpleHashtable = new SimpleHashtable<>(2);
    }

    @Test
    public void testKeyIsNull() {
        assertThrows(NullPointerException.class, () -> simpleHashtable.put(null, 1));
    }


    @Test
    public void testInvalidCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<String, Integer>(0));
    }


    @Test
    public void testOverwrite() {
        simpleHashtable.put("Davorin", 3);
        simpleHashtable.put("Kemal", 2);
        simpleHashtable.put("Ivana", 2);
        assertEquals(2, simpleHashtable.put("Kemal", 5));
        assertEquals(simpleHashtable.get("Kemal"), 5);
    }

    @Test
    public void testGetNull() {
        assertDoesNotThrow(() -> simpleHashtable.get(null));
    }

    @Test
    public void testContainsKey() {
        simpleHashtable.put("Davorin", 3);
        simpleHashtable.put("Kemal", 2);
        simpleHashtable.put("Ivana", 2);
        assertTrue(simpleHashtable.containsKey("Kemal"));
        assertFalse(simpleHashtable.containsKey("Toma"));
    }

    @Test
    public void testContainsKeyNull() {
        assertFalse(simpleHashtable.containsKey(null));
    }

    @Test
    public void testContainsValue() {
        simpleHashtable.put("Davorin", 3);
        simpleHashtable.put("Kemal", null);
        simpleHashtable.put("Ivana", 1);
        assertTrue(simpleHashtable.containsValue(1));
        assertFalse(simpleHashtable.containsValue(0));

    }

    @Test
    public void testContainsValueNull() {
        simpleHashtable.put("Kemal", null);
        assertTrue(simpleHashtable.containsValue(null));
    }

    @Test
    public void testRemove() {
        simpleHashtable.put("Davorin", 3);
        simpleHashtable.put("Kemal", null);
        simpleHashtable.put("Ivana", 1);
        simpleHashtable.put("Toma", 2);
        simpleHashtable.put("Ante", 2);
        simpleHashtable.put("Igor", 3);
        assertEquals(3, simpleHashtable.remove("Davorin"));
        assertNull(simpleHashtable.get("Davorin"));
        assertEquals(5, simpleHashtable.size());
        assertNull(simpleHashtable.remove("Toza"));
        assertEquals(5, simpleHashtable.size());
    }

    @Test
    public void testToArray() {
        simpleHashtable.put("Davorin", 3);
        simpleHashtable.put("Kemal", null);
        simpleHashtable.put("Ivana", 1);
        TableEntry<String, Integer>[] testArray = (TableEntry<String, Integer>[]) Array.newInstance(TableEntry.class, 3);
        testArray[0] = new TableEntry<>("Davorin", 3);
        testArray[1] = new TableEntry<>("Kemal", null);
        testArray[2] = new TableEntry<>("Ivana", 1);
        TableEntry<String, Integer>[] actualArray = simpleHashtable.toArray();
        for (var entry: actualArray) {
            boolean contains = false;
            for (int i = 0; i < testArray.length; i++) {
                if (Objects.equals(entry, testArray[i])) {
                    contains = true;
                    testArray[i] = null;
                }
            }
            if(!contains) fail();
        }
        assertEquals(3, actualArray.length);
    }

    @Test
    public void testClear() {
        simpleHashtable.put("Davorin", 3);
        simpleHashtable.put("Kemal", null);
        simpleHashtable.put("Ivana", 1);
        simpleHashtable.clear();
        assertEquals(0, simpleHashtable.size());
        assertEquals(0, simpleHashtable.toArray().length);
    }

    @Test
    public void testHasNextEmptyTableReturnsFalse() {
        Iterator<TableEntry<String,Integer>> it = simpleHashtable.iterator();
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
        assertThrows(IllegalStateException.class, it::remove);
    }

    @Test
    public void testNextEmptyTableThrowsNSEE() {
        Iterator<TableEntry<String,Integer>> it = simpleHashtable.iterator();
        assertThrows(NoSuchElementException.class, it::next);

    }

    @Test
    public void testRemoveEmptyTableThrowsISE() {
        Iterator<TableEntry<String,Integer>> it = simpleHashtable.iterator();
        assertThrows(IllegalStateException.class, it::remove);
    }

    @Test
    public void testHasNextMultipleTimes() {
        simpleHashtable.put("Toma", 1);
        Iterator<TableEntry<String,Integer>> it = simpleHashtable.iterator();
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
    }

    @Test
    public void testRemoveTwiceThrowsISE() {
        simpleHashtable.put("Toma", 1);
        Iterator<TableEntry<String,Integer>> it = simpleHashtable.iterator();
        it.next();
        it.remove();
        assertThrows(IllegalStateException.class,it::remove);
    }

    @Test
    public void testHasNextTwiceAfterNext() {
        simpleHashtable.put("Toma", 1);
        Iterator<TableEntry<String,Integer>> it = simpleHashtable.iterator();
        it.next();
        assertFalse(it::hasNext);
        assertFalse(it::hasNext);
    }

    @Test
    public void testRemoveAllItemsViaIterator() {
        simpleHashtable.put("Davorin", 3);
        simpleHashtable.put("Kemal", null);
        simpleHashtable.put("Toma", 1);
        Iterator<TableEntry<String,Integer>> it = simpleHashtable.iterator();
        while(it.hasNext()){
            it.next();
            it.remove();
        }
        assertEquals(0, simpleHashtable.size());
        assertEquals(0, simpleHashtable.toArray().length);
        assertThrows(IllegalStateException.class ,it::remove);
    }

    @Test
    public void testMethodsThrowConcurrentModificationExceptionAfterPut(){
        simpleHashtable.put("Davorin", 3);
        simpleHashtable.put("Kemal", null);
        simpleHashtable.put("Toma", 1);
        Iterator<TableEntry<String,Integer>> it = simpleHashtable.iterator();
        simpleHashtable.put("Zorica", 4);
        assertThrows(ConcurrentModificationException.class, it::next);
        assertThrows(ConcurrentModificationException.class, it::remove);
        assertThrows(ConcurrentModificationException.class, it::hasNext);
    }

    @Test
    public void testMethodsDoNotThrowConcurrentModificationExceptionAfterOverwrite(){
        simpleHashtable.put("Davorin", 3);
        simpleHashtable.put("Kemal", null);
        simpleHashtable.put("Toma", 1);
        Iterator<TableEntry<String,Integer>> it = simpleHashtable.iterator();
        simpleHashtable.put("Toma", 4);
        assertDoesNotThrow(it::next);
        assertDoesNotThrow(it::hasNext);
        assertDoesNotThrow(it::remove);
    }
}
