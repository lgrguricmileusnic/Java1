package hr.fer.oprpp.hw02.prob1;

/**
 * Represents a lexical unit (token), storing its type and value.
 */
public class Token {
    /**
     * type of this token
     */
    private TokenType type;
    /**
     * value of this token
     */
    private Object value;

    /**
     * Constructs a {@code Token} with passed {@code type} and {@code value}.
     * @param type type of token
     * @param value token value
     */
    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * {@code Token value} getter
     * @return value of this token
     */
    public Object getValue() {
        return value;
    }

    /**
     * {@code Token type} getter
     * @return type of this token
     */
    public TokenType getType() {
        return type;
    }
}
