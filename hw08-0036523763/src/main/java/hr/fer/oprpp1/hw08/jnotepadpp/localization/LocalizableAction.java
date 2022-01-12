package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serial;

/**
 * This {@link AbstractAction} extension allows for dynamic name localization.
 */
public class LocalizableAction extends AbstractAction {
    /**
     * localization UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * localization key
     */
    private String key;

    /**
     * Creates {@code LocalizableAction} with passed localization key and provider.
     * Adds listener for provider changes, and updates this {@link Action} accordingly.
     * @param key localization key
     * @param lp localization provider
     */
    public LocalizableAction(String key, ILocalizationProvider lp) {
        this.key = key;
        String translation = lp.getString(key);
        putValue(NAME, translation);

        lp.addLocalizationListener(() -> putValue(NAME, lp.getString(key)));
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * Gets localization key.
     * @return localization key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets localization key.
     * @param key localization key
     */
    public void setKey(String key) {
        this.key = key;
    }
}
