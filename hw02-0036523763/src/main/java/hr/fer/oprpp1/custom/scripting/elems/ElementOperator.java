package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Representation of a symbol element.
 */
public class ElementOperator extends Element{
    /**
     * value of this symbol element
     */
    private final String symbol;

    /**
     * Constructor which sets the value of this symbol.
     * @param symbol value to be set
     */
    public ElementOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets string representation of this element.
     *
     * @return string representation of this element
     */
    @Override
    public String asText() {
        return symbol;
    }

    /**
     * Gets operator symbol.
     * @return operator symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj object that is being tested
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ElementOperator) {
            return this.asText().equals(((ElementOperator) obj).asText());
        }
        return false;
    }
}
