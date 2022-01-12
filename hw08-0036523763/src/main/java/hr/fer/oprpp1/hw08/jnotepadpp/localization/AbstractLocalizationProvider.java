package hr.fer.oprpp1.hw08.jnotepadpp.localization;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Partial implementation of {@link ILocalizationProvider}.
 * Implements listener handling methods.
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
    /**
     * underlying listener set
     */
    Set<ILocalizationListener> listeners;

    /**
     * Constructs {@code AbstractLocalizationProvider} and initialises underlying listener set.
     */
    public AbstractLocalizationProvider() {
        this.listeners = new HashSet<>();
    }

    /**
     * Adds passed listener to underlying listener set.
     * @param l listener
     * @throws NullPointerException if {@code l} is null
     */
    @Override
    public void addLocalizationListener(ILocalizationListener l) {
        listeners.add(l);
    }

    /**
     * Removes passed listener from underlying listener set.
     * @param l listener
     */
    @Override
    public void removeLocalizationListener(ILocalizationListener l) {
        listeners.remove(l);
    }

    /**
     * Notifies all subscribed listeners.
     */
    public void fire() {
        for(var l : listeners) {
            l.localizationChanged();
        }
    }
}
