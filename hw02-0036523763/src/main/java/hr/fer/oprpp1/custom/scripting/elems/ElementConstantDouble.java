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
}
