package hr.fer.oprpp1.hw04.db;

/**
 * FieldValueGetter strategy interface, has method {@code get} which returns the value of a specific field from the passed {@code StudentRecord}.
 */
@FunctionalInterface
public interface IFieldValueGetter {
    /**
     * Gets the value of a specific field from the passed record.
     *
     * @param record record from which the value will be extracted
     * @return field value
     */
    public String get(StudentRecord record);
}
