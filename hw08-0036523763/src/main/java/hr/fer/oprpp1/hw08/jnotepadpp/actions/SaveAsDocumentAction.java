package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveAsDocumentAction extends LocalizableAction {
    private MultipleDocumentModel model;
    private ILocalizationProvider lp;

    /**
     * Creates an {@code Action}.
     */
    public SaveAsDocumentAction(MultipleDocumentModel model, ILocalizationProvider lp) {
        super("save_as", lp);
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
        if (fc.showSaveDialog(model.getVisualComponent()) == JFileChooser.APPROVE_OPTION) {
            Path path = fc.getSelectedFile().toPath();
            if(Files.isRegularFile(path)) {
                JOptionPane.showMessageDialog(model.getVisualComponent(),lp.getString("exists_message"), lp.getString("warning"), JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    model.saveDocument(model.getCurrentDocument(),path);
                    model.getCurrentDocument().setFilePath(path);
                    model.getCurrentDocument().setModified(false);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(model.getVisualComponent(),lp.getString("already_open_message"), lp.getString("error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
