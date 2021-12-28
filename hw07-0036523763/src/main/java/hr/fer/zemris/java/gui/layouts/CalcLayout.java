package hr.fer.zemris.java.gui.layouts;

import javax.swing.border.Border;
import java.awt.LayoutManager2;
import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;
import java.awt.Dimension;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class CalcLayout implements LayoutManager2 {
    private final int gapWidth;
    private final Map<Component, RCPosition> componentMap;
    private final int NUMBER_OF_ROWS = 5;
    private final int NUMBER_OF_COLUMNS = 7;

    public CalcLayout(int gap) {
        this.gapWidth = gap;
        componentMap = new HashMap<>();
    }

    public CalcLayout() {
        this(0);
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
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

    @Override
    public void removeLayoutComponent(Component comp) {
        componentMap.remove(comp);
    }

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

            if(position.equals(RCPosition.RCPOSITION1_1)) {
                cWidth = columnWidth * 5 + gapWidth * 4;
                for(int index : uniformColumnIndexes) {
                    if(index > 5) break;
                    cWidth++;
                }
            } else if (uniformColumnIndexes.contains(position.getColumn())) {
                cWidth++;
            }
            c.setBounds(cx, cy, cWidth, cHeight);
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return calcLayoutSize(target, Component::getMaximumSize);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return calcLayoutSize(parent, Component::getMinimumSize);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return calcLayoutSize(parent, Component::getPreferredSize);
    }


    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException("Adding layout component with name is not supported.");
    }

    @Override
    public void invalidateLayout(Container target) {
    }

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
                if (componentMap.get(c).equals(RCPosition.RCPOSITION1_1)) {
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

    private Set<Integer> getUniformRowIndexes(int numberOfRows) {
        switch (numberOfRows) {
            case 0 -> {
                return Collections.emptySet();
            }
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
                throw new IllegalArgumentException("Invalid number of rows");
            }
        }
    }

    private Set<Integer> getUniformColumnIndexes(int numberOfRows) {

        switch (numberOfRows) {
            case 0 -> {
                return Collections.emptySet();
            }
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
                throw new IllegalArgumentException("Invalid number of columns");
            }
        }
    }
}
