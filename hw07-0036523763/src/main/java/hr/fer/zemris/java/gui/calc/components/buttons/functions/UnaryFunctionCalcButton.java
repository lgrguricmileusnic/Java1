package hr.fer.zemris.java.gui.calc.components.buttons.functions;

import hr.fer.zemris.java.gui.calc.components.buttons.CalcButton;

import java.util.function.Function;

/**
 * CalcButton subclass which adds a {@link Function} property,
 * which can then be applied in any actions related to this button.
 */
public class UnaryFunctionCalcButton extends CalcButton {
    /**
     * function
     */
    private final Function<Double,Double> function;

    /**
     * Constructs button with passed text and function.
     * @param text button text
     * @param function function
     */
    public UnaryFunctionCalcButton(String text, Function<Double, Double> function) {
        super(text);
        this.function = function;
    }

    /**
     * Gets function.
     * @return function
     */
    public Function<Double,Double> getFunction() {
        return function;
    }
}
