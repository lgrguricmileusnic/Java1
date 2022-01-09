package hr.fer.oprpp1.hw08.jnotepadpp.actions;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;

import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;

public class CopyAction extends DefaultEditorKit.CopyAction {
    private static final long serialVersionUID = 1L;
    String key;
    public CopyAction(String key, ILocalizationProvider lp) {
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
