package hr.fer.oprpp1.hw08.jnotepadpp.models.listeners;

import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

public interface MultipleDocumentListener {
    void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
    void documentAdded(SingleDocumentModel model);
    void documentRemoved(SingleDocumentModel model);
}
