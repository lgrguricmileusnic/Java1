package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;

/**
 * Implementation of {@code Tester}, object passes if it's an even integer.
 */
class EvenIntegerTester implements Tester {
    /**
     * Indicates if object is an even integer
     * @param obj argument for which the predicate will be evaluated
     * @return evaluation result
     */
    public boolean test(Object obj) {
        if (!(obj instanceof Integer)) return false;
        Integer i = (Integer) obj;
        return i % 2 == 0;
    }
}