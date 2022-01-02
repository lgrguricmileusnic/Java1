package hr.fer.zemris.java.gui.layouts;


/**
 * {@code CalcLayoutException} is a {@link RuntimeException} which should be thrown
 *  when a problem occurs when using {@link CalcLayout}.
 */
public class CalcLayoutException extends RuntimeException{

    /**
     * Constructor
     */
    public CalcLayoutException() {
        super();
    }

    /**
     * Constructor with message
     * @param message message
     */
    public CalcLayoutException(String message) {
        super(message);
    }
}
