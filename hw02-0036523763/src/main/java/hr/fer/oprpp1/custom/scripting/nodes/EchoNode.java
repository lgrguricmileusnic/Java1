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

    private final Element[] elements;

    public EchoNode(Element[] elements){
        this.elements = elements;
    }

    public Element[] getElements() {
        return elements;
    }

    @Override
    public String toString() {
        String output = "{$= ";
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] instanceof ElementString) {
                output += '"' + elements[i].asText() + '"';
            } else if (elements[i] instanceof ElementFunction) {
                output += '@' + elements[i].asText();
            } else {
                output += elements[i].asText();
            }
            output += ' ';
        }
        return output + "$}";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EchoNode) {
            if(((EchoNode) obj).getElements().length == elements.length) {
                for (int i = 0; i < elements.length ; i++) {
                    if(!Arrays.equals(elements,((EchoNode) obj).getElements())) return false;
                }
            }
            return true;
        }
        return false;
    }
}
