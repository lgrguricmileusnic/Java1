package hr.fer.zemris.java.gui.layouts.demo;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;

/**
 * Demo for {@link CalcLayout} class.
 */
public class DemoFrame1 extends JFrame {
    /**
     * Constructor
     */
    public DemoFrame1() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
        pack();
    }

    /**
     * Initialises GUI
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(3));
        cp.add(l("tekst 1"), new RCPosition(1,1));
        cp.add(l("tekst 2"), new RCPosition(2,3));
        cp.add(l("tekst stvarno najdulji"), new RCPosition(2,7));
        cp.add(l("tekst kraći"), new RCPosition(4,2));
        cp.add(l("tekst srednji"), new RCPosition(4,5));
        cp.add(l("tekst"), new RCPosition(4,7));
    }

    /**
     * Creates and returns {@link JLabel} object with
     * passed text and yellow opaque background.
     * @param text text for the label
     * @return {@link JLabel} with passed text and yellow opaque background.
     */
    private JLabel l(String text) {
        JLabel l = new JLabel(text);
        l.setBackground(Color.YELLOW);
        l.setOpaque(true);
        return l;
    }

    /**
     * Main function
     * @param args application takes no arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new DemoFrame1().setVisible(true);
        });
    }
}
