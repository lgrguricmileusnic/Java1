package hr.fer.oprpp.hw02.prob1;

/**
 * Unchecked exception, thrown to indicate that an invalid token was passed to the {@code Lexer}.
 */
public class LexerException extends RuntimeException {
    /**
     * Default constructor
     */
    public LexerException() {
        super();
    }

    /**
     * Constructs an {@code LexerException} with a specified detail message.
     *
     * @param message the detail message
     */
    public LexerException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code LexerException} with a specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public LexerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code LexerException} with the specified cause.
     *
     * @param cause the cause
     */
    public LexerException(Throwable cause) {
        super(cause);
    }
}
