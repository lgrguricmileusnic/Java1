package hr.fer.oprpp1.hw08.jnotepadpp.actions.tools;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Locale;
import java.util.function.Function;

public class SortSelectionAction extends LocalizableAction {
    private MultipleDocumentModel model;
    boolean reverse;
    Collator collator;

    public CaretListener listener = e -> {
        JTextComponent textComponent = (JTextComponent) e.getSource();
        if(textComponent.getSelectedText() == null) {
            this.setEnabled(false);
            return;
        }
        this.setEnabled(true);
    };

    public SortSelectionAction(String key, ILocalizationProvider lp, MultipleDocumentModel model, boolean reverse) {
        super(key, lp);
        this.model = model;
        this.reverse = reverse;
        setEnabled(false);

        Locale locale = new Locale(lp.getLanguage());
        collator = Collator.getInstance(locale);

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextComponent textComponent = model.getCurrentDocument().getTextComponent();

    }
}
