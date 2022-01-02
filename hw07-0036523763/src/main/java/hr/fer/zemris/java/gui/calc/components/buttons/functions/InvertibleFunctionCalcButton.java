package hr.fer.zemris.java.gui.calc.components.buttons.functions;

import hr.fer.zemris.java.gui.calc.components.buttons.CalcButton;

import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

/**
 * CalcButton subclass which adds two {@link Function} property,
 * which can then be applied in any actions related to this button.
 * Only one of the {@link Function} properties is applied, depending on the
 * {@code inverted} flag.
 */
public class InvertibleFunctionCalcButton extends CalcButton {
    /**
     * boolean flag storing the state of this button
     */
    private boolean inverted;
    /**
     * default function
     */
    private final Function<Double,Double> function;
    /**
     * inverted function
     */
    private final Function<Double,Double> invertedFunction;
    /**
     * default button text
     */
    private final String defaultText;
    /**
     * alternate button text for when the function is inverted
     */
    private final String alternateText;

    /**
     * Constructs a {@code InvertibleFunctionCalcButton} with passed button texts and functions.
     * Initialises button to default state (not inverted).
     * @param defaultText default button text
     * @param alternateText alternate button text
     * @param function default function
     * @param invertedFunction inverted function
     */
    public InvertibleFunctionCalcButton(String defaultText, String alternateText, Function<Double,Double> function, Function<Double,Double> invertedFunction) {
        super(defaultText);
        this.inverted = false;
        this.alternateText = alternateText;
        this.defaultText = defaultText;
        this.function = function;
        this.invertedFunction = invertedFunction;
    }

    /**
     * Gets default function.
     * @return default function.
     */
    public Function<Double,Double> getFunction() {
        return function;
    }

    /**
     * Gets inverted function.
     * @return inverted function
     */
    public Function<Double,Double> getInvertedFunction() {
        return invertedFunction;
    }

    /**
     * Gets function based of button state.
     * @return default function if not inverted, inverted function otherwise
     */
    public Function<Double,Double> getActiveFunction() {
        return inverted ? invertedFunction : function;
    }

    /**
     * Returns button state.
     * @return true if inverted, false otherwise
     */
    public boolean isInverted() {
        return inverted;
    }

    /**
     * Sets button state.
     * @param inverted new state
     */
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
        setText(inverted ? alternateText : defaultText);
    }
}
