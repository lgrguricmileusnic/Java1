package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

public class RCPosition {
    private final int row;
    private final int column;

    public static final RCPosition RCPOSITION1_1 = new RCPosition(1,1);
    
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

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RCPosition position = (RCPosition) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column +")";
    }
}
