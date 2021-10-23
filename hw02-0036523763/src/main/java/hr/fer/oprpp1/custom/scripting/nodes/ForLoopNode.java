package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

import java.util.Objects;

/**
 * Node representation of a for loop tag.
 */
public class ForLoopNode extends Node{
    private final ElementVariable variable;
    private final Element startExpression;
    private final Element endExpression;
    private final Element stepExpression;

    /**
     * Constructor which initialises all data members (stepExpression can be null).
     * @param variable for loop variable
     * @param startExpression for loop start
     * @param endExpression for loop end
     * @param stepExpression for loop step
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
     * @return for loop variable
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Gets for loop start expression.
     * @return for loop start expression
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Gets for loop end expression
     * @return for loop end expression
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Gets for loop step expression
     * @return for loop step expression
     */
    public Element getStepExpression() {
        return stepExpression;
    }
}
