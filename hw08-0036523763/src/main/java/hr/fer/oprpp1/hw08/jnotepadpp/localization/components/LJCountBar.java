package hr.fer.oprpp1.hw08.jnotepadpp.localization.components;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LJCountBar extends JComponent {
    private Map<String, String> translations;
    private JLabel left;
    private JLabel right;
    private int length;
    private int lines;
    private int columns;
    private int selected;

    public LJCountBar(ILocalizationProvider lp) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        length = lines = columns = selected = 0;
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
        left.setPreferredSize(new Dimension(50, 10));
        right.setBorder(BorderFactory.createSoftBevelBorder(1));
        add(left);
        add(right);

    }

    private void updateLabels() {
        left.setText(translations.get("length") + " : " + length + "    ");
        String rightText = translations.get("lines") + " : " + lines + "  " + translations.get("columns") + " : " + columns;
        if(selected != 0) rightText += "  " + translations.get("selected") + " : " + selected;
        right.setText(rightText);
    }

    public void setLength(int length) {
        this.length = length;
        updateLabels();
    }

    public void setLines(int lines) {
        this.lines = lines;
        updateLabels();
    }

    public void setColumns(int columns) {
        this.columns = columns;
        updateLabels();
    }

    public void setSelected(int selected) {
        this.selected = selected;
        updateLabels();
    }

    public void updateFromModel(SingleDocumentModel model) {
        if(model == null) {
            length = lines = columns = selected = 0;
            updateLabels();
            return;
        }
        JTextArea textArea = model.getTextComponent();
        this.length = textArea.getDocument().getLength();
        updateLabels();
    }

    public static CaretListener caretListener = new CaretListener() {
        @Override
        public void caretUpdate(CaretEvent e) {
            e.getDot()
        }
    }
}
