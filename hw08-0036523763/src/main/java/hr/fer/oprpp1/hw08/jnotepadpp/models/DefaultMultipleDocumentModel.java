package hr.fer.oprpp1.hw08.jnotepadpp.models;

import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.MultipleDocumentListenerNotifier;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

/**
 * Default implementation of {@link MultipleDocumentModel}
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
    /**
     * underlying list of documents
     */
    List<SingleDocumentModel> documents;
    /**
     * current document reference
     */
    SingleDocumentModel currentDocument;
    /**
     * underlying listener set
     */
    Set<MultipleDocumentListener> listeners;

    /**
     * Creates an empty <code>TabbedPane</code> with a default
     * tab placement of <code>JTabbedPane.TOP</code>.
     *
     * @see #addTab
     */
    public DefaultMultipleDocumentModel() {
        documents = new ArrayList<>();
        currentDocument = null;
        listeners =new HashSet<>();
        this.addChangeListener(e -> {
            if (getSelectedIndex() < 0) {
                setCurrentDocument(null);
                return;
            }
            setCurrentDocument(getDocument(getSelectedIndex()));
            Path p = currentDocument.getFilePath();
            //System.out.println("Current Document: " + (p == null ? "Unnamed" : p.getFileName().toString()));
        });
    }

    /**
     * Gets visual component of this model.
     *
     * @return {@link JTabbedPane} for this model
     */
    @Override
    public JComponent getVisualComponent() {
        return this;
    }

    /**
     * Creates new document and returns its model.
     *
     * @return {@link SingleDocumentModel} of new document
     */
    @Override
    public SingleDocumentModel createNewDocument() {
        SingleDocumentModel doc = new DefaultSingleDocumentModel(null, "");
        documents.add(doc);
        this.addTab("", wrapTextComponent(doc.getTextComponent()));
        setSelectedIndex(getIndexOfDocument(doc));
        notifyListeners((l -> l.documentAdded(doc)));
        return doc;
    }

    /**
     * Gets the model of the document the user is currently working on.
     *
     * @return {@link SingleDocumentModel} of current document
     */
    @Override
    public SingleDocumentModel getCurrentDocument() {
        return currentDocument;
    }

    /**
     * Loads document from passed {@link Path}.
     *
     * @param path document path
     * @return {@link SingleDocumentModel} of loaded document
     * @throws NullPointerException if passed {@code path} is null
     */
    @Override
    public SingleDocumentModel loadDocument(Path path) {
        Objects.requireNonNull(path);
        SingleDocumentModel oldDoc = findForPath(path);
        if (oldDoc != null) {
            setSelectedIndex(getIndexOfDocument(oldDoc));
            return oldDoc;
        }

        StringBuilder text = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(Files.newInputStream(path)), StandardCharsets.UTF_8))) {
            String l;
            while (true) {
                l = br.readLine();
                if (l == null) break;
                text.append(l).append("\n");
            }
        } catch (IOException ignored) {
            return null;
        }
        SingleDocumentModel doc = new DefaultSingleDocumentModel(path, text.toString());
        documents.add(doc);
        addTab("", wrapTextComponent(doc.getTextComponent()));
        setSelectedIndex(getIndexOfDocument(doc));
        notifyListeners((l -> l.documentAdded(doc)));
        return doc;
    }

    /**
     * Saves document described by passed model to passed path {@code newPath}.
     * If the passed path is the same as the path in the document model,
     * the file at that path is overwritten.
     * Otherwise the document will be saved in the file specified by new path ("Save as" behavior).
     *
     * @param model   model of document which should be saved
     * @param newPath path where the document should be saved
     * @throws NullPointerException if passed {@code model} is null
     * @throws IllegalArgumentException if specified path is already open
     */
    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        Objects.requireNonNull(model);
        if (newPath == null) newPath = model.getFilePath();
        SingleDocumentModel other = findForPath(newPath);
        if (other != null)
            if (!other.equals(model)) throw new IllegalArgumentException("File specified by newPath is already open.");

        try (BufferedWriter fileOut = new BufferedWriter(new FileWriter(newPath.toFile()))) {
            model.getTextComponent().write(fileOut);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * Closes document specified by passed model.
     *
     * @param model {@link SingleDocumentModel} model of the document which should be closed.
     */
    @Override
    public void closeDocument(SingleDocumentModel model) {
        removeTabAt(getIndexOfDocument(model));
        if(documents.remove(model)){
            notifyListeners((l -> l.documentRemoved(model)));
        }
    }

    /**
     * Subscribes passed {@link MultipleDocumentListener} to listen for changes in this model.
     * The listener is notified when a document is added or removed, or the current document changed.
     *
     * @param l listener to be subscribed
     */
    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    /**
     * Removes passed listener from underlying list of listeners for this model,
     * if the listener is subscribed.
     *
     * @param l listener to be removed
     */
    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);
    }

    /**
     * Gets the number of currently opened documents.
     *
     * @return number of currently opened documents.
     */
    @Override
    public int getNumberOfDocuments() {
        return documents.size();
    }

    /**
     * Gets model of document open at specified index.
     *
     * @param index document index
     * @return {@link SingleDocumentModel} model of document opened at specified index
     */
    @Override
    public SingleDocumentModel getDocument(int index) {
        return documents.get(index);
    }

    /**
     * Finds model of open document with the specified path.
     * If no such document exists, null is returned
     *
     * @param path path of sought document
     * @return {@link SingleDocumentModel} model of document if document exists, null otherwise
     * @throws NullPointerException if passed {@code path} is null
     */
    @Override
    public SingleDocumentModel findForPath(Path path) {
        Objects.requireNonNull(path);
        for (var doc : documents) {
            Path otherPath = doc.getFilePath();
            if(otherPath != null) {
                if (otherPath.equals(path)) return doc;
            }
        }
        return null;
    }

    /**
     * Gets index of open document whose model was passed,
     * if no such document exists returns -1
     *
     * @param doc model of document whose index is sought after
     * @return document index if found, -1 otherwise
     */
    @Override
    public int getIndexOfDocument(SingleDocumentModel doc) {
        return documents.indexOf(doc);
    }

    /**
     * Returns an iterator over {@link SingleDocumentModel} models of this
     * {@link MultipleDocumentModel}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return documents.iterator();
    }

    /**
     * Notifies subscribed listeners with passed notifier.
     * @param notifier listener notifier
     */
    private void notifyListeners(MultipleDocumentListenerNotifier notifier) {
        for(var listener : listeners) {
            notifier.notify(listener);
        }
    }

    /**
     * Sets current document and notifies listeners.
     * @param document new current document
     */
    private void setCurrentDocument(SingleDocumentModel document) {
        SingleDocumentModel prev = this.currentDocument;
        this.currentDocument = document;
        notifyListeners((l -> l.currentDocumentChanged(prev, this.currentDocument)));
    }

    /**
     * Wraps passed {@link JTextComponent} into {@link JScrollPane}.
     * @param comp component to be wrapped
     * @return wrapped component
     */
    private JComponent wrapTextComponent(JTextComponent comp) {
        return new JScrollPane(comp);
    }
}
