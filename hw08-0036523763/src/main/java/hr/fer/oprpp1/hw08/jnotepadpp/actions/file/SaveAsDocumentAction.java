package hr.fer.oprpp1.hw08.jnotepadpp.actions.file;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

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
        SingleDocumentModel doc = model.getCurrentDocument();
        if (fc.showSaveDialog(model.getVisualComponent()) == JFileChooser.APPROVE_OPTION) {
            Path path = fc.getSelectedFile().toPath();
            if(Files.isRegularFile(path)) {
                Object[] options = new Object[] {lp.getString("yes"), lp.getString("no")};
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
                        JOptionPane.showMessageDialog(model.getVisualComponent(),lp.getString("already_open_message"),"Warning", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
            }
            model.saveDocument(doc,path);
            doc.setFilePath(path);
            doc.setModified(false);
        }

    }
}
