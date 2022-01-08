package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.*;
import java.awt.*;

public class JNotepadPP extends JFrame {


    public JNotepadPP() {
        super();
        setTitle("JNotepad++");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocation(20, 20);
        setSize(1300, 900);
        initGUI();
    }

    /**
     * Initialises GUI
     */
    public void initGUI() {
        Container contentPane = getContentPane();
        JTabbedPane tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JNotepadPP().setVisible(true);
        });
    }
}
