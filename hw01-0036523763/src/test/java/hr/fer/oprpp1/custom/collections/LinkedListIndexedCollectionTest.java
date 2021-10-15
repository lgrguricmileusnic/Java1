package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListIndexedCollectionTest {
    LinkedListIndexedCollection testCollection;
    @BeforeEach
    public void setup() {
        testCollection = new LinkedListIndexedCollection();
        for (int i = 0; i < 3; i++) {
            testCollection.add(Integer.valueOf(i));
        }
    }


    @Test
    public void testPassedCollectionIsNull() {
        assertThrows(NullPointerException.class, () -> {
            LinkedListIndexedCollection a = new LinkedListIndexedCollection(null);
        });
    }

    @Test
    public void testCreatingCollectionFromCollection() {
        ArrayIndexedCollection a = new ArrayIndexedCollection();
        a.add(0);
        a.add(1);
        a.add(2);
        LinkedListIndexedCollection l = new LinkedListIndexedCollection(a);
        assertEquals(testCollection, new LinkedListIndexedCollection(a));
    }

    @Test
    public void testToArray() {
        Object[] array = testCollection.toArray();
        assertArrayEquals(new Object[]{0, 1, 2}, array);
    }

    @Test
    public void testAddingNullValue() {
        assertThrows(NullPointerException.class, () -> {
            testCollection.add(null);
        });
    }

    @Test
    public void testSizeWhenAddingValues() {
        testCollection.add(5);
        testCollection.add(2);
        assertEquals(5, testCollection.size());
    }

    @Test
    public void testAddingValues() {
        LinkedListIndexedCollection a = new LinkedListIndexedCollection();
        a.add(1);
        a.add(2);
        assertArrayEquals(new Object[]{1, 2}, a.toArray());
    }

    @Test
    public void testContainsValue() {
        assertTrue(testCollection.contains(2));
    }

    @Test
    public void testDoesNotContainValue() {
        assertFalse(testCollection.contains(7));
    }

    @Test
    public void testRemoveContainedElement() {
        LinkedListIndexedCollection a = new LinkedListIndexedCollection();
        a.addAll(testCollection);
        assertTrue(a.remove(Integer.valueOf(2)));
        assertArrayEquals(new Object[]{0, 1}, a.toArray());
    }

    @Test
    public void testShouldRemoveOneOfMultipleSameElements() {
        LinkedListIndexedCollection a = new LinkedListIndexedCollection();
        a.addAll(testCollection);
        a.add(2);
        a.add(3);
        testCollection.add(3);
        assertTrue(a.remove(Integer.valueOf(2)));
        assertEquals(testCollection, a);

    }

    @Test
    public void testRemovingNonExistingElement() {
        LinkedListIndexedCollection a = new LinkedListIndexedCollection();
        a.addAll(testCollection);
        assertFalse(a.remove(Integer.valueOf(3)));
        assertEquals(testCollection, a);
    }

    @Test
    public void testRemovingFromEmptyArray() {
        testCollection.clear();
        assertFalse(testCollection.remove(Integer.valueOf(3)));
        assertEquals(testCollection, new LinkedListIndexedCollection());
    }


    @Test
    public void testIndexLessThanZeroRemoveMethod() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testCollection.remove(-1);
        });
    }

    @Test
    public void testIndexCrossedUpperBoundRemoveMethod() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testCollection.remove(100);
        });
    }

    @Test
    public void testClear() {
        testCollection.clear();
        assertEquals(testCollection, new LinkedListIndexedCollection());
    }

    @Test
    public void testGetElement() {
        Object[] testArray = testCollection.toArray();
        for (int i = 0; i < testCollection.size(); i++) {
            assertEquals(testArray[i], testCollection.get(i));
        }
    }

    @Test
    public void testIndexLessThanZeroGetMethod() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testCollection.get(-1);
        });
    }

    @Test
    public void testIndexCrossedUpperBoundGetMethod() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testCollection.get(100);
        });
    }

    @Test
    public void testIndexLessThanZeroInsertMethod() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testCollection.insert(1, -1);
        });
    }

    @Test
    public void testIndexCrossedUpperBoundInsertMethod() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testCollection.insert(1, 100);
        });
    }

    @Test
    public void testInsertAtLastIndex() {
        LinkedListIndexedCollection a = new LinkedListIndexedCollection(testCollection);
        testCollection.add(-1);
        a.insert(-1, a.size());
        assertEquals(testCollection, a);

    }

    @Test
    public void testInsertAtArrayStart() {
        LinkedListIndexedCollection a = new LinkedListIndexedCollection(testCollection);
        a.remove(0);
        a.insert(0, 0);
        assertEquals(testCollection, a);

    }

    @Test
    public void testInsertElementInMiddleOfArray() {
        LinkedListIndexedCollection a = new LinkedListIndexedCollection();
        a.add(0);
        a.add(2);
        a.add(3);
        testCollection.add(3);
        a.insert(1, 1);
        assertEquals(testCollection, a);
    }

    @Test
    public void testInsertIntoEmptyArray() {
        LinkedListIndexedCollection a = new LinkedListIndexedCollection();
        assertDoesNotThrow(() -> {
            a.insert(3, 0);
        });
    }

    @Test
    public void testIndexOfNonExistingElement() {
        assertEquals(-1, testCollection.indexOf(10));
    }

    @Test
    public void testIndexOfExistingElement() {
        assertEquals(1, testCollection.indexOf(1));
    }

    @Test
    public void testIndexOfNull() {
        assertEquals(-1, testCollection.indexOf(null));
    }
}
