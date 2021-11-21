package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with one static function used for formatting passed records for emulator output.
 */
public class RecordFormatter {

    /**
     * Formats passed records for emulator output.
     *
     * @param records records which should be formatted
     * @return records formatted by rows as a list of strings
     */
    public static List<String> format(List<StudentRecord> records) {
        int columnWidths[] = new int[2];
        List<String> formattedOutput = new ArrayList<>();
        if (records.size() == 0) {
            formattedOutput.add("Records selected: 0");
            return formattedOutput;
        }
        columnWidths[0] = records.stream().mapToInt((r) -> r.getLastName().length()).max().getAsInt();
        columnWidths[1] = records.stream().mapToInt((r) -> r.getFirstName().length()).max().getAsInt();

        String headerAndFooter = "+============+" + "=".repeat(columnWidths[0] + 2) + "+" + "=".repeat(columnWidths[1] + 2) + "+===+";

        formattedOutput.add(headerAndFooter);
        for (var record : records) {
            formattedOutput.add(
                    String.format("| %1s | %2$-" + columnWidths[0] + "s | %3$-" + columnWidths[1] + "s | %4$d |",
                            record.getJmbag(), record.getLastName(), record.getFirstName(), record.getFinalGrade()));
        }
        formattedOutput.add(headerAndFooter);
        formattedOutput.add("Records selected: " + records.size());
        return formattedOutput;
    }
}
