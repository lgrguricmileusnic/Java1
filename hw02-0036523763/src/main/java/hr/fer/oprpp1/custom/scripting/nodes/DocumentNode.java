package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.HashMap;

/**
 * Root node of each document.
 */
public class DocumentNode extends Node {
    /**
     * Creates string representation of parsed document by calling {@code toString} on
     * its children.
     * The result is only semantically identical to the input.
     *
     * @return string representation of parsed document
     */
    @Override
    public String toString() {
        String output = "";
        Node child;
        for (int i = 0; i < numberOfChildren(); i++) {
            child = getChild(i);
            output += child.toString();

        }
        return output;
    }

    /**
     * Indicates if the passed object is a {@code DocumentNode} that is structurally identical to
     * this {@code DocumentNode}
     * @param obj object that is being tested
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DocumentNode) {
            if (((DocumentNode) obj).numberOfChildren() == this.numberOfChildren()) {
                for (int i = 0; i < numberOfChildren(); i++) {
                    if (!((DocumentNode) obj).getChild(i).equals(this.getChild(i))) return false;
                }
                return true;
            }
        }
        return false;
    }
}
