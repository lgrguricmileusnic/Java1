package hr.fer.oprpp1.hw08.jnotepadpp.models.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

/**
 * Listener for {@link hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel}
 */
public interface MultipleDocumentListener {
    /**
     * Invoked when current document changes.
     * @param previousModel previous document model
     * @param currentModel current document model
     */
    void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

    /**
     * Invoked when a new document was added.
     * @param model new document model
     */
    void documentAdded(SingleDocumentModel model);
    /**
     * Invoked when a document was removed.
     * @param model removed document model
     */
    void documentRemoved(SingleDocumentModel model);
}
