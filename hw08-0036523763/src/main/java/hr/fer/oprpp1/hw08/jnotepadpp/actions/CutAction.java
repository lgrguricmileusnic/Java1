package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;

import javax.swing.text.DefaultEditorKit;

public class CutAction extends DefaultEditorKit.CutAction {
    private static final long serialVersionUID = 1L;
    String key;
    public CutAction(String key, ILocalizationProvider lp) {
        super();
        this.key = key;
        String translation = lp.getString(key);
        putValue(NAME, translation);
        lp.addLocalizationListener(() -> {
            String translation1 = lp.getString(key);
            putValue(NAME, translation1);
        });
    }
}
