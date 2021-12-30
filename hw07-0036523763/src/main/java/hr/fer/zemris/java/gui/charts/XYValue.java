package hr.fer.zemris.java.gui.charts;

import hr.fer.zemris.java.gui.layouts.RCPosition;

public class XYValue {
    private final int x;
    private final int y;

    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static XYValue parse(String text) {
        text = text.strip();
        if(!text.matches("[0-9]+,[0-9]+")) throw new IllegalArgumentException("Invalid RCPosition format!");
        String[] args = text.split(",");
        return new XYValue(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
}
