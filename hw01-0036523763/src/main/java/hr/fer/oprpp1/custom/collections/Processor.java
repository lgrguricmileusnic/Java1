package hr.fer.oprpp1.custom.collections;

/**
 * Model of an object which performs one action on passed object.
 * The action must be defined in the process method, when implementing subclasses of the processor.
 */
public class Processor {
    /**
     * Action to be performed on the passed object.
     * @param value reference of the object on which the action will be performed
     */
    public void process(Object value) {};
}
