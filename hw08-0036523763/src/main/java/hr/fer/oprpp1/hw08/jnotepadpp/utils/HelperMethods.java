package hr.fer.oprpp1.hw08.jnotepadpp.utils;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class with JNotepad++ helper methods.
 */
public class HelperMethods {
    /**
     * Checks if there are any modified documents in passed {@link MultipleDocumentModel}.
     * If such documents exist, creates option dialog asking the user for continuation.
     * Dialog is localized with passed localization provider.
     * @param model multiple document model
     * @param lp localization provider
     * @return true if there are no modified documents or the user allowed for continuation, otherwise false
     */
    public static boolean checkModifiedDocuments(MultipleDocumentModel model, ILocalizationProvider lp) {
        for(var doc : model) {
            if(doc.isModified()) {
                Object[] options = new Object[] {lp.getString("yes"), lp.getString("no")};
                int selected = JOptionPane.showOptionDialog(model.getVisualComponent(),
                        lp.getString("modified_message"),
                        lp.getString("warning"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                return selected == 0;
            }
        }
        return true;
    }

    /**
     * Loads icon from "resources/icons/iconName.png".
     * @param iconName icon name
     * @param context context
     * @return {@link ImageIcon} instance of loaded icon
     */
    public static ImageIcon loadIcon(String iconName, Object context) {
        InputStream is = context.getClass().getResourceAsStream("/icons/" + iconName + ".png");
        if(is==null) {
            throw new IllegalArgumentException("Icon not found: " + iconName);
        }
        byte[] bytes;
        try {
            bytes = is.readAllBytes();
            is.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Couldn't load icon: " + iconName);
        }
        Image img = new ImageIcon(bytes).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
