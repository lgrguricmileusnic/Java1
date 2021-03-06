package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import java.awt.*;

/**
 * Demo for PrimListModel
 */
public class PrimDemo extends JFrame {

    /**
     * Constructor
     */
    public PrimDemo() {
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PrimDemo");
        setLocation(20, 20);
        setSize(400, 450);
        initGUI();
    }

    /**
     * Initialises GUI
     */
    public void initGUI() {
        Container contentPane = getContentPane();
        PrimListModel model = new PrimListModel();

        JList<Integer> leftList= new JList<>(model);
        JList<Integer> rightList= new JList<>(model);

        Container listsPane = new Container();
        listsPane.setLayout(new GridLayout(1,2));
        listsPane.add(new JScrollPane(leftList));
        listsPane.add(new JScrollPane(rightList));

        JButton b = new JButton("sljedeći");
        b.addActionListener((a) -> {
            model.next();
        });

        contentPane.add(listsPane, BorderLayout.CENTER);
        contentPane.add(b, BorderLayout.SOUTH);
    }

    /**
     * Starts PrimDemo
     * @param args demo takes no arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PrimDemo().setVisible(true);
        });
    }
}
