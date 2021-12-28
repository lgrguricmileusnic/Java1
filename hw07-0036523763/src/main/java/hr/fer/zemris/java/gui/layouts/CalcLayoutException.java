package hr.fer.zemris.java.gui.layouts;

public class CalcLayoutException extends RuntimeException{
    public CalcLayoutException() {
        super();
    }

    public CalcLayoutException(String message) {
        super(message);
    }

    public CalcLayoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalcLayoutException(Throwable cause) {
        super(cause);
    }

    protected CalcLayoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
