package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.components.Display;
import hr.fer.zemris.java.gui.calc.components.buttons.CalcButton;
import hr.fer.zemris.java.gui.calc.components.buttons.functions.InvertibleFunctionButton;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class Calculator extends JFrame {
    private static CalcModel model;
    private static Container contentPane;

    public Calculator(){
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calculator");
        setLocation(20, 20);
        setPreferredSize(new Dimension(600, 500));
        initGUI();
        pack();
    }

    private void initGUI() {
        contentPane = getContentPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(new CalcLayout(10));
        Display display = new Display("");
        contentPane.add(display, "1,1");
        model.addCalcValueListener(model -> display.setText(model.toString()));
        initDigits();
        initInvertibleFunctions();
    }

    private void initOther() {

    }
    private void initDigits() {
        int dgt = 9;
        for (int i = 2; i < 5 ; i++) {
            for (int j = 5; j >=3 ; j--) {
                CalcButton tmp = new CalcButton(String.valueOf(dgt));
                tmp.addActionListener((e) -> {
                    model.insertDigit(Integer.parseInt(((JButton) e.getSource()).getText()));
                });
                tmp.setFont(tmp.getFont().deriveFont(30f));
                contentPane.add(tmp, new RCPosition(i, j));
                dgt--;
            }
        }
        CalcButton zero = new CalcButton("0");
        zero.addActionListener((e) -> {
            model.insertDigit(Integer.parseInt(((JButton) e.getSource()).getText()));
        });
        zero.setFont(zero.getFont().deriveFont(30f));
        contentPane.add(zero, new RCPosition(5,3));
    }

    private void initInvertibleFunctions() {
        List<InvertibleFunctionButton<Double,Double>> buttons = new ArrayList<>(6);
        buttons.add(new InvertibleFunctionButton<>("sin", "arcsin", Math::sin, Math::asin));
        buttons.add(new InvertibleFunctionButton<>("log", "10^x", Math::log10, (x) -> Math.pow(10.0, x)));
        buttons.add(new InvertibleFunctionButton<>("cos", "arccos", Math::cos, Math::acos));
        buttons.add(new InvertibleFunctionButton<>("ln", "e^x", Math::log, (x) -> Math.pow(Math.E, x)));
        buttons.add(new InvertibleFunctionButton<>("tan", "arctan", Math::tan, Math::atan));
        buttons.add(new InvertibleFunctionButton<>("ctg", "arcctg", x -> 1.0 / Math.tan(x), x -> Math.PI / 2 - Math.atan(x)));

        contentPane.add(buttons.get(0), "2,2");
        contentPane.add(buttons.get(1), "3,1");
        contentPane.add(buttons.get(2), "3,2");
        contentPane.add(buttons.get(3), "4,1");
        contentPane.add(buttons.get(4), "4,2");
        contentPane.add(buttons.get(5), "5,2");

        for(var b : buttons) {
            b.addActionListener((l) -> {
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
    }


    public static void main(String[] args) {
        model = new CalcModelImpl();
        SwingUtilities.invokeLater(() ->{
            new Calculator().setVisible(true);
        });
    }

}
