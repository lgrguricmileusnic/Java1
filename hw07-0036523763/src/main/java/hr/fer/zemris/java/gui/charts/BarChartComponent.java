package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class BarChartComponent extends JComponent {
    private BarChart barChart;
    private static final int SPACING = 5;
    private static final int AXIS_SPACING = 10;

    public BarChartComponent(BarChart barChart) {
        this.barChart = barChart;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Dimension dim = getSize();
        Insets insets = getInsets();
        int w = dim.width - insets.left - insets.right;
        int h = dim.height - insets.top - insets.bottom;
        int x0 = insets.left;
        int y0 = insets.top;
        int barCount = barChart.getValues().size();

        //font setup
        FontMetrics titleMetrics = g2d.getFontMetrics();
        Font titleFont = titleMetrics.getFont();
        Font nameFont = titleMetrics.getFont().deriveFont(12f).deriveFont(Font.BOLD);
        g2d.setFont(nameFont);
        FontMetrics nameMetrics = g2d.getFontMetrics();

        int maxNumberWidth = nameMetrics.stringWidth(String.valueOf(barChart.getYmax()));
        //distance from left inset end to the closest character on the y axis
        int baseline = titleMetrics.getHeight() + SPACING;
        int originX = baseline + maxNumberWidth + SPACING;
        int originY = baseline + nameMetrics.getHeight() + SPACING;

        int mYmax = calcNextDivisibleNumber(barChart.getYmax() - barChart.getYmin(), barChart.getUnitSegment());
        int count = mYmax / barChart.getUnitSegment();
        int unitLength = (h - originY - AXIS_SPACING) / count / barChart.getUnitSegment();
        int barWidth = (w - originX - AXIS_SPACING) / barCount;

        int xAxisLength = barWidth * barCount;
        int yAxisLength = unitLength * barChart.getUnitSegment() * count;

        g2d.setFont(titleFont);
        //draw xTitle
        g2d.drawString(barChart.getxTitle(), originX + (xAxisLength - titleMetrics.stringWidth(barChart.getxTitle())) / 2, h - titleMetrics.getDescent());
        //draw yTitle
        AffineTransform at = new AffineTransform();
        at.rotate(-Math.PI / 2);
        g2d.setTransform(at);
        g2d.drawString(barChart.getyTitle(), -(yAxisLength + titleMetrics.stringWidth(barChart.getyTitle())) / 2, titleMetrics.getAscent());
        at.rotate(Math.PI / 2);
        g2d.setTransform(at);

        //draw x axis names and markers
        List<XYValue> values = barChart.getValues();
        g2d.setFont(nameFont);
        g2d.drawLine(originX, h - (originY - SPACING),
                originX, h - originY);
        for (int i = 0; i < values.size(); i++) {
            String valueX = String.valueOf(values.get(i).getX());
            int strW = nameMetrics.stringWidth(valueX);
            g2d.drawString(valueX, originX + barWidth / 2 + barWidth * i - strW / 2, h - baseline);
            int x = originX + barWidth + barWidth * i;
            g2d.drawLine(x, h - (originY - SPACING), x, h - originY);
        }
        //draw x axis
        g2d.drawLine(originX, h - originY, w, h - originY);
        g2d.fillPolygon(new int[] {w, w-5, w-5}, new int[] {h - originY, h - originY - 5, h - originY + 5}, 3);

        //draw y axis
        g2d.drawLine(originX, h - originY, originX, 0);
        g2d.fillPolygon(new int[] {originX, originX - 5, originX + 5}, new int[] {0, 5, 5}, 3);

        //draw y axis markers and numbers
        for (int i = 0; i <= count; i++) {
            String num = String.valueOf(i * barChart.getUnitSegment());
            int strW = nameMetrics.stringWidth(num);
            int y = h - (originY + unitLength * barChart.getUnitSegment() * i);
            g2d.drawLine(originX, y, (int) (originX - SPACING / 2), y);
            g2d.drawString(num, (baseline + maxNumberWidth - nameMetrics.stringWidth(num)), y + nameMetrics.getAscent()/2);
        }

        //draw grid
        g2d.setColor(Color.gray);
        for (int i = 0; i <= values.size(); i++) {
            int x = originX + barWidth + barWidth * i;
            g2d.drawLine(x, h - (originY - SPACING), x, 0);
        }
        for (int i = 1; i <= count; i++) {
            int y = h - (originY + unitLength * barChart.getUnitSegment() * i);
            g2d.drawLine(originX, y, w, y);
        }

        //draw shadows
        g2d.setColor(Color.darkGray);
        for (int i = 0; i < barCount; i++) {
            XYValue value = values.get(i);
            int barHeight = unitLength * value.getY();
            g2d.fillRect(originX + barWidth*i + 5, h - (originY + barHeight - 5), barWidth - 2, barHeight - 5);
        }

        //draw bars
        g2d.setColor(Color.orange);
        for (int i = 0; i < barCount; i++) {
            XYValue value = values.get(i);
            int y = h - (originY + unitLength * i);
            int barHeight = unitLength * value.getY();
            g2d.fillRect(originX + (barWidth)*i + 1 , h - (originY + barHeight), barWidth - 1, barHeight);
        }


    }

    private int calcNextDivisibleNumber(int start, int divisor) {
        if (start % divisor == 0) return start;
        return ((start / divisor) + 1) * divisor;
    }
}
