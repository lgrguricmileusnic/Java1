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
     * @param name
     */
    public ElementFunction(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this function.
     *
     * @return string representation of this element
     */
    @Override
    public String asText() {
        return name;
    }
}
