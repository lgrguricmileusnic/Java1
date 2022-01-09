package hr.fer.oprpp1.hw08.jnotepadpp.localization.components;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;

import javax.swing.*;

public class LJMenu extends JMenu {
    private String key;
    public LJMenu(String key, ILocalizationProvider lp) {
        this.key = key;
        String translation = lp.getString(key);
        this.setText(translation);
        lp.addLocalizationListener(() -> {
            String translation1 = lp.getString(key);
            this.setText(translation);

        });
    }
}
