package hr.fer.zemris.java.gui.calc.components.buttons;

import javax.swing.*;
import java.awt.*;

/**
 * This class is a {@link JButton} extension,
 * constructs {@link JButton} with a light gray background and black foreground.
 */
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
