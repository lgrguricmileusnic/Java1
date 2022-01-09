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

    public ExitAction(MultipleDocumentModel model, ILocalizationProvider lp, JFrame frame) {
        super("exit", lp);
        this.frame = frame;
        this.model = model;
        this.lp = lp;
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
