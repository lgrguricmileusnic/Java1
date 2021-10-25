package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Representation of a double constant.
 */
public class ElementConstantDouble extends Element{
    /**
     * value of this double constant
     */
    private final double value;

    /**
     * Constructor which sets the value of this constant.
     * @param value value to be set
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * Gets string representation of this element.
     *
     * @return string representation of this element
     */
    @Override
    public String asText() {
        return Double.toString(value);
    }

    /**
     * Gets double value of this element.
     * @return double value of this element
     */
    public double getValue() {
        return value;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj object that is being tested
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ElementConstantDouble) {
            return this.getValue() == ((ElementConstantDouble) obj).getValue();
        }
        return false;
    }
}
