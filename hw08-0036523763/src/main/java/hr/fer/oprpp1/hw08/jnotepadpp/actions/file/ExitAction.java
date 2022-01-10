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

public class ExitAction extends LocalizableAction {
    private JFrame frame;
    private MultipleDocumentModel model;
    private ILocalizationProvider lp;

    public ExitAction(String key, MultipleDocumentModel model, ILocalizationProvider lp, JFrame frame) {
        super(key, lp);
        this.frame = frame;
        this.model = model;
        this.lp = lp;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
    }

    /**
     * Invoked when an action occurs.
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
