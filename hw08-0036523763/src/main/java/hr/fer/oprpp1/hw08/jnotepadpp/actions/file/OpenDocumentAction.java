package hr.fer.oprpp1.hw08.jnotepadpp.actions.file;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

/**
 * Action which opens an existing document in passed {@link MultipleDocumentModel}.
 * Action extends {@link LocalizableAction} allowing for dynamic localization defined by {@code key}.
 */
public class OpenDocumentAction extends LocalizableAction {
    /**
     * multiple document model reference
     */
    private MultipleDocumentModel model;

    /**
     * Creates an {@code OpenDocument action} with passed model and localization key and provider.
     * Sets ACCELERATOR_KEY TO control O.
     * @param key localization key
     * @param lp localization provider
     * @param model multiple document model
     */
    public OpenDocumentAction(String key, MultipleDocumentModel model, ILocalizationProvider lp) {
        super(key, lp);
        this.model = model;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
    }

    /**
     * Opens file chooser, and loads chosen file.
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
