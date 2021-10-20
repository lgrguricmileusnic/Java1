package hr.fer.oprpp1.custom.collections;

/**
 * Functional interface which represents a logic predicate with one argument.
 */
public interface Tester {
    /**
     * Evaluates the predicate and returns the outcome.
     * @param obj argument for which the predicate will be evaluated
     * @return outcome of the evaluation
     */
    boolean test(Object obj);
}
