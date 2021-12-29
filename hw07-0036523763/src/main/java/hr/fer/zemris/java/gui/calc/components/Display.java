package hr.fer.zemris.java.gui.calc.components;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;

public class Display extends JLabel {
    /**
     * Creates a <code>JLabel</code> instance with
     * no image and with an empty string for the title.
     * The label is centered vertically
     * in its display area.
     * The label's contents, once set, will be displayed on the leading edge
     * of the label's display area.
     */
    public Display() {
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text.
     * The label is aligned against the leading edge of its display area,
     * and centered vertically.
     *
     * @param text The text to be displayed by the label.
     */
    public Display(String text) {
        super(text);
        setBackground(Color.YELLOW);
        setOpaque(true);
        setBorder(new BevelBorder(0));
        setHorizontalAlignment(RIGHT);
        setFont(getFont().deriveFont(30f));
    }
}
