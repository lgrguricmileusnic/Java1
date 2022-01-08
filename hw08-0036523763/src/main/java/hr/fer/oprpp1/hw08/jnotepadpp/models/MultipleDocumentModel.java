package hr.fer.oprpp1.hw08.jnotepadpp.models;

import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.MultipleDocumentListener;

import javax.swing.*;
import java.nio.file.Path;

/**
 * This interface describes a model capable of holding zero, one or more documents,
 * where each document and having a concept of current document – the one which is shown to the
 * user and on which user works.
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
    /**
     * Gets visual component of this model.
     * @return {@link JTabbedPane} for this model
     */
    JComponent getVisualComponent();

    /**
     * Creates new document and returns its model.
     * @return {@link SingleDocumentModel} of new document
     */
    SingleDocumentModel createNewDocument();

    /**
     * Gets the model of the document the user is currently working on.
     * @return {@link SingleDocumentModel} of current document
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * Loads document from passed {@link Path}.
     * @param path document path
     * @return {@link SingleDocumentModel} of loaded document
     */
    SingleDocumentModel loadDocument(Path path);

    /**
     * Saves document described by passed model to passed path {@code newPath}.
     * If the passed path is the same as the path in the document model,
     * the file at that path is overwritten.
     * Otherwise the document will be saved in the file specified by new path ("Save as" behavior).
     * @param model model of document which should be saved
     * @param newPath path where the document should be saved
     */
    void saveDocument(SingleDocumentModel model, Path newPath);

    /**
     * Closes document specified by passed model.
     * @param model {@link SingleDocumentModel} model of the document which should be closed.
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * Subscribes passed {@link MultipleDocumentListener} to listen for changes in this model.
     * The listener is notified when a document is added or removed, or the current document changed.
     * @param l listener to be subscribed
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Removes passed listener from underlying list of listeners for this model,
     * if the listener is subscribed.
     *
     * @param l listener to be removed
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Gets the number of currently opened documents.
     * @return number of currently opened documents.
     */
    int getNumberOfDocuments();

    /**
     * Gets model of document open at specified index.
     * @param index document index
     * @return {@link SingleDocumentModel} model of document opened at specified index
     */
    SingleDocumentModel getDocument(int index);

    /**
     * Finds model of open document with the specified path.
     * If no such document exists, null is returned
     * @param path path of sought document
     * @return {@link SingleDocumentModel} model of document if document exists, null otherwise
     */
    SingleDocumentModel findForPath(Path path); //null, if no such model exists

    /**
     * Gets index of open document whose model was passed,
     * if no such document exists returns -1
     * @param doc model of document whose index is sought after
     * @return document index if found, -1 otherwise
     */
    int getIndexOfDocument(SingleDocumentModel doc); //-1 if not present
}
