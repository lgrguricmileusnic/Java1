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
}
