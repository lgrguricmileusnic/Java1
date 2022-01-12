package hr.fer.oprpp1.hw08.jnotepadpp.models;

import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.MultipleDocumentListenerNotifier;
import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.SingleDocumentListenerNotifier;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Default implementation of {@link SingleDocumentModel}
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel{
    /**
     * document file path
     */
    private Path filePath;
    /**
     * this model belonging text component
     */
    private JTextArea textComponent;
    /**
     * modified state
     */
    private boolean modified;
    /**
     * underlying listener set
     */
    private Set<SingleDocumentListener> listeners = new HashSet<>();

    /**
     * Constructs {@code DefaultSingleDocumentModel} for document with {@code filePath}.
     * Initialises belonging {@link JTextArea} and sets it's text to {@code textContent}
     * @param filePath document file path
     * @param textContent initial text content
     */
    public DefaultSingleDocumentModel(Path filePath, String textContent) {
        this.filePath = filePath;
        textComponent = new JTextArea(textContent);
        textComponent.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setModified(true);
            }
        });
    }

    /**
     * Getter for this document's text component.
     *
     * @return {@link JTextArea} component for this {@code SingleDocumentModel}
     */
    @Override
    public JTextArea getTextComponent() {
        return textComponent;
    }

    /**
     * Gets {@link Path} for loaded document.
     *
     * @return {@link Path} object for this document
     */
    @Override
    public Path getFilePath() {
        return filePath;
    }

    /**
     * Sets {@link Path} for this document.
     *
     * @param path new document path
     */
    @Override
    public void setFilePath(Path path) {
        filePath = path;
        notifyListeners(l -> l.documentFilePathUpdated(this));
    }

    /**
     * Returns model's modified state.
     *
     * @return true if modified, false otherwise
     */
    @Override
    public boolean isModified() {
        return modified;
    }

    /**
     * Sets model's modified state to the passed value.
     *
     * @param modified new modified state
     */
    @Override
    public void setModified(boolean modified) {
        this.modified = modified;
        notifyListeners((l) -> l.documentModifyStatusUpdated(this));
    }

    /**
     * Subscribes passed {@link SingleDocumentListener} to listen for changes in this model.
     *
     * @param l listener to be subscribed
     */
    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(l);
    }

    /**
     * Removes passed listener from underlying list of listeners for this model,
     * if the listener is subscribed.
     *
     * @param l listener to be removed
     */
    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(l);
    }

    /**
     * Notifies listeners with passed notifier.
     * @param notifier listener notifier
     */
    private void notifyListeners(SingleDocumentListenerNotifier notifier) {
        for(var listener : listeners) {
            notifier.notify(listener);
        }
    }

}
