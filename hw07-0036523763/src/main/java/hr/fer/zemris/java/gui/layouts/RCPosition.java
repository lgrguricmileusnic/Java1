package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * This class represents a row-column position.
 * (e.g. in {@link CalcLayout})
 */
public class RCPosition {
    /**
     * row index
     */
    private final int row;
    /**
     * column index
     */
    private final int column;


    /**
     * Constructs {@link RCPosition} object for passed row and column
     * @param row row
     * @param column column
     */
    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Parses passed {@link String} into a {@code RCPosition} object and returns it
     * {@link String} must be of format "int,int".
     * @param text text to be parsed
     * @return {@code RCPosition} object
     */
    public static RCPosition parse(String text) {
        text = text.strip();
        if(!text.matches("[0-9]+,[0-9]+")) throw new IllegalArgumentException("Invalid RCPosition format!");
        String[] args = text.split(",");
        return new RCPosition(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }

    /**
     * Gets row.
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets column.
     * @return column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Checks if the passed object is an instance of {@code RCPosition} and
     * if the {code RCposition }object represents the same row and column position
     * @param o other object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RCPosition position = (RCPosition) o;
        return row == position.row && column == position.column;
    }

    /**
     * Calculates hash code of this object.
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    /**
     * Creates and returns {@link String} representation of this {@code RCPosition} object
     * @return {@link String} representation of this {@code RCPosition} object
     */
    @Override
    public String toString() {
        return "(" + row + ", " + column +")";
    }
}
