package hr.fer.zemris.java.gui.layouts;

import java.awt.*;

public class CalcLayout implements LayoutManager2 {


    @Override
    public void addLayoutComponent(Component comp, Object constraints) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public void layoutContainer(Container parent) {

    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
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
}
