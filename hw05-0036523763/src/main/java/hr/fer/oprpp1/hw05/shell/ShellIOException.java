package hr.fer.oprpp1.hw05.shell;


/**
 * Unchecked exception, thrown to indicate that there was a problem during {@code Shell} runtime.
 */
public class ShellIOException extends RuntimeException {
    /**
     * Default constructor
     */
    public ShellIOException() {
        super();
    }

    /**
     * Constructs an {@code ShellIOException} with a specified detail message.
     *
     * @param message the detail message
     */
    public ShellIOException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code ShellIOException} with a specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public ShellIOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code ShellIOException} with the specified cause.
     *
     * @param cause the cause
     */
    public ShellIOException(Throwable cause) {
        super(cause);
    }
}
