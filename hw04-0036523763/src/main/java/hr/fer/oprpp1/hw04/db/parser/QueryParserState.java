package hr.fer.oprpp1.hw04.db.parser;

public enum QueryParserState {
    OPERATOR_NEXT,
    FIELD_NEXT,
    STRING_NEXT,
    NORMAL_STATE
}
