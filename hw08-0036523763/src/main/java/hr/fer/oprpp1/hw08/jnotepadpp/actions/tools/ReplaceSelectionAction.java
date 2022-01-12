package hr.fer.oprpp1.hw08.jnotepadpp.actions.tools;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.util.function.Function;

/**
 * Replaces selected text in {@link JTextComponent} of current {@link hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel}
 * for passed {@link MultipleDocumentModel}. Replacement text is provided by passed function.
 */
public class ReplaceSelectionAction extends LocalizableAction {
    /**
     * multiple document model
     */
    private MultipleDocumentModel model;
    /**
     * replacer function
     */
    private Function<String,String> replacer;

    /**
     * Default {@link CaretListener} implementation which checks text component for
     * selection. If there is no selection, disables this {@link javax.swing.Action}
     */
    public CaretListener enableBySelectionListener = e -> {
        JTextComponent textComponent = (JTextComponent) e.getSource();
        if(textComponent.getSelectedText() == null) {
            this.setEnabled(false);
            return;
        }
        this.setEnabled(true);
    };

    /**
     * Creates {@code ReplaceSelectionAction} with passed localization key and provider, multiple document model and
     * replacer function.
     * @param key localization key
     * @param lp localization provider
     * @param model multiple document model
     * @param replacer replacer function
     */
    public ReplaceSelectionAction(String key, ILocalizationProvider lp, MultipleDocumentModel model, Function<String,String> replacer) {
        super(key, lp);
        this.model = model;
        this.replacer = replacer;
        setEnabled(false);
    }

    /**
     * Invoked when an action occurs.
     * When invoked, replaces selected text in {@link JTextComponent} with function output for selected text.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextComponent textComponent = model.getCurrentDocument().getTextComponent();
        String selection = textComponent.getSelectedText();
        textComponent.replaceSelection(replacer.apply(selection));
    }
}
