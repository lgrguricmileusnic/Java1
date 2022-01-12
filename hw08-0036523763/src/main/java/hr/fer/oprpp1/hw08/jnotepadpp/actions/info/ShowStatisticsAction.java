package hr.fer.oprpp1.hw08.jnotepadpp.actions.info;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action shows current file statistics: line count, character count and non-blank character count.
 */
public class ShowStatisticsAction extends LocalizableAction {
    /**
     * multiple document model reference
     */
    private MultipleDocumentModel model;
    /**
     * localization provider reference for initial and dynamic localization
     */
    private ILocalizationProvider localizationProvider;

    /**
     * Creates an {@code ShowStatisticsAction} with passed model and localization key and provider.
     * Sets {@code ACCELERATOR_KEY} to {@code control T}
     * @param key localization key
     * @param lp localization provider
     * @param model multiple document model
     */
    public ShowStatisticsAction(String key, MultipleDocumentModel model, ILocalizationProvider lp) {
        super(key, lp);
        this.model = model;
        localizationProvider = lp;
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
    }

    /**
     * Invoked when an action occurs.
     * When invoked shows current file statistics in a message dialog:
     * line count, character count and non-blank character count.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextArea textArea = model.getCurrentDocument().getTextComponent();
        int lineCount = textArea.getLineCount();
        int charCount = textArea.getText().length();
        long nonBlankCount = textArea.getText().chars().filter((c) -> !Character.isWhitespace(c)).count();
        String stats = String.format(localizationProvider.getString("stats_string"), charCount, nonBlankCount, lineCount);
        JOptionPane.showMessageDialog(model.getVisualComponent(),stats,"Statistics", JOptionPane.PLAIN_MESSAGE);
    }
}
