package hr.fer.oprpp1.hw08.jnotepadpp.actions.tools;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.util.function.Function;

public class ReplaceSelectionAction extends LocalizableAction {
    private MultipleDocumentModel model;
    private Function<String,String> caseChanger;

    public CaretListener listener = e -> {
        JTextComponent textComponent = (JTextComponent) e.getSource();
        if(textComponent.getSelectedText() == null) {
            this.setEnabled(false);
            return;
        }
        this.setEnabled(true);
    };

    public ReplaceSelectionAction(String key, ILocalizationProvider lp, MultipleDocumentModel model, Function<String,String> caseChanger) {
        super(key, lp);
        this.model = model;
        this.caseChanger = caseChanger;
        setEnabled(false);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextComponent textComponent = model.getCurrentDocument().getTextComponent();
        String selection = textComponent.getSelectedText();
        textComponent.replaceSelection(caseChanger.apply(selection));
    }
}
