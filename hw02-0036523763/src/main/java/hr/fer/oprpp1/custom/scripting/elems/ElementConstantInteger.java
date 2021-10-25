package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantInteger extends Element{
    /**
     * value of this integer constant
     */
    private final int value;

    /**
     * Constructor which sets the value of this constant.
     * @param value value to be set
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * Gets string representation of this element.
     *
     * @return string representation of this element
     */
    @Override
    public String asText() {
        return Integer.toString(value);
    }

    /**
     * Gets double value of this element.
     * @return double value of this element
     */
    public int getValue() {
        return value;
    }


    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj object that is being tested
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ElementConstantInteger) {
            return ((ElementConstantInteger) obj).getValue() == getValue();
        }
        return false;
    }
}
