package hr.fer.oprpp1.hw08.jnotepadpp.commands;

import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CloseDocumentAction extends AbstractAction {
    private MultipleDocumentModel model;

    /**
     * Creates an {@code Action}.
     */
    public CloseDocumentAction(MultipleDocumentModel model) {
        this.model = model;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.closeDocument(model.getCurrentDocument());
    }
}
