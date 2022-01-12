package hr.fer.oprpp1.hw08.jnotepadpp.actions.file;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
/**
 * Action which creates a new {@link hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel} in passed {@link MultipleDocumentModel}.
 * Extends {@link LocalizableAction} so dynamic localization is supported.
 */
public class CreateBlankDocumentAction extends LocalizableAction {
    /**
     * model reference
     */
    private MultipleDocumentModel model;
    /**
     * Creates an {@code CreateBlankDocumentAction} for passed model, localization key and provider.
     * Sets ACCELERATOR_KEY to control N.
     * @param key localization key
     * @param lp localization provider
     * @param model multiple document model
     */
    public CreateBlankDocumentAction(String key, MultipleDocumentModel model, ILocalizationProvider lp) {
        super(key, lp);
        this.model = model;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
    }

    /**
     * When invoked, creates new document.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.createNewDocument();
    }
}
