package hr.fer.oprpp1.hw08.jnotepadpp.actions.edit;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
/**
 * This class is an extension of the {@link DefaultEditorKit.PasteAction} class,
 * allowing for name localization when using it in buttons/menu items etc.
 */
public class LocalizablePasteAction extends DefaultEditorKit.PasteAction {
    /**
     * UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * localization key
     */
    private String key;

    /**
     * Constructor takes two parameters, key and {@link ILocalizationProvider} to be used for dynamic localization
     * @param key localization key
     * @param lp localization provider
     */
    public LocalizablePasteAction(String key, ILocalizationProvider lp) {
        super();
        this.key = key;
        String translation = lp.getString(key);
        putValue(NAME, translation);
        lp.addLocalizationListener(() -> {
            putValue(NAME, lp.getString(key));
        });
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));

    }

    /**
     * Gets localization key.
     * @return localization key.
     */
    public String getKey() {
        return key;
    }
}
