package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

public class BarChart {
    private List<XYValue> values;
    private String xTitle;
    private String yTitle;
    private int ymin;
    private int ymax;
    private short unitSegment;

    public BarChart(List<XYValue> values, String xTitle, String yTitle, int ymin, int ymax, short unitSegment) {
        Objects.requireNonNull(values);
        Objects.requireNonNull(values);
        Objects.requireNonNull(values);
        if(ymin < 0) throw new IllegalArgumentException("Value of ymin can not be less than zero");
        if(ymax < ymin) throw new IllegalArgumentException("Value of ymax can not be less than ymin");
        if(unitSegment <= 0) throw new IllegalArgumentException("Unit segment length must be greater than zero");
        for(var value : values) {
            if(value.getY() < ymin) throw new IllegalArgumentException("Passed list contains XYValue with y value set to less than ymin");
        }
        this.values = values;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.ymin = ymin;
        this.ymax = ymax;
        this.unitSegment = unitSegment;
    }
}
