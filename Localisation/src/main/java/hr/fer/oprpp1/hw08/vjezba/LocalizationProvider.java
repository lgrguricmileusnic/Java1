package hr.fer.oprpp1.hw08.vjezba;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {
    String language;
    ResourceBundle bundle;
    private static final LocalizationProvider instance = new LocalizationProvider();

    public static LocalizationProvider getInstance() {
        return instance;
    }

    private LocalizationProvider() {
        language = "en";
        Locale locale = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.vjezba.prijevodi", locale);
    }

    public void setLanguage(String language) {
        this.language = language;
        Locale locale = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.vjezba.prijevodi", locale);
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }

    @Override
    public String getLanguage() {
        return language;
    }
}
