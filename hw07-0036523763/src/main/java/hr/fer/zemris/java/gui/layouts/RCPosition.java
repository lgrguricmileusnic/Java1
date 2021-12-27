package hr.fer.zemris.java.gui.layouts;

public class RCPosition {
    private final int row;
    private final int column;

    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static RCPosition parse(String text) {
        text = text.strip();
        if(!text.matches("[0-9]+,[0-9]+")) throw new IllegalArgumentException("Invalid RCPosition format!");
        String[] args = text.split(",");
        return new RCPosition(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
}
