package hr.fer.oprpp1.hw08.jnotepadpp.localization;

/**
 * Localization listener interface.
 */
@FunctionalInterface
public interface ILocalizationListener {
    /**
     * Invoked when localization has changed.
     */
    void localizationChanged();
}
