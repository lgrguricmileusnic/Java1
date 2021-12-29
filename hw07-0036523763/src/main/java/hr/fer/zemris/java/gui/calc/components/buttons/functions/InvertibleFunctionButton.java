package hr.fer.zemris.java.gui.calc.components.buttons.functions;

import hr.fer.zemris.java.gui.calc.components.buttons.CalcButton;

import java.util.function.Function;

public class InvertibleFunctionButton<T,R> extends CalcButton {
    private boolean inverted;
    private final Function<T,R> function;
    private final Function<T,R> invertedFunction;
    private final String defaultText;
    private final String alternateText;

    public InvertibleFunctionButton(String defaultText, String alternateText, Function<T, R> function, Function<T, R> invertedFunction) {
        super(defaultText);
        this.inverted = false;
        this.alternateText = alternateText;
        this.defaultText = defaultText;
        this.function = function;
        this.invertedFunction = invertedFunction;
    }

    public Function<T, R> getFunction() {
        return function;
    }

    public Function<T, R> getInvertedFunction() {
        return invertedFunction;
    }

    public Function<T,R> getActiveFunction() {
        return inverted ? invertedFunction : function;
    }
    public boolean isInverted() {
        return inverted;
    }

    public void setInverted(boolean inverted) {
        this.inverted = inverted;
        setText(inverted ? alternateText : defaultText);
    }
}
