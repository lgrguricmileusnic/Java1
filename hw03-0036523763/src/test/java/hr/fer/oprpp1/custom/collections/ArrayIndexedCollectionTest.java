package hr.fer.oprpp1.custom.collections;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ArrayIndexedCollectionTest {
    private ArrayIndexedCollection<Integer> testCollection;

    @BeforeEach
    public void setup() {
        testCollection = new ArrayIndexedCollection<>();
        for (int i = 0; i < 3; i++) {
            testCollection.add(i);
        }
    }

    @Test
    public void testIllegalInitialCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection<>(-1));
    }

    @Test
    public void testPassedCollectionIsNull() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection<>(null));
    }

    @Test
    public void testPassedCollectionIsNullInTwoArgumentConstructor() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection<>(null, 16));
    }

    @Test
    public void testIllegalInitialCapacityInTwoArgumentConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection<>(testCollection, -1));
        assertEquals(testCollection, new ArrayIndexedCollection<>(testCollection));
    }

    @Test
    public void testCreatingCollectionFromCollection() {
        assertEquals(testCollection, new ArrayIndexedCollection<>(testCollection));
    }

    @Test
    public void testToArray() {
        assertArrayEquals(testCollection.toArray(), new Integer[]{0, 1, 2});
    }

    @Test
    public void testToArrayForEmptyCollection() {
        Object[] array = (new ArrayIndexedCollection<Integer>()).toArray();
        assertArrayEquals(new Object[]{}, array);
    }

    @Test
    public void testAddingNullValue() {
        assertThrows(NullPointerException.class, () -> testCollection.add(null));
    }

    @Test
    public void testSizeShouldChangeWhenAddingElements() {
        testCollection.add(5);
        testCollection.add(2);
        assertEquals(5, testCollection.size());
    }

    @Test
    public void testAddingValues() {
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
        a.add(0);
        a.add(1);
        a.add(2);
        assertEquals(testCollection, a);
    }

    @Test
    public void testAddingElementWhenCollectionIsFull() {
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>(2);
        a.addAll(testCollection);
        assertEquals(testCollection, a);
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
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>(5);
        a.addAll(testCollection);
        assertTrue(a.remove(Integer.valueOf(2)));
        assertArrayEquals(new Object[]{0, 1}, a.toArray());
    }

    @Test
    public void testShouldRemoveOneOfMultipleEqualElements() {
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>(5);
        a.addAll(testCollection);
        a.add(2);
        a.add(3);
        testCollection.add(3);
        assertTrue(a.remove(Integer.valueOf(2)));
        assertEquals(testCollection, a);

    }

    @Test
    public void testRemovingNonExistingElement() {
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>(4);
        a.addAll(testCollection);
        assertFalse(a.remove(Integer.valueOf(3)));
        assertEquals(testCollection, a);
    }

    @Test
    public void testRemovingFromEmptyArray() {
        testCollection.clear();
        assertFalse(testCollection.remove(Integer.valueOf(3)));
        assertEquals(testCollection, new ArrayIndexedCollection<>());
    }


    @Test
    public void testIndexLessThanZeroRemoveMethod() {
        assertThrows(IndexOutOfBoundsException.class, () -> testCollection.remove(-1));
    }

    @Test
    public void testIndexCrossedUpperBoundRemoveMethod() {
        assertThrows(IndexOutOfBoundsException.class, () -> testCollection.remove(100));
    }

    @Test
    public void testClear() {
        testCollection.clear();
        assertEquals(testCollection, new ArrayIndexedCollection<Integer>());
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
        assertThrows(IndexOutOfBoundsException.class, () -> testCollection.get(-1));
    }

    @Test
    public void testIndexCrossedUpperBoundGetMethod() {
        assertThrows(IndexOutOfBoundsException.class, () -> testCollection.get(100));
    }

    @Test
    public void testIndexLessThanZeroInsertMethod() {
        assertThrows(IndexOutOfBoundsException.class, () -> testCollection.insert(1, -1));
    }

    @Test
    public void testIndexCrossedUpperBoundInsertMethod() {
        assertThrows(IndexOutOfBoundsException.class, () -> testCollection.insert(1, 100));
    }

    @Test
    public void testInsertAtLastIndex() {
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>(testCollection);
        testCollection.add(-1);
        a.insert(-1, a.size());
        assertEquals(testCollection, a);

    }

    @Test
    public void testInsertAtArrayStart() {
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>(testCollection);
        a.remove(0);
        a.insert(0, 0);
        assertEquals(testCollection, a);

    }

    @Test
    public void testInsertElementInMiddleOfArray() {
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>();
        a.add(0);
        a.add(2);
        a.add(3);
        testCollection.add(3);
        a.insert(1, 1);
        assertEquals(testCollection, a);
    }

    @Test
    public void testInsertIntoFullArray() {
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>(2);
        a.add(1);
        a.add(2);
        assertDoesNotThrow(() -> a.insert(3, 1));
    }

    @Test
    public void testInsertIntoEmptyArray() {
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>(3);
        assertDoesNotThrow(() -> a.insert(3, 0));
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

    @Test
    public void testAddAllSatisfyingAlwaysFalse() {
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>(3);
        a.addAllSatisfying(testCollection, (x) -> x < 0 );
        assertTrue(a.isEmpty());
    }

    @Test
    public void testAddAllSatisfyingAlwaysTrue() {
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>(3);
        a.addAllSatisfying(testCollection, (x) -> x >= 0 );
        assertEquals(3, a.size());
    }

    @Test
    public void testAddAllSatisfyingSomeTrue() {
        ArrayIndexedCollection<Integer> a = new ArrayIndexedCollection<>(3);
        a.addAllSatisfying(testCollection, (x) -> x > 0 );
        assertEquals(2, a.size());
    }
}
