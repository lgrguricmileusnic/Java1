package hr.fer.oprpp1.hw04.db;

import java.util.Objects;


/**
 * Representation of a database record of a student.
 */
public class StudentRecord {
    /**
     * student's jmbag
     */
    private String jmbag;
    /**
     * student's last name
     */
    private String lastName;
    /**
     * student's first name
     */
    private String firstName;
    /**
     * student's final grade
     */
    private int finalGrade;

    /**
     * Constructs a {@code StudentRecord} instance with passed attributes.
     * @param jmbag student's jmbag
     * @param lastName student's last name
     * @param firstName student's first name
     * @param finalGrade student's final grade
     * @throws NullPointerException if any of the passed params is null
     * @throws IllegalArgumentException if final grade isn't in range [1,5]
     */
    public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
        Objects.requireNonNull(jmbag);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(firstName);
        if(finalGrade > 5 || finalGrade < 1) throw new IllegalArgumentException("Final grade must be an integer in range [1,5]");
        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    /**
     * Gets student JMBAG
     * @return jmbag
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Gets student's last name
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets student's first name
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * Gets student's final grade
     * @return final grade
     */
    public int getFinalGrade() {
        return finalGrade;
    }

    /**
     * Tests if the passed object is equal to this {@code StudentRecord}.
     * Two {@code StudentRecord} instances are equal if their {@code jmbag}s are equal.
     * @param o the object that is being tested
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return jmbag.equals(that.jmbag);
    }

    /**
     * Returns a hash code value for this {@code StudentRecord} instance based of its jmbag.
     * @return hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }
}
