package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo for {@code BarCharComponent}.
 * Creates a JFrame with a BarCharComponent example.
 */
public class BarChartDemo extends JFrame {
    /**
     * Constructor
     * @param model bar chart model
     */
    public BarChartDemo(BarChart model){
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BarChartDemo");
        setLocation(20, 20);
        setPreferredSize(new Dimension(600, 500));
        initGUI(model);
        pack();
    }

    /**
     * Initialises {@link BarChartComponent} and adds it to the content pane.
     * @param model
     */
    private void initGUI(BarChart model) {
        Container cp = getContentPane();
        cp.add(new BarChartComponent(model));
    }

    /**
     * Creates bar chart model from file specified in {@code args}.
     * If the model is successfully created, creates window and displays the bar chart.
     * @param args path to file with bar chart data
     */
    public static void main(String[] args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(args[0]));
            if(lines.size() < 6) {
                System.out.println("Invalid file format");
                System.exit(1);
            }
            String xTitle = lines.get(0);
            String yTitle = lines.get(1);
            String[] values = lines.get(2).trim().replaceAll("\\s+", " ").split(" ");
            if(values.length < 1) {
                System.out.println("Invalid file format.");
                System.exit(1);
            }
            List<XYValue> xyValues = new ArrayList<>();
            try{
                for (String v : values) {
                    xyValues.add(XYValue.parse(v));
                }
            } catch(IllegalArgumentException e) {
                System.out.println("Invalid values format");
                System.exit(1);
            }

            if(!lines.get(3).matches("[0-9]+")) {
                System.out.println("Invalid ymin");
                System.exit(1);
            }
            int ymin = Integer.parseInt(lines.get(3));
            if(!lines.get(4).matches("[0-9]+")) {
                System.out.println("Invalid ymax");
                System.exit(1);
            }
            int ymax = Integer.parseInt(lines.get(4));
            if(!lines.get(5).matches("[0-9]+")) {
                System.out.println("Invalid step");
                System.exit(1);
            }
            int ystep = Integer.parseInt(lines.get(5));
            BarChart bc = new BarChart(xyValues,xTitle,yTitle,ymin,ymax,ystep);
            SwingUtilities.invokeLater(() -> new BarChartDemo(bc).setVisible(true));
        } catch (IOException e) {
            System.out.println("Unable to read file.");
        }
    }
}
