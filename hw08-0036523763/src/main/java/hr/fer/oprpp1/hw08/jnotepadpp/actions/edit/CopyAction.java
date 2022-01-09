package hr.fer.oprpp1.hw08.jnotepadpp.actions.edit;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;

import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;

public class CopyAction extends DefaultEditorKit.CopyAction {
    private static final long serialVersionUID = 1L;
    private String key;
    public CopyAction(ILocalizationProvider lp) {
        super();
        this.key = "copy";
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
