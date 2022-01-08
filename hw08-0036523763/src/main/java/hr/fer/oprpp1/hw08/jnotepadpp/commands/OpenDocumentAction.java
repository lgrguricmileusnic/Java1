package hr.fer.oprpp1.hw08.jnotepadpp.commands;

import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

public class OpenDocumentAction extends AbstractAction {
    private MultipleDocumentModel model;

    /**
     * Creates an {@code Action}.
     */
    public OpenDocumentAction(MultipleDocumentModel model) {
        this.model = model;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(model.getVisualComponent()) == JFileChooser.APPROVE_OPTION) {
            Path path = fc.getSelectedFile().toPath();
            model.loadDocument(path);
        }
    }
}
