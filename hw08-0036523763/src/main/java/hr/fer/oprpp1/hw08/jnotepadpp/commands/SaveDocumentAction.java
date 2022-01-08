package hr.fer.oprpp1.hw08.jnotepadpp.commands;

import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveDocumentAction extends AbstractAction {
    private MultipleDocumentModel model;

    /**
     * Creates an {@code Action}.
     */
    public SaveDocumentAction(MultipleDocumentModel model) {
        this.model = model;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SingleDocumentModel doc = model.getCurrentDocument();
        if (doc.getFilePath() == null) {
            (new SaveAsDocumentAction(model)).actionPerformed(null);
            return;
        }
        model.saveDocument(doc, doc.getFilePath());
    }
}
