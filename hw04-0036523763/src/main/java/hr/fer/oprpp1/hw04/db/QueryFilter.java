package hr.fer.oprpp1.hw04.db;

import java.util.List;
import java.util.Objects;

/**
 * Concrete implementation of IFilter strategy, method accept returns true if passed record satisfies all conditional
 * expressions from list passed to the constructor.
 */
public class QueryFilter implements IFilter {
    /**
     * list of expressions which need to be satisfied
     */
    private List<ConditionalExpression> query;

    /**
     * Constructs {@code QueryFilter} instance with passed {@code List} of conditional expressions which will be used
     * for filtering
     *
     * @param query {@code List} of conditional expressions which form a query
     */
    public QueryFilter(List<ConditionalExpression> query) {
        this.query = Objects.requireNonNull(query);
    }

    /**
     * Returns true if passed record satisfies all conditional expressions from {@code query}
     *
     * @param record student record
     * @return true if passed record satisfies all conditional expressions from {@code query}, false otherwise
     */
    @Override
    public boolean accepts(StudentRecord record) {
        for (var expression : query) {
            if (!expression.getComparisonOperator().satisfied(expression.getFieldGetter().get(record), expression.getOperand2()))
                return false;
        }
        return true;
    }
}
