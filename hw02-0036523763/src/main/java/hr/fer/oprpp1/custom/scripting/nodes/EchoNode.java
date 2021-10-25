package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Echo node representation.
 */
public class EchoNode extends Node{
    /**
     * {@code EchoNode} elements storage
     */
    private final Element[] elements;

    /**
     * Constructs {@code EchoNode} instance and
     * initialises {@code elements} data member to passed {@code Element} array.
     * @param elements elements of {@code EchoNode}
     */
    public EchoNode(Element[] elements){
        this.elements = elements;
    }

    /**
     * Gets elements of this {@code EchoNode}
     * @return array of this node's elements
     */
    public Element[] getElements() {
        return elements;
    }

    /**
     * Creates string representation of this {@code EchoNode}.
     * Representation is semantically identical to its original.
     * @return string representation of this {@code EchoNode}
     */
    @Override
    public String toString() {
        String output = "{$= ";
        for (int i = 0; i < elements.length; i++) {
            output += elements[i].asText();
            output += ' ';
        }
        return output + "$}";
    }

    /**
     * Indicates if the passed object is a {@code EchoNode} whose content is identical to
     * this {@code EchoNode}
     * @param obj object that is being tested
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EchoNode) {
            if(((EchoNode) obj).getElements().length == elements.length) {
                for (int i = 0; i < elements.length ; i++) {
                    if(!Arrays.equals(elements,((EchoNode) obj).getElements())) return false;
                }
                return true;
            }
        }
        return false;
    }
}
