package hr.fer.zemris.java.gui.charts;


/**
 * This class represents a value consisting of two integer properties x, y.
 * e.g. graph coordinates, bar chart bar name and value...
 */
public class XYValue {
    /**
     * x value
     */
    private final int x;
    /**
     * y value
     */
    private final int y;

    /**
     * Constructs {@code XYValue} with passed x and y.
     * @param x x
     * @param y y
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x.
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y.
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Parses {@link String} of format "x,y" as a {@code XYValue} object
     * @param text {@link String} to be parsed
     * @return {@code XYValue} of parsed {@link String}
     */
    public static XYValue parse(String text) {
        text = text.strip();
        if(!text.matches("[0-9]+,[0-9]+")) throw new IllegalArgumentException("Invalid RCPosition format!");
        String[] args = text.split(",");
        return new XYValue(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
}
