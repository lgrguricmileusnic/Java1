package hr.fer.oprpp1.hw08.jnotepadpp.models.listeners;

/**
 * Interface for a {@link MultipleDocumentListener} notifier.
 * Method {@code notify} notifies passed listener.
 */
@FunctionalInterface
public interface SingleDocumentListenerNotifier {
    /**
     * Notifiy passed listener.
     * @param l listener
     */
    void notify(SingleDocumentListener l);
}
