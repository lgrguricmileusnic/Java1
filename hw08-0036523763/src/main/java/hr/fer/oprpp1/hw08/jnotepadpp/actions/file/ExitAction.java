package hr.fer.oprpp1.hw08.jnotepadpp.actions.file;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.utils.HelperMethods;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

/**
 * Action which disposes passed frame, after checking for modified documents.
 */
public class ExitAction extends LocalizableAction {
    /**
     * frame reference
     */
    private JFrame frame;
    /**
     * multiple document model reference
     */
    private MultipleDocumentModel model;
    /**
     * localization provider for initial and dynamic localization
     */
    private ILocalizationProvider lp;
    /**
     * Creates an {@code CloseDocumentAction} and sets up ACCELERATOR_KEY to be control Q.
     *
     * @param key localization key
     * @param lp localization provider
     * @param model multiple document model
     * @param frame frame which should be disposed
     */
    public ExitAction(String key, MultipleDocumentModel model, ILocalizationProvider lp, JFrame frame) {
        super(key, lp);
        this.frame = frame;
        this.model = model;
        this.lp = lp;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
    }

    /**
     * When invoked, checks for open modified documents.
     * If there are none, disposes the frame, otherwise if a document was modified displays a {@link JOptionPane} option
     * dialog to double-check if the user wishes to exit without saving.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(HelperMethods.checkModifiedDocuments(model, lp)) {
            frame.dispose();
        };
    }
}
