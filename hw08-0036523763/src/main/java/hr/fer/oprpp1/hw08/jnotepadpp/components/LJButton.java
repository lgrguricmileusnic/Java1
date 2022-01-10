package hr.fer.oprpp1.hw08.jnotepadpp.components;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;

import javax.swing.*;
import java.awt.*;


public class LJButton extends JButton {
    private String key;

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
