package hr.fer.oprpp1.hw08.vjezba;

public class LocalizationProviderBridge extends AbstractLocalizationProvider{
    boolean connected;
    private String currentLanguage;
    private ILocalizationProvider provider;

    public LocalizationProviderBridge(ILocalizationProvider provider) {
        this.provider = provider;
        currentLanguage = "";
    }

    public void connect() {
        if(connected) return;
        if(!currentLanguage.equals(provider.getLanguage())) {
            currentLanguage = provider.getLanguage();
            fire();
        }
        provider.addLocalizationListener(() -> currentLanguage = provider.getLanguage());
    }

    public void disconnect() {
        provider.removeLocalizationListener(() -> currentLanguage = provider.getLanguage());
    }

    @Override
    public String getString(String key) {
        return provider.getString(key);
    }

    @Override
    public String getLanguage() {
        return provider.getLanguage();
    }
}
