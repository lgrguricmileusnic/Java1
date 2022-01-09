package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serial;

public class LocalizableAction extends AbstractAction {
    private static final long serialVersionUID = 1L;
    String key;
    public LocalizableAction(String key, ILocalizationProvider lp) {
        this.key = key;
        String translation = lp.getString(key);
        putValue(NAME, translation);
        lp.addLocalizationListener(() -> {
            String translation1 = lp.getString(key);
            putValue(NAME, translation1);
        });
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
