package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serial;

public class LocalizableAction extends AbstractAction {
    private static final long serialVersionUID = 1L;
    private String key;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
