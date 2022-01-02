package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

/**
 * This class is a representation of a bar chart's data.
 * Instances of this class store bar chart values, axis titles, minimum and maximum y values and unit segment length.
 */
public class BarChart {
    /**
     * underlying list storing {@link XYValue} objects for this bar chart.
     */
    private List<XYValue> values;
    /**
     * title of the x axis
     */
    private String xTitle;
    /**
     * title of the y axis
     */
    private String yTitle;
    /**
     * minimum y value
     */
    private int ymin;
    /**
     * maximum y value
     */
    private int ymax;
    /**
     * unit segment
     */
    private int unitSegment;

    /**
     * Constructs a {@code BarChart} object with passed parameters.
     * @param values values
     * @param xTitle x axis title
     * @param yTitle y axis title
     * @param ymin minimum y value
     * @param ymax maximum y value
     * @param unitSegment lenght of the unit segment
     * @throws IllegalArgumentException if one of the following occurs
     *                                      -ymin is less than zero
     *                                      -ymax is less than ymin
     *                                      -unitSegment is less than zero
     * @throws NullPointerException if values, xTitle or yTitle is null
     */
    public BarChart(List<XYValue> values, String xTitle, String yTitle, int ymin, int ymax, int unitSegment) {
        Objects.requireNonNull(values);
        Objects.requireNonNull(values);
        Objects.requireNonNull(values);
        if(ymin < 0) throw new IllegalArgumentException("Value of ymin can not be less than zero");
        if(ymax < ymin) throw new IllegalArgumentException("Value of ymax can not be less than ymin");
        if(unitSegment < 0) throw new IllegalArgumentException("Unit segment length must be greater than zero");
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

    /**
     * Gets bar chart values.
     * @return values
     */
    public List<XYValue> getValues() {
        return values;
    }

    /**
     * Gets x axis title
     * @return x axis title
     */
    public String getXTitle() {
        return xTitle;
    }

    /**
     * Gets y axis title.
     * @return y axis title
     */
    public String getYTitle() {
        return yTitle;
    }

    /**
     * Gets minimum y value.
     * @return ymin
     */
    public int getYMin() {
        return ymin;
    }

    /**
     * Gets maximum y value.
     * @return ymax
     */
    public int getYMax() {
        return ymax;
    }

    /**
     * Gets unit segment length.
     * @return unit segment length.
     */
    public int getUnitSegment() {
        return unitSegment;
    }
}
