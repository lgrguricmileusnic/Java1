package hr.fer.oprpp1.custom.collections;

/**
 * Unchecked exception, thrown to indicate that a stack is empty when peeking or popping an empty stack.
 */
public class EmptyStackException extends RuntimeException{
    /**
     * Default constructor
     */
    public EmptyStackException() {
        super();
    }

    /**
     * Constructs an {@code EmptyStackException} with a specified detail message.
     * @param message the detail message
     */
    public EmptyStackException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code EmptyStackException} with a specified detail message and cause.
     * @param message the detail message
     * @param cause the cause
     */
    public EmptyStackException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Constructs an {@code EmptyStackException} with the specified cause.
     * @param cause the cause
     */
    public EmptyStackException(Throwable cause) {
        super(cause);
    }
}
