package hr.fer.oprpp1.hw08.jnotepadpp.actions.language;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;


import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChangeLanguageAction extends LocalizableAction {
    private String languageKey;
    public ChangeLanguageAction(String languageKey, ILocalizationProvider lp) {
        super(languageKey, lp);
        this.languageKey = languageKey;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        LocalizationProvider.getInstance().setLanguage(languageKey);
    }
}
