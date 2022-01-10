package hr.fer.oprpp1.hw08.jnotepadpp.actions.edit;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

public class LocalizableCopyAction extends DefaultEditorKit.CopyAction {
    private static final long serialVersionUID = 1L;
    private String key;
    public LocalizableCopyAction(String key, ILocalizationProvider lp) {
        super();
        this.key = key;
        String translation = lp.getString(key);
        putValue(NAME, translation);
        lp.addLocalizationListener(() -> {
            putValue(NAME, lp.getString(key));
        });
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
    }

    public String getKey() {
        return key;
    }
}
