package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecordFormatterTest {
    private static List<StudentRecord> records;

    @BeforeAll
    public static void setup() {
        records = new ArrayList<>();
    }

    @Test
    public void testEmptyList() {
        assertEquals("Records selected: 0", RecordFormatter.format(records).get(0));
    }

    @Test
    void testSampleList() {
        StudentDatabase database = readDatabaseFromResources();
        records.addAll(StudentDB.queryDatabase("jmbag < \"0000000005\"", database));
        String[] strings = new String[]{"+============+===========+========+===+",
                                        "| 0000000001 | Akšamović | Marin  | 2 |",
                                        "| 0000000002 | Bakamović | Petra  | 3 |",
                                        "| 0000000003 | Bosnić    | Andrea | 4 |",
                                        "| 0000000004 | Božić     | Marin  | 5 |",
                                        "+============+===========+========+===+",
                                        "Records selected: 4"};
        assertArrayEquals(strings, RecordFormatter.format(records).toArray());
    }

    private StudentDatabase readDatabaseFromResources() {
        List<String> rows = null;
        String[] splitRow;
        List<StudentRecord> recordList;

        try {
            rows = Files.readAllLines(Paths.get(this.getClass().getClassLoader().getResource("database.txt").toURI()));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Can't read database file");
            return null;
        }

        recordList = new ArrayList<>();
        for (String row : rows) {
            splitRow = row.split("\t");
            recordList.add(new StudentRecord(splitRow[0], splitRow[1], splitRow[2], Integer.valueOf(splitRow[3])));
        }
        return new StudentDatabase(recordList);
    }
}