package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of a student database.
 */
public class StudentDatabase {
    /**
     * internal list used for stording records
     */
    List<StudentRecord> studentRecordList;
    /**
     * index used for accessing student records by JMBAG in O(1) time
     */
    Map<String,StudentRecord> studentRecordMap;

    /**
     * Constructs a {@code StudentDatabase} instance from supplied student record list.
     * Checks for duplicate JMBAGs and invalid final grades
     * @param studentRecordList list with records with which the database will be filled
     * @throws IllegalArgumentException if passed list contains duplicate JMBAGs or invalid grades
     */
    public StudentDatabase(List<StudentRecord> studentRecordList) {
        studentRecordMap = new HashMap<>();
        for (StudentRecord record: studentRecordList) {
            if(studentRecordMap.containsKey(record.getJmbag()))
                throw new IllegalArgumentException("List contains records with duplicate JMBAGs");
            if(record.getFinalGrade() < 1 || record.getFinalGrade() > 5)
                throw new IllegalArgumentException("Record with JMBAG " + record.getJmbag() + " contains an invalid grade");
            studentRecordMap.put(record.getJmbag(), record);
        }
        this.studentRecordList = studentRecordList;
    }

    /**
     * Obtains requested record in O(1), if record doesn't exist returns null.
     * @param jmbag JMBAG of requested record
     * @return requested record or null if record doesn't exist
     */
    public StudentRecord forJMBAG(String jmbag){
        return studentRecordMap.get(jmbag);
    }

    /**
     * Loops through all student records in this database and returns a list of acceptable records
     * defined by the passed {@code filter}
     * @param filter filter which should be used
     * @return an instance of {@code List<StudentRecord>} filled with accepted student records
     */
    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> list = new ArrayList<>();
        for (StudentRecord record: studentRecordList) {
            if (filter.accepts(record)) list.add(record);
        }
        return list;
    }
}
