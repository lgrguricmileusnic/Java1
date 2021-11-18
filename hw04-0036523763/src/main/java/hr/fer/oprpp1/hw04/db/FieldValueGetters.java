package hr.fer.oprpp1.hw04.db;
/**
 * Class containing concrete {@code IComparisonOperator} strategy implementations.
 */
public class FieldValueGetters {
    /**
     * {@code IFieldValueGetter} strategy implementation for field firstName
     */
    public static final IFieldValueGetter FIRST_NAME = (record -> record.getFirstName());
    /**
     * {@code IFieldValueGetter} strategy implementation for field lastName
     */
    public static final IFieldValueGetter LAST_NAME = (record -> record.getLastName());
    /**
     * {@code IFieldValueGetter} strategy implementation for field jmbag
     */
    public static final IFieldValueGetter JMBAG = (record -> record.getJmbag());

    public static IFieldValueGetter getFieldValueGetter(String type) {
        if(type.equals("firstName")) return FIRST_NAME;
        if(type.equals("lastName")) return LAST_NAME;
        if(type.equals("jmbag")) return JMBAG;
        throw new IllegalArgumentException("Unknown IFieldValueGetter");
    }
}
