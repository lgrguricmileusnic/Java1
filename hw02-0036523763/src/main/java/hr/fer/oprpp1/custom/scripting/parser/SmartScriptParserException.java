package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Unchecked exception, thrown to indicate that an invalid token was passed to the {@code SmartScriptParser}.
 */
public class SmartScriptParserException extends RuntimeException {
    /**
     * Default constructor
     */
    public SmartScriptParserException() {
        super();
    }

    /**
     * Constructs an {@code SmartScriptParserException} with a specified detail message.
     *
     * @param message the detail message
     */
    public SmartScriptParserException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code SmartScriptParserException} with a specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public SmartScriptParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code SmartScriptParserException} with the specified cause.
     *
     * @param cause the cause
     */
    public SmartScriptParserException(Throwable cause) {
        super(cause);
    }
}
