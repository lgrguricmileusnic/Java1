package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Representation of a function.
 */
public class ElementFunction extends Element{
    /**
     * function name
     */
    private final String name;

    /**
     * Constructor that sets this function's name.
     * @param name function name
     */
    public ElementFunction(String name) {
        this.name = name;
    }

    /**
     * Gets the string representation of this function.
     *
     * @return string representation of this function
     */
    @Override
    public String asText() {
        return '@' + name;
    }

    /**
     * Gets function name.
     * @return function name
     */
    public String getName() {
        return name;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj object that is being tested
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ElementFunction) {
            return this.asText().equals(((ElementFunction) obj).asText());
        }
        return false;
    }
}
