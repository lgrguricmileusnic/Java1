package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Representation of a variable element.
 */
public class ElementVariable extends Element{
    /**
     * name of this variable
     */
    private final String name;

    /**
     * Constructor that sets this variable's name.
     * @param name name of this variable
     */
    public ElementVariable(String name) {
        this.name = name;
    }

    /**
     * Gets string representation of this element.
     *
     * @return string representation of this element
     */
    @Override
    public String asText() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ElementVariable) {
            return this.asText().equals(((ElementVariable) obj).asText());
        }
        return false;
    }
}
