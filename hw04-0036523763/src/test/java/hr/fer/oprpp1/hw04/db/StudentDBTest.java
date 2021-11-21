package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDBTest {
    StudentDatabase database;


    @Test
    void testQueryDatabase() {
        database = readDatabaseFromResources();
        assertEquals(0, StudentDB.queryDatabase("jmbag=\"8\"", database).size());
    }

    @Test
    void testQueryDatabaseAllRecords() {
        database = readDatabaseFromResources();
        assertEquals(63, StudentDB.queryDatabase("jmbag LIKE \"*\"", database).size());
    }

    @Test
    void testQueryDatabaseSomeRecords() {
        database = readDatabaseFromResources();
        assertEquals(6, StudentDB.queryDatabase("jmbag < \"0000000007\"", database).size());
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