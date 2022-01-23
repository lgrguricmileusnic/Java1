package hr.fer.oprpp1.hw08.jnotepadpp.components;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Component which can be used for displaying length of file,
 * current caret column and row, and length of selected text.
 */
public class LJCountBar extends JComponent {
    /**
     * translation map
     */
    private Map<String, String> translations;
    /**
     * length {@link JLabel}
     */
    private JLabel left;
    /**
     * line column selecton {@link JLabel}
     */
    private JLabel right;
    /**
     * length
     */
    private int length;
    /**
     * line
     */
    private int line;
    /**
     * column
     */
    private int column;
    /**
     * selection length
     */
    private int selected;

    /**
     * Creates {@link LJCountBar} with passed localization provider.
     * @param lp localization provider
     */
    public LJCountBar(ILocalizationProvider lp) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        length = line = column = selected = 0;
        translations = new HashMap<>(4);
        translations.put("length", lp.getString("length"));
        translations.put("lines", lp.getString("lines"));
        translations.put("columns", lp.getString("columns"));
        translations.put("selected", lp.getString("selected"));
        lp.addLocalizationListener(() -> {
            translations.put("length", lp.getString("length"));
            translations.put("lines", lp.getString("lines"));
            translations.put("columns", lp.getString("columns"));
            translations.put("selected", lp.getString("selected"));
            updateLabels();
        });

        initGUI();
    }

    /**
     * Initialises GUI
     */
    private void initGUI() {
        left = new JLabel();
        right = new JLabel();
        updateLabels();
        left.setBorder(BorderFactory.createSoftBevelBorder(1));
        left.setMinimumSize(new Dimension(200, 20));
        right.setBorder(BorderFactory.createSoftBevelBorder(1));
        add(left);
        add(right);
    }

    /**
     * Updates labels with current translations and values.
     */
    private void updateLabels() {
        left.setText(translations.get("length") + " : " + length + "    ");
        String rightText = translations.get("lines") + " : " + line + "  " + translations.get("columns") + " : " + column;
        if(selected != 0) rightText += "  " + translations.get("selected") + " : " + selected;
        right.setText(rightText);
    }

    /**
     * Sets length and updates labels.
     * @param length length
     */
    public void setLength(int length) {
        this.length = length;
        updateLabels();
    }
    /**
     * Sets line index and updates labels.
     * @param line number
     */
    public void setLine(int line) {
        this.line = line;
        updateLabels();
    }

    /**
     * Sets column index and updates labels.
     * @param column column index
     */
    public void setColumn(int column) {
        this.column = column;
        updateLabels();
    }

    /**
     * Sets selection length and updates labels.
     * @param selected selection length
     */
    public void setSelected(int selected) {
        this.selected = selected;
        updateLabels();
    }

    /**
     * Updates length from passed {@link SingleDocumentModel} and updates labels.
     * If passed model is null, updates all values to zero
     * @param model {@link SingleDocumentModel}
     */
    public void updateFromModel(SingleDocumentModel model) {
        if(model == null) {
            length = line = column = selected = 0;
            updateLabels();
            return;
        }
        JTextArea textArea = model.getTextComponent();
        try {
            line = textArea.getLineOfOffset(textArea.getSelectionStart());
            column = textArea.getSelectionStart() - textArea.getLineStartOffset(line);
            line++;
            column++;
        } catch (BadLocationException ignored) {
        }
        selected = Math.abs(textArea.getSelectionEnd() - textArea.getSelectionStart());
        this.length = textArea.getDocument().getLength();
        updateLabels();
    }

    /**
     * Default {@link CaretListener} implementation, updates selection length and line, column indexes.
     */
    public CaretListener lineColumnSelectionListener = new CaretListener() {
        @Override
        public void caretUpdate(CaretEvent e) {
            JTextArea textArea = (JTextArea) e.getSource();
            try {
                line = textArea.getLineOfOffset(e.getDot());
                column = e.getDot() - textArea.getLineStartOffset(line);
                line++;
                column++;
            } catch (BadLocationException ignored) {
            }
            selected = Math.abs(e.getMark() - e.getDot());
            updateLabels();
        }
    };
}
