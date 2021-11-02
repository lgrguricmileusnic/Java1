package hr.fer.oprpp1.custom.collections;
import org.junit.jupiter.api.*;



import static org.junit.jupiter.api.Assertions.*;

public class DictionaryTest {
    private Dictionary<Integer, String> dictionary;
    @BeforeEach
    public void setup() {
        dictionary = new Dictionary<>();
    }

    @Test
    public void testKeyIsNull() {
        assertThrows(NullPointerException.class, () -> dictionary.put(null,"A"));
    }

    @Test
    public void testGetEntry() {
        dictionary.put(1,"A");
        dictionary.put(2,"A");
        dictionary.put(5,"B");
        dictionary.put(3,"A");
        assertEquals("B", dictionary.get(5));
    }

    @Test
    public void testGetEmptyDictionary() {
        assertNull(dictionary.get(5));
    }

    @Test
    public void testRemoveEntry() {
        dictionary.put(1,"A");
        dictionary.put(2,"A");
        dictionary.put(5,"B");
        dictionary.put(3,"A");
        assertEquals("B", dictionary.remove(5));
        assertNull(dictionary.get(5));
        assertEquals(3, dictionary.size());
    }


    @Test
    public void testRemoveNonExistentEntry(){
        dictionary.put(2, "a");
        assertNull(dictionary.remove(5));
    }

    @Test
    public void testRemoveFromEmptyDictionary(){
        assertNull(dictionary.remove(5));
    }
    @Test
    public void testSize() {
        dictionary.put(1,"A");
        dictionary.put(2,"A");
        dictionary.put(5,"B");
        dictionary.put(3,"A");
        dictionary.remove(5);
        assertEquals(3, dictionary.size());
    }

    @Test
    public void testOverwrite() {
        dictionary.put(1, "a");
        dictionary.put(2, "b");
        dictionary.put(1, "a");
        assertEquals("b", dictionary.get(2));
    }


}
