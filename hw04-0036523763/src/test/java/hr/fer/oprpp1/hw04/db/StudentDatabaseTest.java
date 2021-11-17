package hr.fer.oprpp1.hw04.db;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



public class StudentDatabaseTest {
    StudentDatabase database;
    List<StudentRecord> list;
    @BeforeEach
    public void setup() {
        list = new ArrayList<>();
        list.add(new StudentRecord("0036523763", "Antolis", "Ana", 5));
        list.add(new StudentRecord("0036523762", "Marcec", "Filip", 5));
        list.add(new StudentRecord("0036523761", "Grguric Mileusnic", "Lovro", 5));

        database = new StudentDatabase(list);
    }

    @Test
    public void testForJMBAG() {
        assertEquals(new StudentRecord("0036523761", "Grguric Mileusnic", "Lovro", 5),
                database.forJMBAG("0036523761"));
    }

    @Test
    public void testForJMBAGShouldReturnNull() {
        assertNull(database.forJMBAG("nepostojecijmbag"));
    }

    @Test
    public void testForNullJMBAG() {
        assertNull(database.forJMBAG(null));
    }

    @Test
    public void testFilterAlwaysFalse() {
        assertTrue(database.filter((record) -> false).isEmpty());
    }

    @Test
    public void testFilterAlwaysTrue() {
        assertEquals(list, database.filter((record) -> true));
    }
}
