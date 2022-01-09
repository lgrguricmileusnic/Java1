package hr.fer.oprpp1.hw08.jnotepadpp.utils;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class HelperMethods {
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

        return new ImageIcon(bytes);
    }
}
