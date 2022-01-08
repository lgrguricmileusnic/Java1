package hr.fer.oprpp1.hw08.jnotepadpp.commands;

import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveAsDocumentAction extends AbstractAction {
    private MultipleDocumentModel model;

    /**
     * Creates an {@code Action}.
     */
    public SaveAsDocumentAction(MultipleDocumentModel model) {
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
        if (fc.showSaveDialog(model.getVisualComponent()) == JFileChooser.APPROVE_OPTION) {
            Path path = fc.getSelectedFile().toPath();
            if(Files.isRegularFile(path)) {
                JOptionPane.showMessageDialog(model.getVisualComponent(),"File already exists!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    model.saveDocument(model.getCurrentDocument(),path);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(model.getVisualComponent(),"File already open!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
