package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.HashMap;

/**
 * Root node of each document.
 */
public class DocumentNode extends Node{
    @Override
    public String toString() {
        String output = "";
        Node child;
        for (int i = 0; i < numberOfChildren(); i++) {
            child = getChild(i);
            if(child instanceof TextNode) {
                output += ((TextNode) child).getText();
            } else {
                output += child.toString();
            }
        }
        return output;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DocumentNode ) {
            if(((DocumentNode) obj).numberOfChildren() == this.numberOfChildren()){
                for (int i = 0; i < numberOfChildren(); i++) {
                    if(!((DocumentNode) obj).getChild(i).equals(this.getChild(i))) return false;
                }
                return true;
            }
        }
        return false;
    }
}
