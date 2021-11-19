package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldValueGettersTest {


    @Test
    public void testFirstName() {
        assertEquals("Lovro", FieldValueGetters.FIRST_NAME.get(new StudentRecord("0036523763", "Grguric", "Lovro", 1)));
    }
    @Test
    public void testLastName() {
        assertEquals("Grguric", FieldValueGetters.LAST_NAME.get(new StudentRecord("0036523763", "Grguric", "Lovro", 1)));
    }
    @Test
    public void testJmbag() {
        assertEquals("0036523763", FieldValueGetters.JMBAG.get(new StudentRecord("0036523763", "Grguric", "Lovro", 1)));
    }

    @Test
    public void testGetFieldValueGetterByName (){
        assertEquals(FieldValueGetters.FIRST_NAME, FieldValueGetters.getFieldValueGetter("firstName"));
        assertEquals(FieldValueGetters.LAST_NAME, FieldValueGetters.getFieldValueGetter("lastName"));
        assertEquals(FieldValueGetters.JMBAG, FieldValueGetters.getFieldValueGetter("jmbag"));
    }
}