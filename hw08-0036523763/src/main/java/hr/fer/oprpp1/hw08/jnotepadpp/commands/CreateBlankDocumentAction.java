package hr.fer.oprpp1.hw08.jnotepadpp.commands;

import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CreateBlankDocumentAction extends AbstractAction {
    private MultipleDocumentModel model;
    /**
     * Creates an {@code Action}.
     */
    public CreateBlankDocumentAction(MultipleDocumentModel model) {
        this.model = model;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.createNewDocument();
    }
}
