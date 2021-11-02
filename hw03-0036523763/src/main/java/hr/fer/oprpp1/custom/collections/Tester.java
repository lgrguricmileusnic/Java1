package hr.fer.oprpp1.custom.collections;

/**
 * Functional interface which represents a logic predicate with one argument.
 * @param <T> type of elements which will be tested
 */
@FunctionalInterface
public interface Tester<T> {
    /**
     * Evaluates the predicate and returns the outcome.
     * @param obj argument for which the predicate will be evaluated
     * @return outcome of the evaluation
     */
    boolean test(T obj);
}
