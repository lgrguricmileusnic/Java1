package hr.fer.oprpp1.hw08.jnotepadpp.models;


import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.SingleDocumentListener;

import javax.swing.*;
import java.nio.file.Path;

/**
 * This interface describes a model of a document and its belonging {@link JTextArea} component.
 */
public interface SingleDocumentModel {

    /**
     * Getter for this document's text component.
     *
     * @return {@link JTextArea} component for this {@code SingleDocumentModel}
     */
    JTextArea getTextComponent();

    /**
     * Gets {@link Path} for loaded document.
     *
     * @return {@link Path} object for this document
     */
    Path getFilePath();

    /**
     * Sets file {@link Path} for this model.
     *
     * @param path new path
     */
    void setFilePath(Path path);

    /**
     * Returns model's modified state.
     *
     * @return true if modified, false otherwise
     */
    boolean isModified();

    /**
     * Sets model's modified state to the passed value.
     *
     * @param modified new modified state
     */
    void setModified(boolean modified);

    /**
     * Subscribes passed {@link SingleDocumentListener} to listen for changes in this model.
     *
     * @param l listener to be subscribed
     */
    void addSingleDocumentListener(SingleDocumentListener l);

    /**
     * Removes passed listener from underlying list of listeners for this model,
     * if the listener is subscribed.
     *
     * @param l listener to be removed
     */
    void removeSingleDocumentListener(SingleDocumentListener l);
}
