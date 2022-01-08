package hr.fer.oprpp1.hw08.jnotepadpp.models.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

public interface SingleDocumentListener {
    void documentModifyStatusUpdated(SingleDocumentModel model);
    void documentFilePathUpdated(SingleDocumentModel model);
}