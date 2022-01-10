package hr.fer.oprpp1.hw08.jnotepadpp.actions.tools;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Function;

public class SortSelectionAction extends LocalizableAction {
    private MultipleDocumentModel model;
    private ILocalizationProvider lp;
    boolean reverse;

    public CaretListener enableBySelectionListener = e -> {
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
        this.lp = lp;
        this.reverse = reverse;
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
        Document doc = textComponent.getDocument();
        Element root = doc.getDefaultRootElement();

        int start = textComponent.getSelectionStart();
        int end = textComponent.getSelectionEnd();

        Element startLine = root.getElement(root.getElementIndex(start));
        Element endLine = root.getElement(root.getElementIndex(end));

        start = startLine.getStartOffset();
        end = endLine.getEndOffset();

        try {
            String text = doc.getText(start, end-start);
            Locale locale = new Locale(lp.getLanguage());
            Collator collator = Collator.getInstance(locale);

            Comparator<String> localizedComparator = collator::compare;
            if(reverse) localizedComparator = localizedComparator.reversed();
            String[] lines = text.split("\\n");
            Arrays.sort(lines, localizedComparator);
            textComponent.setSelectionStart(start);
            textComponent.setSelectionEnd(end);
            textComponent.replaceSelection(String.join("\n", lines) + "\n");
        } catch (BadLocationException ignored) {}
    }
}
