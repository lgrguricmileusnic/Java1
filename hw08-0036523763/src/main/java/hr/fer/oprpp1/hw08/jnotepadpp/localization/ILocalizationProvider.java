package hr.fer.oprpp1.hw08.jnotepadpp.localization;

/**
 * Localization provider interface
 */
public interface ILocalizationProvider {
    /**
     * Get string for passed localization key.
     * @param key localization key
     * @return {@link String} for key
     */
    String getString(String key);

    /**
     * Get current language.
     * @return current language
     */
    String getLanguage();

    /**
     * Subscribe passed listener to changes in this provider.
     * @param l lisener
     */
    void addLocalizationListener(ILocalizationListener l);

    /**
     * Unsubscribe passed listener.
     * @param l listener
     */
    void removeLocalizationListener(ILocalizationListener l);
}
