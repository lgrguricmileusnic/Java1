package hr.fer.oprpp1.hw04.db;

/**
 * Class containing concrete {@code IComparisonOperator} strategy implementations.
 */
public class ComparisonOperators {
    /**
     * Operator less than <
     */
    public static final IComparisonOperator LESS = (v1, v2) -> v2.compareTo(v1) > 0;
    /**
     * Operator less than or equals <=
     */
    public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v2.compareTo(v1) >= 0;
    /**
     * Operator greater than >
     */
    public static final IComparisonOperator GREATER = (v1, v2) -> v2.compareTo(v1) < 0;
    /**
     * Operator greater than or equals <
     */
    public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v2.compareTo(v1) <= 0;
    /**
     * Operator equals =
     */
    public static final IComparisonOperator EQUALS = (v1, v2) -> v1.compareTo(v2) == 0;
    /**
     * Operator not equal !=
     */
    public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> v1.compareTo(v2) != 0;
    /**
     * Operator LIKE
     * Satisfied if operands are equal or if the first operand matches the pattern specified by the second operand.
     * Only one wildcard character (*) in a pattern is allowed.
     * Wildcard represents any string of characters (including empty strings).
     */
    public static final IComparisonOperator LIKE = (v1, v2) -> {
        int n = (int) v2.chars().filter((c) -> c =='*').count();
        if (n == 0) return v1.equals(v2);
        if (n > 1) throw new IllegalArgumentException("Too many wildcard characters!");
        //if(v1.equals(v2.replaceAll("\\*", ""))) return true;

        if(v2.endsWith("*")) {
            return v1.startsWith(v2.substring(0,v2.length() - 1));
        } else if (v2.startsWith("*")) {
            return v1.endsWith(v2.substring(1));
        } else {
            String[] splitV2 = v2.split("\\*");
            return v1.startsWith(splitV2[0]) && v1.endsWith(splitV2[1]) && v1.length() >= v2.length() - 1;
        }

    };

    /**
     * Gets comparison operator by their query language representation.
     * @param type operator
     * @return {@code IComparisonOperator} for the passed operator.
     */
    public static IComparisonOperator getComparisonOperator(String type) {
        if(type.equals("<")) return LESS;
        if(type.equals(">")) return GREATER;
        if(type.equals("<=")) return LESS_OR_EQUALS;
        if(type.equals(">=")) return GREATER_OR_EQUALS;
        if(type.equals("=")) return EQUALS;
        if(type.equals("!=")) return NOT_EQUALS;
        if(type.equals("LIKE")) return LIKE;
        throw new IllegalArgumentException("Unknown operator type: " + type);
    }
}
