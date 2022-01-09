package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class ExitAction extends LocalizableAction {
    private JFrame frame;
    private MultipleDocumentModel model;
    private ILocalizationProvider lp;

    public ExitAction(String key, ILocalizationProvider lp, JFrame frame, MultipleDocumentModel model, ILocalizationProvider lp1) {
        super(key, lp);
        this.frame = frame;
        this.model = model;
        this.lp = lp1;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for(var doc : model) {
            if(doc.isModified()) {
                Object[] options = new Object[] {lp.getString("yes"), lp.getString("no")};
                int selected = JOptionPane.showOptionDialog(frame,
                        lp.getString("modified_message"),
                        lp.getString("warning"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if(selected == 0){
                    frame.dispose();
                }

            }
        }
    }
}
