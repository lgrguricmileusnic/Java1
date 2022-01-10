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

public class LJCountBar extends JComponent {
    private Map<String, String> translations;
    private JLabel left;
    private JLabel right;
    private int length;
    private int line;
    private int column;
    private int selected;

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

    private void updateLabels() {
        left.setText(translations.get("length") + " : " + length + "    ");
        String rightText = translations.get("lines") + " : " + line + "  " + translations.get("columns") + " : " + column;
        if(selected != 0) rightText += "  " + translations.get("selected") + " : " + selected;
        right.setText(rightText);
    }

    public void setLength(int length) {
        this.length = length;
        updateLabels();
    }

    public void setLine(int line) {
        this.line = line;
        updateLabels();
    }

    public void setColumn(int column) {
        this.column = column;
        updateLabels();
    }

    public void setSelected(int selected) {
        this.selected = selected;
        updateLabels();
    }

    public void updateFromModel(SingleDocumentModel model) {
        if(model == null) {
            length = line = column = selected = 0;
            updateLabels();
            return;
        }
        JTextArea textArea = model.getTextComponent();
        this.length = textArea.getDocument().getLength();
        updateLabels();
    }

    public CaretListener caretListener = new CaretListener() {
        @Override
        public void caretUpdate(CaretEvent e) {
            JTextArea textArea = (JTextArea) e.getSource();
            try {
                line = textArea.getLineOfOffset(e.getDot());
                column = e.getDot() - textArea.getLineStartOffset(line);
            } catch (BadLocationException ignored) {
            }
            selected = Math.abs(e.getMark() - e.getDot());
            updateLabels();
        }
    };
}
