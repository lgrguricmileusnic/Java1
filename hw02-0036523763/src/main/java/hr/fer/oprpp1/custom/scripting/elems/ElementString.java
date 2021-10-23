package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Representation of a string element.
 */
public class ElementString extends Element{
    /**
     * value of this string
     */
    private final String value;

    /**
     * Constructor which sets the value of this string.
     * @param value value to be set
     */
    public ElementString(String value) {
        this.value = value;
    }

    /**
     * Gets string representation of this element.
     *
     * @return string representation of this element
     */
    @Override
    public String asText() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ElementString) {
            return this.asText().equals(((ElementString) obj).asText());
        }
        return false;
    }
}
