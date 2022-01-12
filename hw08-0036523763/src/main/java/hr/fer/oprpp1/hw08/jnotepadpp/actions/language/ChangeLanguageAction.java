package hr.fer.oprpp1.hw08.jnotepadpp.actions.language;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;


import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Action changes language of localization provider to key passed in the constructor when invoked.
 * Key for localization string and localization bundle must be the same.
 */
public class ChangeLanguageAction extends LocalizableAction {
    /**
     * language key
     */
    private String languageKey;

    /**
     * Creates new {@code ChangeLanguageAction} with passed {@code languageKey} and {@link ILocalizationProvider}
     * @param languageKey language key
     * @param lp localization provider
     */
    public ChangeLanguageAction(String languageKey, ILocalizationProvider lp) {
        super(languageKey, lp);
        this.languageKey = languageKey;
    }

    /**
     * Invoked when an action occurs.
     * When invoked changes language of localization provider to language defined by key passed in the constructor.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        LocalizationProvider.getInstance().setLanguage(languageKey);
    }
}
