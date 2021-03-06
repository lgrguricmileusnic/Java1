package hr.fer.oprpp1.hw08.jnotepadpp.actions.file;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.utils.HelperMethods;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Action saves current document, if document has no path, opens {@link JFileChooser} and saves to chosen location.
 */
public class SaveDocumentAction extends LocalizableAction {
    private MultipleDocumentModel model;
    private ILocalizationProvider lp;

    /**
     * Creates {@code SaveDocumentAction} for passed model and localization key and provider.
     * @param key localization key
     * @param lp localization provider
     * @param model multiple document model
     */
    public SaveDocumentAction(String key, MultipleDocumentModel model, ILocalizationProvider lp) {
        super(key, lp);
        this.lp = lp;
        this.model = model;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
    }

    /**
     * Invoked when an action occurs.
     * When invoked, saves current document,
     * if document has no path opens {@link JFileChooser} and saves to new location.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SingleDocumentModel doc = model.getCurrentDocument();
        HelperMethods.saveDocumentExtended(model, doc, lp);
    }
}
