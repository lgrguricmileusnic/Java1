package hr.fer.oprpp1.hw08.jnotepadpp.utils;

import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Class with JNotepad++ helper methods.
 */
public class HelperMethods {
    /**
     * Checks if there are any modified documents in passed {@link MultipleDocumentModel}.
     * If such documents exist, creates option dialog asking the user for input.
     * Dialog is localized with passed localization provider.
     * @param model multiple document model
     * @param lp localization provider
     * @return true if there are no modified documents or the user allowed for input, otherwise false
     */
    public static boolean checkModifiedDocuments(MultipleDocumentModel model, ILocalizationProvider lp) {
        for(var doc : model) {
            if(doc.isModified()) {
                Object[] options = new Object[] {lp.getString("save"), lp.getString("discard"), lp.getString("cancel")};

                int selected = JOptionPane.showOptionDialog(model.getVisualComponent(),
                        String.format(lp.getString("modified_message"), getFileNameSafe(doc, lp)),
                        lp.getString("warning"),
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                switch (selected) {
                    case 0 -> {
                        saveDocumentExtended(model, doc, lp);
                    }
                    case 1 -> {}
                    case 2 -> {
                        return false;
                    }
                }
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

    /**
     * Saves document from passed {@link SingleDocumentModel}.
     * If document has no file path, presents user with {@link JFileChooser}.
     * If chosen path matches an already open document's path, warns user and cancels save action.
     * @param model {@link MultipleDocumentModel}
     * @param doc document to be saved
     * @param lp localization provider
     */
    public static void saveDocumentExtended(MultipleDocumentModel model, SingleDocumentModel doc, ILocalizationProvider lp) {
        Path path = doc.getFilePath();
        if (path == null) {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(model.getVisualComponent()) == JFileChooser.APPROVE_OPTION) {
                path = fc.getSelectedFile().toPath();
            }
        }

        SingleDocumentModel other = model.findForPath(path);
        if (other != null)
            if (!other.equals(doc)) {
                JOptionPane.showMessageDialog(model.getVisualComponent(),lp.getString("already_open_message"),"Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
        model.saveDocument(doc,path);
        doc.setFilePath(path);
        doc.setModified(false);
    }

    /**
     * Safely gets localized file name for specified {@link SingleDocumentModel}
     * @param doc document model
     * @param lp {@link ILocalizationProvider}
     * @return file name if exists, localized unnamed {@link String} otherwise
     */
    public static String getFileNameSafe(SingleDocumentModel doc, ILocalizationProvider lp) {
        String fileName;
        if (doc == null) {
            fileName = "";
        } else if (doc.getFilePath() == null) {
            fileName = lp.getString("unnamed");
        } else {
            fileName = doc.getFilePath().getFileName().toString();
        }
        return fileName;
    }
}
