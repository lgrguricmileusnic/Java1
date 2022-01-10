package hr.fer.oprpp1.hw08.jnotepadpp.actions.file;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CreateBlankDocumentAction extends LocalizableAction {
    private MultipleDocumentModel model;
    /**
     * Creates an {@code Action}.
     */
    public CreateBlankDocumentAction(String key, MultipleDocumentModel model, ILocalizationProvider lp) {
        super(key, lp);
        this.model = model;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
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
