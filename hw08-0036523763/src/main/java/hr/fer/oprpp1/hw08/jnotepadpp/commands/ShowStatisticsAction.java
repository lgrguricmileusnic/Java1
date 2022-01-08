package hr.fer.oprpp1.hw08.jnotepadpp.commands;

import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.charset.Charset;

public class ShowStatisticsAction extends AbstractAction {
    private MultipleDocumentModel model;

    /**
     * Creates an {@code Action}.
     */
    public ShowStatisticsAction(MultipleDocumentModel model) {
        this.model = model;
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
        String stats = String.format("Your document has %d characters, %d non-blank characters and %d lines.", charCount, nonBlankCount, lineCount);
        JOptionPane.showMessageDialog(model.getVisualComponent(),stats,"Statistics",JOptionPane.PLAIN_MESSAGE);
    }
}
