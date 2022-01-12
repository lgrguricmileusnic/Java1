package hr.fer.oprpp1.hw08.jnotepadpp.actions.file;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action which closes current document in passed {@link MultipleDocumentModel}.
 * Extends {@link LocalizableAction} so dynamic localization is supported.
 */
public class CloseDocumentAction extends LocalizableAction {
    /**
     * {@link MultipleDocumentModel} reference
     */
    private MultipleDocumentModel model;
    /**
     * {@link ILocalizationProvider} reference for initial and dynamic localization
     */
    private ILocalizationProvider lp;
    /**
     * Creates an {@code CloseDocumentAction} and sets up ACCELERATOR_KEY to be control E.
     * @param key localization key
     * @param lp localization provider
     * @param model multiple document model
     */
    public CloseDocumentAction(String key, MultipleDocumentModel model, ILocalizationProvider lp) {
        super(key, lp);
        this.lp = lp;
        this.model = model;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
    }

    /**
     * When invoked, closes current document.
     * If document was modified displays a {@link JOptionPane} option dialog to double-check if the user wishes to close
     * without saving.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SingleDocumentModel doc = model.getCurrentDocument();
        if(doc.isModified()) {
            Object[] options = new Object[] {lp.getString("yes"), lp.getString("no")};
            int selected = JOptionPane.showOptionDialog(model.getVisualComponent(),
                    lp.getString("file_modified_message"),
                    lp.getString("warning"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if(selected != 0) return;
        }
        model.closeDocument(doc);
    }
}
