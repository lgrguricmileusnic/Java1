package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ShowStatisticsAction extends LocalizableAction {
    private MultipleDocumentModel model;
    private ILocalizationProvider localizationProvider;

    /**
     * Creates an {@code Action}.
     */
    public ShowStatisticsAction(MultipleDocumentModel model, ILocalizationProvider lp) {
        super("stats", lp);
        this.model = model;
        localizationProvider = lp;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextArea textArea = model.getCurrentDocument().getTextComponent();
        int lineCount = textArea.getLineCount();
        int charCount = textArea.getText().length();
        long nonBlankCount = textArea.getText().chars().filter((c) -> !Character.isWhitespace(c)).count();
        String stats = String.format(localizationProvider.getString("stats_string"), charCount, nonBlankCount, lineCount);
        JOptionPane.showMessageDialog(model.getVisualComponent(),stats,"Statistics",JOptionPane.PLAIN_MESSAGE);
    }
}
