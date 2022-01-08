package hr.fer.oprpp1.hw08.vjezba;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serial;

public class LocalizableAction extends AbstractAction {
    @Serial
    private static final long serialVersionUID = 1L;
    String key;
    public LocalizableAction(String key, ILocalizationProvider lp) {
        this.key = key;
        String translation = lp.getString(key);
        putValue(NAME, translation);
        lp.addLocalizationListener(new ILocalizationListener() {
            @Override
            public void localizationChanged() {
                String translation = lp.getString(key);
                putValue(NAME, translation);
            }
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
