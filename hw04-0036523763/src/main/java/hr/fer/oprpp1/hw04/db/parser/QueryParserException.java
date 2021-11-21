package hr.fer.oprpp1.hw04.db.parser;

/**
 * Unchecked exception, thrown to indicate that an invalid token was passed to the {@code QueryParser}.
 */
public class QueryParserException extends RuntimeException {
    /**
     * Default constructor
     */
    public QueryParserException() {
        super();
    }

    /**
     * Constructs an {@code QueryParserException} with a specified detail message.
     *
     * @param message the detail message
     */
    public QueryParserException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code QueryParserException} with a specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public QueryParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code QueryParserException} with the specified cause.
     *
     * @param cause the cause
     */
    public QueryParserException(Throwable cause) {
        super(cause);
    }
}
