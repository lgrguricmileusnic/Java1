package hr.fer.oprpp1.hw04.db;

/**
 * Strategy interface with method {@code accepts} which for a given {@code StudentRecord} returns true or false,
 * depending on the concrete strategy implementation.
 */
@FunctionalInterface
public interface IFilter {
    /**
     * Returns true if {@code StudentRecord} should be accepted.
     * @param record student record
     * @return true if {@code record} should be accpeted, false otherwise
     */
    boolean accepts(StudentRecord record);
}
