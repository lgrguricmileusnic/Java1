package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 */
public class Node {
    /**
     * collection that stores all child nodes
     */
    private ArrayIndexedCollection childNodes;

    /**
     * Adds passed {@code child} to this node's children.
     * @param child child node which will be added
     */
    public void addChildNode(Node child) {
        if (childNodes == null) childNodes = new ArrayIndexedCollection();
        childNodes.add(child);
    }

    /**
     * Gets the number of this node's children
     * @return number of children
     */
    public int numberOfChildren() {
        if(childNodes == null) return 0;
        return childNodes.size();
    }

    /**
     * Gets child node at passed {@code index}.
     * @param index specified {@code index}
     * @return child node at passed {@code index}
     */
    public Node getChild(int index) {
        return (Node) childNodes.get(index);
    }
}
