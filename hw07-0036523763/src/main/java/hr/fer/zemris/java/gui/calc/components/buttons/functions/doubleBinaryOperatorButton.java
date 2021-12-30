package hr.fer.zemris.java.gui.calc.components.buttons.functions;

import hr.fer.zemris.java.gui.calc.components.buttons.CalcButton;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;

public class doubleBinaryOperatorButton extends CalcButton {
    private DoubleBinaryOperator binaryOperator;

    public doubleBinaryOperatorButton(String text, DoubleBinaryOperator bo) {
        super(text);
        this.binaryOperator = bo;
    }

    public DoubleBinaryOperator getBinaryOperator() {
        return binaryOperator;
    }
}
