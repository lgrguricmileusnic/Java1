package hr.fer.oprpp1.hw08.jnotepadpp.actions.edit;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;

import javax.swing.text.DefaultEditorKit;

public class PasteAction extends DefaultEditorKit.PasteAction {
    private static final long serialVersionUID = 1L;
    private String key;
    public PasteAction(ILocalizationProvider lp) {
        super();
        this.key = "paste";
        String translation = lp.getString(key);
        putValue(NAME, translation);
        lp.addLocalizationListener(() -> {
            putValue(NAME, lp.getString(key));
        });
    }

    public String getKey() {
        return key;
    }
}
