package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectStackTest {
    ObjectStack testStack;

    @BeforeEach
    public void setup() {
        testStack = new ObjectStack();
    }

    @Test
    public void testPoppingEmptyStack() {
        assertThrows(EmptyStackException.class, () -> testStack.pop());
    }

    @Test
    public void testPeekingEmptyStack() {
        assertThrows(EmptyStackException.class, () -> testStack.peek());
    }

    @Test
    public void testPushingAndPoppingOrder() {
        testStack.push(1);
        testStack.push(2);
        assertEquals(2, testStack.pop());
        assertEquals(1, testStack.pop());
    }

    @Test
    public void testPeekDoesNotPop() {
        testStack.push(1);
        testStack.push(2);
        testStack.peek();
        assertEquals(2, testStack.pop());
    }

    @Test
    public void testSize() {
        testStack.push(1);
        testStack.push(2);
        assertEquals(2, testStack.size());
    }

    @Test
    public void testClearingStack() {
        testStack.push(1);
        testStack.push(2);
        testStack.clear();
        assertEquals(0, testStack.size());
        assertThrows(EmptyStackException.class, () -> testStack.peek());
    }
}
