package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Representation of a string element.
 */
public class ElementString extends Element{
    /**
     * value of this string
     */
    private String value;

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
        String formattedOutput = "";
        Character current;
        for (int i = 0; i < value.length(); i++) {
            current = value.charAt(i);
            if(current == '"') {
                formattedOutput += "\\" + current;
            } else {
                formattedOutput += current;
            }
        }
        return '"' + formattedOutput + '"';
    }

    /**
     * Gets value of this element.
     * @return element value
     */
    public String getValue() {
        return value;
    }
    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj object that is being tested
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ElementString) {
            return this.asText().equals(((ElementString) obj).asText());
        }
        return false;
    }
}
