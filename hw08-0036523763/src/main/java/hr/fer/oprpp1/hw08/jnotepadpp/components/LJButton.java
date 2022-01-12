package hr.fer.oprpp1.hw08.jnotepadpp.components;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;

import javax.swing.*;
import java.awt.*;

/**
 * {@link JButton} with localized text and tooltip.
 */
public class LJButton extends JButton {
    /**
     * localization key
     */
    private String key;

    /**
     * Creates {@code LJButton} with passed localization key and provider.
     * @param key localization key
     * @param lp localization provider
     */
    public LJButton(String key, ILocalizationProvider lp) {
        this.key = key;
        String translation = lp.getString(key);
        setToolTipText(translation);
        setText(translation);
        lp.addLocalizationListener(() -> {
            setToolTipText(lp.getString(this.key));
            setText(lp.getString(this.key));
        });
    }
}
