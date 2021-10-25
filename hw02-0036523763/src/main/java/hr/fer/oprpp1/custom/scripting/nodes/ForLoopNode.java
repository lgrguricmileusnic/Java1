package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

import java.util.Objects;

/**
 * Node representation of a for loop tag.
 */
public class ForLoopNode extends Node {
    private final ElementVariable variable;
    private final Element startExpression;
    private final Element endExpression;
    private final Element stepExpression;

    /**
     * Constructor which initialises all data members (stepExpression can be null).
     *
     * @param variable        for loop variable
     * @param startExpression for loop start
     * @param endExpression   for loop end
     * @param stepExpression  for loop step
     * @throws NullPointerException if any paramater except {@code stepExpression} is null
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
        Objects.requireNonNull(variable);
        Objects.requireNonNull(startExpression);
        Objects.requireNonNull(endExpression);
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * Gets for loop variable
     *
     * @return for loop variable
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Gets for loop start expression.
     *
     * @return for loop start expression
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Gets for loop end expression
     *
     * @return for loop end expression
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Gets for loop step expression
     *
     * @return for loop step expression
     */
    public Element getStepExpression() {
        return stepExpression;
    }

    /**
     * Returns a string representation of this node
     *
     * @return string representation of this node
     */
    @Override
    public String toString() {
        String output = "";
        String stepExpressionString = "";
        if(getStepExpression() != null) stepExpressionString = getStepExpression().asText() + " ";
        output += "{$ FOR " + getVariable().asText() + " " + getStartExpression().asText() + " " + getEndExpression().asText() + " " + stepExpressionString + "$}";
        for (int i = 0; i < numberOfChildren(); i++) {
            output += getChild(i).toString();
        }
        output += "{$END$}";
        return output;
    }

    /**
     * Indicates if the passed object is a {@code ForLoopNode} whose arguments and content are identical to
     * this {@code ForLoopNode}
     * @param obj object that is being tested
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ForLoopNode) {
            if (((ForLoopNode) obj).numberOfChildren() == numberOfChildren()) {
                for (int i = 0; i < numberOfChildren(); i++) {
                    if (!this.getChild(i).equals(((ForLoopNode) obj).getChild(i))) return false;
                }
                return this.endExpression.equals(((ForLoopNode) obj).getEndExpression()) &&
                        this.startExpression.equals(((ForLoopNode) obj).getStartExpression()) &&
                        this.stepExpression.equals(((ForLoopNode) obj).getStepExpression()) &&
                        this.variable.equals(((ForLoopNode) obj).getVariable());
            }
        }
        return false;
    }
}
