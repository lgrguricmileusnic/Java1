package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
    Set<ILocalizationListener> listeners;

    public AbstractLocalizationProvider() {
        this.listeners = new HashSet<>();
    }

    @Override
    public void addLocalizationListener(ILocalizationListener l) {
        listeners.add(l);
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener l) {
        listeners.remove(l);
    }

    public void fire() {
        for(var l : listeners) {
            l.localizationChanged();
        }
    }
}
