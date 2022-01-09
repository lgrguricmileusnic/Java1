package hr.fer.oprpp1.hw08.jnotepadpp.actions.file;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveDocumentAction extends LocalizableAction {
    private MultipleDocumentModel model;
    private ILocalizationProvider lp;
    /**
     * Creates an {@code Action}.
     */
    public SaveDocumentAction(MultipleDocumentModel model, ILocalizationProvider lp) {
        super("save", lp);
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
        SingleDocumentModel doc = model.getCurrentDocument();
        Path path = doc.getFilePath();
        if (path == null) {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(model.getVisualComponent()) == JFileChooser.APPROVE_OPTION) {
                path = fc.getSelectedFile().toPath();
            }
        }

        SingleDocumentModel other = model.findForPath(path);
        if (other != null)
            if (!other.equals(doc)) {
                JOptionPane.showMessageDialog(model.getVisualComponent(),lp.getString("already_open_message"),"Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        model.saveDocument(doc,path);
        doc.setFilePath(path);
        doc.setModified(false);
    }
}
