package hr.fer.zemris.java.gui.calc.components.buttons;

import javax.swing.*;
import java.awt.*;

public class CalcButton extends JButton {
    /**
     * Creates a button with text.
     *
     * @param text the text of the button
     */
    public CalcButton(String text) {
        super(text);
        setBackground(Color.LIGHT_GRAY);
        setForeground(Color.BLACK);
        setOpaque(true);
    }
}
