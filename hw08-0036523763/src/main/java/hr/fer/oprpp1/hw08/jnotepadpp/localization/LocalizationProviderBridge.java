package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.Objects;

/**
 * {@code LocalizationProviderBridge} allows connecting and disconnecting to and from a {@link ILocalizationProvider}.
 * When connecting, adds its own {@link ILocalizationListener} used for "forwarding" location provider changes
 * to location provider bridge listeners.
 * When disconnecting, removes the listener.
 * By doing so it allows for garbage collection of objects of this class and {@link javax.swing.JFrame} instances in
 * which they were instantiated.
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
    /**
     * connection flag
     */
    boolean connected;
    /**
     * current language
     */
    private String currentLanguage;
    /**
     * localization provider
     */
    private ILocalizationProvider provider;
    /**
     * localization listener
     */
    private ILocalizationListener listener;

    /**
     * Creates {@code LocalizationProviderBridge} for passed {@link ILocalizationProvider}.
     * @param provider localization provider
     * @throws NullPointerException if provider is null
     */
    public LocalizationProviderBridge(ILocalizationProvider provider) {
        Objects.requireNonNull(provider);
        this.provider = provider;
        currentLanguage = "";
        listener = () -> {
            currentLanguage = provider.getLanguage();
            fire();
        };
    }

    /**
     * Connects to decorated {@link ILocalizationProvider}.
     */
    public void connect() {
        if(connected) return;
        if(!currentLanguage.equals(provider.getLanguage())) {
            currentLanguage = provider.getLanguage();
            fire();
        }
        provider.addLocalizationListener(listener);
    }
    /**
     * Disconnects from decorated {@link ILocalizationProvider}.
     */
    public void disconnect() {
        connected = false;
        provider.removeLocalizationListener(listener);
    }

    /**
     * Gets string for passed localization key from decorated {@link ILocalizationProvider}.
     * @param key localization key
     * @return localized string
     */
    @Override
    public String getString(String key) {
        return provider.getString(key);
    }

    /**
     * Gets current language from decorated provider.
     * @return current language
     */
    @Override
    public String getLanguage() {
        return provider.getLanguage();
    }
}
