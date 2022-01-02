package hr.fer.zemris.java.gui.layouts;


import java.awt.LayoutManager2;
import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;
import java.awt.Dimension;
import java.util.*;
import java.util.function.Function;


/**
 * This class is a {@link LayoutManager2} implementation.
 * The layout consists of a grid with 7 columns and 5 rows.
 * All components have the same width and the same height, except the first element of the first row
 * which has a 5-column width (including gaps between columns) and one row height.
 * A gap can also be specified for this layout, which will then be present between rows and columns of components.
 */
public class CalcLayout implements LayoutManager2 {
    /**
     * stores specified gap width
     */
    private final int gapWidth;
    /**
     * underlying map storing {@link RCPosition} positions of components
     */
    private final Map<Component, RCPosition> componentMap;
    /**
     * row count constant
     */
    private final int NUMBER_OF_ROWS = 5;
    /**
     * column count constant
     */
    private final int NUMBER_OF_COLUMNS = 7;

    /**
     * {@link RCPosition} object for position 1,1
     */
    public static final RCPosition RCPOSITION1_1 = new RCPosition(1,1);

    /**
     * Constructs {@code CalcLayout} with specified gap
     * @param gap gap between rows and columns
     */
    public CalcLayout(int gap) {
        this.gapWidth = gap;
        componentMap = new HashMap<>();
    }

    /**
     * Constructs {@code CalcLayout} with no gap.
     */
    public CalcLayout() {
        this(0);
    }

    /**
     * Adds layout component to this {@code CalcLayout} with passed constraint.
     * Constraint can be a string of format "row,column" or a {@link RCPosition} object specifying the row and column
     * in which the component should be placed.
     * The specified row must be in range [1,5] and the specified column must be in range [1,7].
     * Since the first component of the first row takes up 5 columns, only 1,6,7 are valid column indexes for the first
     * row.
     * @param comp component
     * @param constraints {@link String} or {@link RCPosition} specifying the row-column position of the component
     * @throws NullPointerException if any of the passed parameters is {@code null}
     * @throws IllegalArgumentException if the {@link String} constraint was incorrectly formatted
     * @throws CalcLayoutException if there already exists a component at the specified position, if a column or row index is
     * out of bounds or if an invalid row-column combination was specified
     */
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        Objects.requireNonNull(constraints);
        Objects.requireNonNull(comp);
        RCPosition position;
        if (constraints instanceof String) {
            position = RCPosition.parse((String) constraints);
        } else if (constraints instanceof RCPosition) {
            position = (RCPosition) constraints;
        } else {
            throw new CalcLayoutException("Invalid constraint type: must be String or RCPosition");
        }

        if (position.getRow() < 1 || position.getRow() > NUMBER_OF_ROWS || position.getColumn() < 1 || position.getColumn() > NUMBER_OF_COLUMNS) {
            throw new CalcLayoutException("Row or column position out of bounds");
        }

        if (position.getColumn() > 1 && position.getColumn() < 6 && position.getRow() == 1) {
            throw new CalcLayoutException("Only column indexes 1, 6, 7 can be used in the first row");
        }
        if (componentMap.containsValue(position)) {
            throw new CalcLayoutException("Component already set at position " + position);
        }

