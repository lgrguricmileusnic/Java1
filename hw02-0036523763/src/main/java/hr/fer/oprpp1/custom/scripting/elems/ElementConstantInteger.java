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
}
