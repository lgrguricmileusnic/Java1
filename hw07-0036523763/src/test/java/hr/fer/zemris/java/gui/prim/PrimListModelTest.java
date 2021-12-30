package hr.fer.zemris.java.gui.prim;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class PrimListModelTest {
    private PrimListModel model;
    @BeforeEach
    void setUp() {
        model = new PrimListModel();
    }

    @Test
    void next() {
        model.next();
        model.next();
        model.next();
        model.next();
        model.next();
        assertEquals(6, model.getSize());
        assertEquals(11, model.getElementAt(5));
    }

    @Test
    void getSizeNewModel() {
        assertEquals(1, model.getSize());
    }

    @Test
    void getSizeTest() {
        model.next();
        model.next();
        model.next();
        assertEquals(4, model.getSize());
    }
    @Test
    void getElementAtIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> model.getElementAt(6));
    }

    @Test
    void getElementAtExample() {
        model.next();
        model.next();
        model.next();
        assertEquals(5, model.getElementAt(3));
    }
}