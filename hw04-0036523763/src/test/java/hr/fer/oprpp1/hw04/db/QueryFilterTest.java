package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueryFilterTest {

    @Test
    void testNull() {
        assertThrows(NullPointerException.class, () -> new QueryFilter(null));
    }

    @Test
    public void testAccepts() {
        QueryParser parser = new QueryParser("firstName=\"Ivan\" AND lastName LIKE \"AA*AA\" AND jmbag=\"0\"");
        QueryFilter filter = new QueryFilter(parser.getQuery());
        assertTrue(filter.accepts(new StudentRecord("0", "AAAA", "Ivan", 2)));
        assertFalse(filter.accepts(new StudentRecord("0", "AAA", "Ivan", 2)));
        assertFalse(filter.accepts(new StudentRecord("0", "AAAA", "Ian", 2)));
        assertFalse(filter.accepts(new StudentRecord("1", "AAAA", "Ivan", 2)));
    }
}