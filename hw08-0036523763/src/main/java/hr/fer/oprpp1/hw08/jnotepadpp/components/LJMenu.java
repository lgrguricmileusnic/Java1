package hr.fer.oprpp1.hw08.jnotepadpp.components;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;

import javax.swing.*;

/**
 * Localized {@link JMenu}
 */
public class LJMenu extends JMenu {
    /**
     * localization key
     */
    private String key;

    /**
     * Constructs {@link LJMenu} with passed key and localization provider.
     * @param key localization key
     * @param lp localization provider
     */
    public LJMenu(String key, ILocalizationProvider lp) {
        this.key = key;
        String translation = lp.getString(key);
        this.setText(translation);
        lp.addLocalizationListener(() -> {
            this.setText(lp.getString(this.key));
        });
    }
}
