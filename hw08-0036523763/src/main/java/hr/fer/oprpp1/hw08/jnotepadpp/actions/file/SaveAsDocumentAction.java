package hr.fer.oprpp1.hw08.jnotepadpp.actions.file;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Action saves current document to new location, warns user if an existing location was selected.
 */
public class SaveAsDocumentAction extends LocalizableAction {
    /**
     * multiple document model reference
     */
    private MultipleDocumentModel model;
    /**
     * localization provider allowing for initial and dynamic localization
     */
    private ILocalizationProvider lp;

    /**
     * Creates {@code SaveAsDocumentAction} for passed model and localization key and provider.
     * @param key localization key
     * @param lp localization provider
     * @param model multiple document model
     */
    public SaveAsDocumentAction(String key, MultipleDocumentModel model, ILocalizationProvider lp) {
        super(key, lp);
        this.lp = lp;
        this.model = model;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
    }

    /**
     * Invoked when an action occurs.
     * When invoked opens {@link JFileChooser}, and tries to save document content to chosen path,
     * if file already exists, warns user and creates {@link JOptionPane} asking whether to continue.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        SingleDocumentModel doc = model.getCurrentDocument();
        if (fc.showSaveDialog(model.getVisualComponent()) == JFileChooser.APPROVE_OPTION) {
            Path path = fc.getSelectedFile().toPath();
            if (Files.isRegularFile(path)) {
                Object[] options = new Object[]{lp.getString("yes"), lp.getString("no")};
                int selected = JOptionPane.showOptionDialog(model.getVisualComponent(),
                        lp.getString("exists_message"),
                        lp.getString("warning"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (selected != 0) {
                    return;
                }

            } else {
                SingleDocumentModel other = model.findForPath(path);
                if (other != null)
                    if (!other.equals(doc)) {
                        JOptionPane.showMessageDialog(model.getVisualComponent(), lp.getString("already_open_message"), "Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
            }
            model.saveDocument(doc, path);
            doc.setFilePath(path);
            doc.setModified(false);
        }

    }
}
