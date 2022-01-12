package hr.fer.oprpp1.hw08.jnotepadpp.models.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
/**
 * Listener for {@link SingleDocumentModel}
 */
public interface SingleDocumentListener {
    /**
     * Invoked when modified flag was updated.
     * @param model model which was modified
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);
    /**
     * Invoked when file path was updated.
     * @param model model which was modified
     */
    void documentFilePathUpdated(SingleDocumentModel model);
}