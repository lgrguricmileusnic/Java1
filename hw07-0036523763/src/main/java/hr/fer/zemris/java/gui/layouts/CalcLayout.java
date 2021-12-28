package hr.fer.zemris.java.gui.layouts;

import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class CalcLayout implements LayoutManager2 {
    private final int gapWidth;
    private final Map<RCPosition,Component> componentMap;

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
        if(constraints instanceof String) {
            position = RCPosition.parse((String) constraints);
        } else if (constraints instanceof RCPosition) {
            position = (RCPosition) constraints;
        } else {
            throw new CalcLayoutException("Invalid constraint type: must be String or RCPosition");
        }

        if(position.getRow() < 1 || position.getRow() > 5 || position.getColumn() < 1 || position.getColumn() > 7) {
            throw new CalcLayoutException("Row or column position out of bounds");
        }

        if(position.getColumn() > 1 && position.getColumn() < 6 && position.getRow() == 1) {
            throw new CalcLayoutException("Only column indexes 1, 6, 7 can be used in the first row");
        }
        if(componentMap.containsKey(position)) {
            throw new CalcLayoutException("Component already set at position " + position);
        }

        componentMap.put(position, comp);
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        componentMap.remove(comp);
    }

    @Override
    public void layoutContainer(Container parent) {
        GridLayout
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
        Dimension dimension = new Dimension(0,0);
        Component[] components = parent.getComponents();
        int cMaxWidth = -1;
        int cMaxHeight = -1;

        for (Component c : components) {
            if(componentMap.containsValue(c)) {
                Dimension componentDimension = sizeGetter.apply(c);
                if(componentDimension == null) continue;

                cMaxHeight = Math.max(cMaxHeight, componentDimension.height);
                if(componentMap.get(new RCPosition(1,1)).equals(c)) {
                    continue;
                }
                dimension.width = Math.max(cMaxWidth, componentDimension.width);
            }
        }

        Insets insets = parent.getInsets();
        dimension.width = 6 * gapWidth + 7 * cMaxWidth + insets.left + insets.right;
        dimension.height = 4 * gapWidth + 5 * cMaxHeight + insets.top + insets.bottom;
        return dimension;
    }


}
