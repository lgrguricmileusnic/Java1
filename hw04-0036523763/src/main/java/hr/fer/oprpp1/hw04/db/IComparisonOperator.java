package hr.fer.oprpp1.hw04.db;


/**
 * Comparison operator strategy interface, has method {@code satisfied} which returns true
 * if comparison predicate is true for passed values.
 */
@FunctionalInterface
public interface IComparisonOperator {
    /**
     * Compares value based on concrete strategy implementation and returns true or false accordingly.
     * @param value1 first operand
     * @param value2 second operand
     * @return true if comparison predicate is true
     */
    boolean satisfied(String value1, String value2);
}
