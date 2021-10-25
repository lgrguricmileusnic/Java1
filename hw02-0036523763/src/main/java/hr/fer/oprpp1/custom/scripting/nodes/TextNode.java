package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Text node representation.
 */
public class TextNode extends Node{
    /**
     * text stored in this node
     */
    private final String text;

    /**
     * Constructor which stores the passed text in this node.
     * @param text text to be stored
     */
    public TextNode(String text) {
        this.text = text;
    }

    /**
     * Gets node text.
     * @return text
     */
    public String getText() {
        return text;
    }


    /**
     * Gets string representation of this node.
     * @return string representation of this node
     */
    @Override
    public String toString() {
        return text;
    }


    /**
     * Indicates if the passed object is a {@code TextNode} whose content is identical to
     * this {@code TextNode}.
     * @param obj object that is being tested
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TextNode) {
            return this.text.equals(((TextNode) obj).getText());
        }
        return false;
    }
}
