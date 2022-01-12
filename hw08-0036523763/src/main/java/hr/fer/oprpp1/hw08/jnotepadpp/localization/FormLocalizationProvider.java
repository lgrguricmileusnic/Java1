package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * This class is a {@link LocalizationProviderBridge} extension which
 * automatically connects and disconnects localization bridge when window is opened and closed.
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
    /**
     * Constructs {@code FormLocalizationProvider} from passed provider, for passed {@link JFrame}.
     * @param provider localization provider
     * @param frame {@link JFrame}
     */
    public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
        super(provider);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                connect();
            }
            @Override
            public void windowClosed(WindowEvent e) {
                disconnect();
            }
        });
    }
}
