package hr.fer.oprpp1.custom.collections;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ArrayIndexedCollectionTest {

    @Test
    public void testIllegalInitialCapacity() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArrayIndexedCollection a = new ArrayIndexedCollection(-1);
        });
    }

    @Test
    public void testPassedCollectionIsNull() {
        assertThrows(NullPointerException.class, () -> {
            ArrayIndexedCollection a = new ArrayIndexedCollection(null);
        });
    }

    @Test
    public void testPassedCollectionIsNullInTwoArgumentConstructor() {
        assertThrows(NullPointerException.class, () -> {
            ArrayIndexedCollection a = new ArrayIndexedCollection(null, 16);
        });
    }

    @Test
    public void testIllegalInitialCapacityInTwoArgumentConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            ArrayIndexedCollection a = new ArrayIndexedCollection(new ArrayIndexedCollection(), -1);
        });
    }

    @Test
    public void testAddingNullValue() {
        ArrayIndexedCollection a = new ArrayIndexedCollection();
        assertThrows(NullPointerException.class, () -> {
            a.add(null);
        });
    }

    @Test
    public void testSizeWhenAddingValues() {
        ArrayIndexedCollection a = new ArrayIndexedCollection();
        a.add(1);
        a.add(2);
        assertEquals(2,a.size() );
    }

    @Test
    public void testElementsWhenAddingValues() {
        ArrayIndexedCollection a = new ArrayIndexedCollection();
        a.add(1);
        a.add(2);
        assertEquals(new Object[] {1,2}, a.toArray());
    }

    @Test
    public void testAddingElementWhenCollectionIsFull() {
        ArrayIndexedCollection a = new ArrayIndexedCollection(2);
        a.add(1);
        a.add(2);
        a.add(3);
        assertEquals(new Object[] {1,2,3}, a.toArray());
    }

    @Test
    public void testContainsValue() {
        ArrayIndexedCollection a = new ArrayIndexedCollection(2);
        a.add(1);
        a.add(2);
        assertTrue(a.contains(2));
    }

    @Test
    public void testDoesntContainValue() {
        ArrayIndexedCollection a = new ArrayIndexedCollection(2);
        a.add(1);
        a.add(2);
        assertFalse(a.contains(3));
    }

    @Test
    public void IllegalIndexRemoveMethod() {
        ArrayIndexedCollection a = new ArrayIndexedCollection(2);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            a.remove(-1);
        });
    }
}
