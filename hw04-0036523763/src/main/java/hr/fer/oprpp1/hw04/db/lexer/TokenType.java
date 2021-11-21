package hr.fer.oprpp1.hw04.db.lexer;

/**
 * TokenType is an enum of possible {@code Token} types for {@code QueryLexer}.
 */
public enum TokenType {
    OPERATOR,
    FIELD,
    LOGICAL,
    DOUBLE_QUOTES,
    STRING,
    EOF
}
