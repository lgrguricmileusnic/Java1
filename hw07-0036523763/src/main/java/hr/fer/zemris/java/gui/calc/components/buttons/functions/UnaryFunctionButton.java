package hr.fer.zemris.java.gui.calc.components.buttons.functions;

import hr.fer.zemris.java.gui.calc.components.buttons.CalcButton;

import java.util.function.Function;

public class UnaryFunctionButton<T,R> extends CalcButton {
    private final Function<T,R> function;

    public UnaryFunctionButton(String text, Function<T, R> function) {
        super(text);
        this.function = function;
    }

    public Function<T, R> getFunction() {
        return function;
    }
}
