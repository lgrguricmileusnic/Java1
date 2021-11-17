package hr.fer.oprpp1.hw04.db;

public class ComparisonOperators {
    public static final IComparisonOperator LESS = (v1, v2) -> v2.compareTo(v1) < 0;
    public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v2.compareTo(v1) <= 0;
    public static final IComparisonOperator GREATER = (v1, v2) -> v2.compareTo(v1) > 0;
    public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v2.compareTo(v1) >= 0;
    public static final IComparisonOperator EQUALS = (v1, v2) -> v1.compareTo(v2) == 0;
    public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> v1.compareTo(v2) != 0;
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
            return v1.startsWith(splitV2[0]) && v2.endsWith(splitV2[1]) && v1.length() >= v2.length() - 1;
        }

    };
}
