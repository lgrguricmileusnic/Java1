package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.components.Display;
import hr.fer.zemris.java.gui.calc.components.buttons.CalcButton;
import hr.fer.zemris.java.gui.calc.components.buttons.functions.DoubleBinaryOperatorCalcButton;
import hr.fer.zemris.java.gui.calc.components.buttons.functions.InvertibleFunctionCalcButton;
import hr.fer.zemris.java.gui.calc.components.buttons.functions.UnaryFunctionCalcButton;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Calculator application with {@code Swing} GUI.
 * Supports various unary functions, stack operations and standard algebra operations.
 */
public class Calculator extends JFrame {
    /**
     * calculator model
     */
    private static CalcModel model;
    /**
     * GUI main content pane
     */
    private static Container contentPane;
    /**
     * stack
     */
    private static Stack<Double> stack;

    /**
     * Constructs {@code Calculator JFrame}, sets title, location, close operation and preferred size.
     * Initialises stack and GUI.
     */
    public Calculator(){
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calculator");
        setLocation(20, 20);
        setPreferredSize(new Dimension(600, 500));
        stack = new Stack<>();
        initGUI();
        pack();
    }

    /**
     * Initialises GUI
     */
    private void initGUI() {
        contentPane = getContentPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(new CalcLayout(10));
        Display display = new Display("");
        contentPane.add(display, "1,1");
        model.addCalcValueListener(model -> display.setText(model.toString()));
        initInputPanel();
        initFunctions();
        initOther();
        initBinaryFunctions();
    }

    /**
     * Initialises binary function buttons
     */
    private void initBinaryFunctions() {
        List<DoubleBinaryOperatorCalcButton> buttons = new ArrayList<>(5);
        buttons.add(new DoubleBinaryOperatorCalcButton("/", (a, b) -> a/b));
        buttons.add(new DoubleBinaryOperatorCalcButton("*", (a, b) -> a*b));
        buttons.add(new DoubleBinaryOperatorCalcButton("-", (a, b) -> a-b));
        buttons.add(new DoubleBinaryOperatorCalcButton("+", Double::sum));
        buttons.add(new DoubleBinaryOperatorCalcButton("x^n", Math::pow));

        for (int i = 2; i <=5 ; i++) {
            contentPane.add(buttons.get(i - 2), new RCPosition(i,6));
        }
        contentPane.add(buttons.get(4), "5,1");

        for(var button : buttons) {
            button.addActionListener(l -> {
                if (model.hasFrozenValue()) throw new CalculatorInputException("Model has frozen value");
                if(model.getPendingBinaryOperation() != null) {
                    model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
                }
                model.freezeValue(String.valueOf(model.getValue()));
                model.setActiveOperand(model.getValue());
                model.setPendingBinaryOperation(button.getBinaryOperator());
                model.clear();
            });
        }
    }

    /**
     * Initialises unary function buttons.
     * e.g. sin log cos ln
     */
    private void initFunctions() {
        List<InvertibleFunctionCalcButton> buttons = new ArrayList<>(6);
        buttons.add(new InvertibleFunctionCalcButton("sin", "arcsin", Math::sin, Math::asin));
        buttons.add(new InvertibleFunctionCalcButton("log", "10^x", Math::log10, (x) -> Math.pow(10.0, x)));
        buttons.add(new InvertibleFunctionCalcButton("cos", "arccos", Math::cos, Math::acos));
        buttons.add(new InvertibleFunctionCalcButton("ln", "e^x", Math::log, (x) -> Math.pow(Math.E, x)));
        buttons.add(new InvertibleFunctionCalcButton("tan", "arctan", Math::tan, Math::atan));
        buttons.add(new InvertibleFunctionCalcButton("ctg", "arcctg", x -> 1.0 / Math.tan(x), x -> Math.PI / 2 - Math.atan(x)));

        contentPane.add(buttons.get(0), "2,2");
        contentPane.add(buttons.get(1), "3,1");
        contentPane.add(buttons.get(2), "3,2");
        contentPane.add(buttons.get(3), "4,1");
        contentPane.add(buttons.get(4), "4,2");
        contentPane.add(buttons.get(5), "5,2");

        for(var b : buttons) {
            b.addActionListener((l) -> {
                if(model.hasFrozenValue()) throw new CalcLayoutException("Model already has frozen value");
                model.setValue(b.getActiveFunction().apply(model.getValue()));
            });
        }

        JCheckBox invCheckbox = new JCheckBox("Inv", false);
        invCheckbox.setBackground(Color.WHITE);
        invCheckbox.addChangeListener(l -> {
            if(invCheckbox.isSelected()) {
                for(var button : buttons){
                    button.setInverted(true);
                }
            } else {
                for(var button : buttons){
                    button.setInverted(false);
                }
            }
        });
        contentPane.add(invCheckbox, "5,7");

        UnaryFunctionCalcButton reciprocal = new UnaryFunctionCalcButton("1/x", x -> 1/x);

        reciprocal.addActionListener(l -> {
            if(model.hasFrozenValue()) throw new CalcLayoutException("Model already has frozen value");
            model.setValue(reciprocal.getFunction().apply(model.getValue()));
        });
        contentPane.add(reciprocal, "2,1");
    }

    /**
     * Initialises clear, reset, push, pop and solve (=) buttons
     */
    private void initOther() {
        CalcButton clr = new CalcButton("clr");
        clr.addActionListener(l -> {
            model.freezeValue(null);
            model.clear();
        });
        contentPane.add(clr, "1,7");

        CalcButton reset = new CalcButton("reset");
        reset.addActionListener(l -> {
            model.freezeValue(null);
            model.clearAll();
        });
        contentPane.add(reset, "2,7");

        CalcButton push = new CalcButton("push");
        push.addActionListener(l -> stack.push(model.getValue()));
        contentPane.add(push, "3,7");

        CalcButton pop = new CalcButton("pop");
        pop.addActionListener(l -> model.setValue(stack.pop()));
        add(pop, "4,7");

        CalcButton solve = new CalcButton("=");
        solve.addActionListener(l -> {
            model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
            model.setPendingBinaryOperation(null);
        });
        contentPane.add(solve, "1,6");
    }

    /**
     * Initialises digit panel, swap and decimal point buttons.
     */
    private void initInputPanel() {
        int dgt = 9;
        for (int i = 2; i < 5 ; i++) {
            for (int j = 5; j >=3 ; j--) {
                CalcButton tmp = new CalcButton(String.valueOf(dgt));
                tmp.addActionListener((e) -> model.insertDigit(Integer.parseInt(((JButton) e.getSource()).getText())));
                tmp.setFont(tmp.getFont().deriveFont(30f));
                contentPane.add(tmp, new RCPosition(i, j));
                dgt--;
            }
        }
        CalcButton zero = new CalcButton("0");
        zero.addActionListener((e) -> model.insertDigit(Integer.parseInt(((JButton) e.getSource()).getText())));
        zero.setFont(zero.getFont().deriveFont(30f));
        contentPane.add(zero, new RCPosition(5,3));

        CalcButton swap = new CalcButton("+/-");
        swap.addActionListener(l -> model.swapSign());
        contentPane.add(swap, "5,4");

        CalcButton decimalPoint = new CalcButton(".");
        decimalPoint.addActionListener(l -> model.insertDecimalPoint());
        contentPane.add(decimalPoint, "5,5");
    }


    /**
     * Main function, initialises {@link CalcModelImpl} model and {@code Calculator}.
     * @param args takes no arguments
     */
    public static void main(String[] args) {
        model = new CalcModelImpl();
        SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
    }

}
