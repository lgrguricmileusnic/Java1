package hr.fer.zemris.java.gui.calc.components;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * This class is a {@link JLabel} subclass,
 * The label has a yellow background, a {@link BevelBorder} and
 * aligns text to the right.
 * The label text is also of size 30f.
 */
public class Display extends JLabel {

    /**
     * Creates a <code>JLabel</code> instance with the specified text.
     * The label is aligned against the leading edge of its display area,
     * and centered vertically.
     * The label has a yellow background, a {@link BevelBorder} and
     * aligns text to the right.
     * The label text is also of size 30f.
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
