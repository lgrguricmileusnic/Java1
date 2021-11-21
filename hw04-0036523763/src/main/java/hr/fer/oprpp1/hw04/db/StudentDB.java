package hr.fer.oprpp1.hw04.db;

import hr.fer.oprpp1.hw04.db.lexer.LexerException;
import hr.fer.oprpp1.hw04.db.parser.QueryParserException;

import java.awt.desktop.SystemEventListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Simple database emulator.
 * Supports queries formulated from one or more expressions formatted as <field><operator><string literal>.
 * Expressions should be joined with logical operator AND separated by spaces.
 * Multiple spaces are ignored, fields are case-sensitive, operator AND is case-insensitive.
 * <p>
 * Supported operators: <, <=, >, >=, =, !=, LIKE
 * Supported fields: firstName,lastName,jmbag
 */
public class StudentDB {
    /**
     * Emulator main function
     *
     * @param args emulator takes no arguments
     */
    public static void main(String[] args) {
        List<String> rows = null;
        String[] splitRow;
        List<StudentRecord> recordList;

        try {
            rows = Files.readAllLines(Paths.get(StudentDatabase.class.getClassLoader().getResource("database.txt").toURI()));

        } catch (IOException | URISyntaxException e) {
            System.out.println("Can't read database file");
            return;
        }

        recordList = new ArrayList<>();
        for (String row : rows) {
            splitRow = row.split("\t");
            recordList.add(new StudentRecord(splitRow[0], splitRow[1], splitRow[2], Integer.valueOf(splitRow[3])));
        }

        StudentDatabase database = new StudentDatabase(recordList);

        String input;
        List<StudentRecord> records;
        List<String> output;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            input = scanner.nextLine().strip();
            if (input.startsWith("exit")) {
                System.out.println("Goodbye!");
                return;
            }
            if (!input.startsWith("query")) {
                System.out.println("Unknown command: " + input.split(" ")[0]);
                continue;
            }
            input = input.replaceFirst("query", "");
            records = queryDatabase(input, database);
            if (records == null) continue;
            output = RecordFormatter.format(records);
            output.forEach(System.out::println);
        }

    }

    /**
     * Parses inputted query, queries database and returns result as a list of {@code StudentRecord} objects.
     *
     * @param input    query
     * @param database database which should be queried
     * @return result of query
     */
    public static List<StudentRecord> queryDatabase(String input, StudentDatabase database) {
        QueryParser parser;
        try {
            parser = new QueryParser(input);
        } catch (QueryParserException e) {
            System.out.println(e.getMessage());
            return null;
        }
        if (parser.isDirectQuery()) {
            ArrayList<StudentRecord> oneRecord = new ArrayList<>();
            StudentRecord record = database.forJMBAG(parser.getQueriedJMBAG());
            if (record != null) oneRecord.add(record);
            return oneRecord;
        } else {
            return database.filter(new QueryFilter(parser.getQuery()));
        }
    }
}
