package hr.fer.zemris.java.gui.calc.components.buttons.functions;

import hr.fer.zemris.java.gui.calc.components.buttons.CalcButton;

import java.util.function.DoubleBinaryOperator;

/**
 * CalcButton subclass which adds a {@link DoubleBinaryOperator} property,
 * which can then be applied in any actions related to this button.
 */
public class DoubleBinaryOperatorCalcButton extends CalcButton {
    /**
     * double binary operator
     */
    private final DoubleBinaryOperator binaryOperator;

    /**
     * Constructs {@code DoubleBinaryOperatorCalcButton} with passed text and operator.
     * @param text button text
     * @param bo button operator
     */
    public DoubleBinaryOperatorCalcButton(String text, DoubleBinaryOperator bo) {
        super(text);
        this.binaryOperator = bo;
    }

    /**
     * Gets this button's binary operator.
     * @return {@link DoubleBinaryOperator} for this button
     */
    public DoubleBinaryOperator getBinaryOperator() {
        return binaryOperator;
    }
}
