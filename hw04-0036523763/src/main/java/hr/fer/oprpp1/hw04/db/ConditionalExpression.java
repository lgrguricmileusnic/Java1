package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Conditional expression representation, contains matching {@code IFieldValueGetter}, {@code IComparisonOperator} and the second operand for represented expression.
 */
public class ConditionalExpression {
    /**
     * value getter matching the field from the expression
     */
    private IFieldValueGetter fieldGetter;
    /**
     * comparison operator matching the operator from the expression
     */
    private IComparisonOperator comparisonOperator;
    /**
     * second operand from the expression
     */
    private String operand2;


    /**
     * Constructs {@code ConditionalExpression} instance from passed expression elements
     *
     * @param fieldGetter        value getter matching the field from the expression
     * @param comparisonOperator comparison operator matching the operator from the expression
     * @param operand2           second operand from the expression
     * @throws NullPointerException if any of the passed expression elements is null
     */
    public ConditionalExpression(IFieldValueGetter fieldGetter, IComparisonOperator comparisonOperator, String operand2) {
        this.fieldGetter = Objects.requireNonNull(fieldGetter);
        this.comparisonOperator = Objects.requireNonNull(comparisonOperator);
        this.operand2 = Objects.requireNonNull(operand2);
    }

    /**
     * fieldGetter getter
     *
     * @return fieldValueGetter
     */
    public IFieldValueGetter getFieldGetter() {
        return fieldGetter;
    }

    /**
     * comparisonOperator getter
     *
     * @return comparisonOperator
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    /**
     * Gets second operand.
     *
     * @return second operand
     */
    public String getOperand2() {
        return operand2;
    }
}
