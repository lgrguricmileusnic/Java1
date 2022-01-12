package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is a {@link AbstractLocalizationProvider} extension, implementing
 * {@code getString}, {@code getLanguage}, {@code setLanguage} methods.
 * {@code LocalizationProvider} uses translation bundle {@code prijevodi} under
 * {@code hr.fer.oprpp1.hw08.jnotepadpp.localization.prijevodi}.
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
    /**
     * current locale key
     */
    String language;
    /**
     * current language {@link ResourceBundle}
     */
    ResourceBundle bundle;
    /**
     * bundle location
     */
    private static final String baseName = "hr.fer.oprpp1.hw08.jnotepadpp.localization.prijevodi";
    /**
     * {@code LocalizationProvider} singleton
     */
    private static final LocalizationProvider instance = new LocalizationProvider();

    /**
     * Gets {@code LocalizationProvider} singleton instance
     * @return {@code LocalizationProvider} singleton
     */
    public static LocalizationProvider getInstance() {
        return instance;
    }

    /**
     * Creates {@code LocalizationProvider}. Default language is english.
     */
    private LocalizationProvider() {
        language = "en";
        Locale locale = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle(baseName, locale);
    }

    /**
     * Sets current language.
     * @param language locale key
     */
    public void setLanguage(String language) {
        this.language = language;
        Locale locale = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle(baseName, locale);
        fire();
    }

    /**
     * Get string for passed key in current language.
     * @param key localization key
     * @return string for passed localization key
     */
    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }

    /**
     * Get current language.
     * @return current language
     */
    @Override
    public String getLanguage() {
        return language;
    }
}