        componentMap.put(comp, position);
    }

    /**
     * Removes specified component.
     * @param comp component to be removed
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        componentMap.remove(comp);
    }

    /**
     * Lays out parent container.
     * Calculates origin x,y coordinates, component width and height and sets bounds for each child component of
     * passed parent.
     * If the width and height available for laying out components, accounting for parent insets, is not divisible by
     * column or row count, the remaining width and height is relatively uniformly distributed among rows or columns.
     * @param parent parent container which is being laid out.
     */
    @Override
    public void layoutContainer(Container parent) {
        Insets parentInsets = parent.getInsets();
        int availableWidth = parent.getWidth() - parentInsets.left - parentInsets.right - (NUMBER_OF_COLUMNS - 1) * gapWidth;
        int availableHeight = parent.getHeight() - parentInsets.top - parentInsets.bottom - (NUMBER_OF_ROWS - 1) * gapWidth;
        int rowHeight = availableHeight / NUMBER_OF_ROWS;
        int columnWidth = availableWidth / NUMBER_OF_COLUMNS;
        int extraWidth = availableWidth - (columnWidth * NUMBER_OF_COLUMNS);
        int extraHeight = availableHeight - (rowHeight * NUMBER_OF_ROWS);

        Set<Integer> uniformRowIndexes = getUniformRowIndexes(extraHeight);
        Set<Integer> uniformColumnIndexes = getUniformColumnIndexes(extraWidth);
        Component[] components = parent.getComponents();

        for (Component c : components) {
            RCPosition position = componentMap.get(c);
            int cx = (position.getColumn() - 1) * (columnWidth + gapWidth);
            int cy = (position.getRow() - 1) * (rowHeight + gapWidth);
            int cWidth = columnWidth;
            int cHeight = rowHeight;

            for (int index : uniformRowIndexes) {
                if (index >= position.getRow()) break;
                cy++;
            }
            for (int index : uniformColumnIndexes) {
                if (index >= position.getColumn()) break;
                cx++;
            }

            if (uniformRowIndexes.contains(position.getRow())) {
                cHeight++;
            }

            if (position.equals(RCPOSITION1_1)) {
                cWidth = columnWidth * 5 + gapWidth * 4;
                for (int index : uniformColumnIndexes) {
                    if (index > 5) break;
                    cWidth++;
                }
            } else if (uniformColumnIndexes.contains(position.getColumn())) {
                cWidth++;
            }
            c.setBounds(cx, cy, cWidth, cHeight);
        }
    }

    /**
     * Calculates maximum layout size of passed parent for this layout and returns it.
     * @param parent parent whose size will be calculated
     * @return maximum layout size
     */
    @Override
    public Dimension maximumLayoutSize(Container parent) {
        return calcLayoutSize(parent, Component::getMaximumSize);
    }

    /**
     * Calculates minimum layout size of passed parent for this layout and returns it.
     * @param parent parent whose size will be calculated
     * @return minimum layout size
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return calcLayoutSize(parent, Component::getMinimumSize);
    }

    /**
     * Calculates preferred layout size of passed parent for this layout and returns it.
     * @param parent parent whose size will be calculated
     * @return preferred layout size
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return calcLayoutSize(parent, Component::getPreferredSize);
    }

    /**
     * Returns the preferred alignment along the x axis.
     *
     * @param target – the target container
     * @return the x-axis alignment preference
     */

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    /**
     * Returns the preferred alignment along the y axis.
     *
     * @param target – the target container
     * @return the x-axis alignment preference
     */
    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    /**
     * Adding layout component with name, without constraints is not a supported operation
     * in this implementation of {@link LayoutManager2}
     * @param name mame
     * @param comp component
     * @throws UnsupportedOperationException always
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException("Adding layout component with name is not supported.");
    }

    /**
     * Invalidates the layout, indicating that if the layout manager has cached information it should be discarded.
     * @param target target
     */
    @Override
    public void invalidateLayout(Container target) {
    }

    /**
     * Calculates layout size with passed "size getter".
     * A size getter is a {@link Function} which for passed {@link Component} returns a {@link Dimension} object
     * for that component.
     * Depending on the size getter a {@code Dimension} object for this layout is calculated using all
     * components of the parent container and returned.
     * E.g. if passed {@link Function} returns maximum component size, a maximum size for the passed parent with this
     * layout will be calculated.
     * @param parent parent container
     * @param sizeGetter {@link Function} which for passed {@link Component} returns a {@link Dimension} object
     * for that component
     * @return {@link Dimension} object with calculated width and height of parent, using {@code sizeGetter} {@link Function}
     */
    private Dimension calcLayoutSize(Container parent, Function<Component, Dimension> sizeGetter) {
        Dimension dimension = new Dimension(0, 0);
        Component[] components = parent.getComponents();
        int cMaxWidth = -1;
        int cMaxHeight = -1;

        for (Component c : components) {
            if (componentMap.containsKey(c)) {
                Dimension componentDimension = sizeGetter.apply(c);
                if (componentDimension == null) continue;

                cMaxHeight = Math.max(cMaxHeight, componentDimension.height);
                if (componentMap.get(c).equals(RCPOSITION1_1)) {
                    componentDimension.width = (componentDimension.width - (4 * gapWidth)) / 5;
                }
                cMaxWidth = Math.max(cMaxWidth, componentDimension.width);
            }
        }

        Insets insets = parent.getInsets();
        dimension.width = (NUMBER_OF_COLUMNS - 1) * gapWidth + NUMBER_OF_COLUMNS * cMaxWidth + insets.left + insets.right;
        dimension.height = (NUMBER_OF_ROWS - 1) * gapWidth + NUMBER_OF_ROWS * cMaxHeight + insets.top + insets.bottom;
        return dimension;
    }

    /**
     * Gets indexes of rows whose width should be increased by one to uniformly distribute extra height.
     * @param extraHeight extra undistributed height
     * @return indexes of rows whose height should be increased by one
     */
    private Set<Integer> getUniformRowIndexes(int extraHeight) {
        switch (extraHeight) {
            case 1 -> {
                return Set.of(3);
            }
            case 2 -> {
                return Set.of(2, 4);
            }
            case 3 -> {
                return Set.of(1, 3, 5);
            }
            case 4 -> {
                return Set.of(1, 2, 4, 5);
            }
            default -> {
                return Collections.emptySet();
            }
        }
    }
    /**
     * Gets indexes of columns whose width should be increased by one to uniformly distribute extra width.
     * @param extraWidth extra undistributed width
     * @return indexes of columns whose width should be increased by one
     */
    private Set<Integer> getUniformColumnIndexes(int extraWidth) {

        switch (extraWidth) {
            case 1 -> {
                return Set.of(4);
            }
            case 2 -> {
                return Set.of(3, 5);
            }
            case 3 -> {
                return Set.of(2, 4, 6);
            }
            case 4 -> {
                return Set.of(1, 3, 5, 7);
            }
            case 5 -> {
                return Set.of(1, 3, 4, 5, 7);
            }
            case 6 -> {
                return Set.of(1, 2, 3, 5, 6, 7);
            }
            default -> {
                return Collections.emptySet();
            }
        }
    }
}
