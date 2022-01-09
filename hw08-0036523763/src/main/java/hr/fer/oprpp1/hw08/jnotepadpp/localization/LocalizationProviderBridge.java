package hr.fer.oprpp1.hw08.jnotepadpp.localization;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
    boolean connected;
    private String currentLanguage;
    private ILocalizationProvider provider;
    private ILocalizationListener listener;

    public LocalizationProviderBridge(ILocalizationProvider provider) {
        this.provider = provider;
        currentLanguage = "";
        listener = () -> {
            currentLanguage = provider.getLanguage();
            fire();
        };
    }

    public void connect() {
        if(connected) return;
        if(!currentLanguage.equals(provider.getLanguage())) {
            currentLanguage = provider.getLanguage();
            fire();
        }
        provider.addLocalizationListener(listener);
    }

    public void disconnect() {
        connected = false;
        provider.removeLocalizationListener(listener);
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
