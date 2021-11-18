package hr.fer.oprpp1.hw04.db.parser;

/**
 * Unchecked exception, thrown to indicate that an invalid token was passed to the {@code SmartScriptParser}.
 */
public class QueryParserException extends RuntimeException {
    /**
     * Default constructor
     */
    public QueryParserException() {
        super();
    }

    /**
     * Constructs an {@code SmartScriptParserException} with a specified detail message.
     *
     * @param message the detail message
     */
    public QueryParserException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code SmartScriptParserException} with a specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public QueryParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code SmartScriptParserException} with the specified cause.
     *
     * @param cause the cause
     */
    public QueryParserException(Throwable cause) {
        super(cause);
    }
}
