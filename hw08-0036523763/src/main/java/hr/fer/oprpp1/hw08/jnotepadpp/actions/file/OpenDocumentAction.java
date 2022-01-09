package hr.fer.oprpp1.hw08.jnotepadpp.actions.file;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

public class OpenDocumentAction extends LocalizableAction {
    private MultipleDocumentModel model;
    private ILocalizationProvider lp;

    /**
     * Creates an {@code Action}.
     */
    public OpenDocumentAction(MultipleDocumentModel model, ILocalizationProvider lp) {
        super("open", lp);
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
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(model.getVisualComponent()) == JFileChooser.APPROVE_OPTION) {
            Path path = fc.getSelectedFile().toPath();
            model.loadDocument(path);
        }
    }
}
