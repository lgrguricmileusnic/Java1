package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.commands.SaveDocumentAction;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.MultipleDocumentListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.nio.file.Path;

public class JNotepadPP extends JFrame {


    public JNotepadPP() {
        super();
        setTitle("JNotepad++");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocation(20, 20);
        setSize(1300, 900);
        initGUI();
    }

    /**
     * Initialises GUI
     */
    public void initGUI() {
        Container contentPane = getContentPane();
        DefaultMultipleDocumentModel tabbedPane = new DefaultMultipleDocumentModel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        JFrame frame = this;
        tabbedPane.addMultipleDocumentListener(new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                Path path = tabbedPane.getCurrentDocument().getFilePath();
                String fileName;
                if(path == null) {
                    fileName = "(unnamed)";
                } else {
                    fileName = path.getFileName().toString();
                }
                frame.setTitle(fileName + " - JNotepad++");
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {
                tabbedPane.addTab("unnamed", model.getTextComponent());
                tabbedPane.setCurrentDocument(model);
                tabbedPane.setSelectedIndex(tabbedPane.getIndexOfDocument(model));
            }

            @Override
            public void documentRemoved(SingleDocumentModel model) {
                tabbedPane.removeTabAt(tabbedPane.indexOfComponent(model.getTextComponent()));
                tabbedPane.setCurrentDocument(tabbedPane.getDocument(tabbedPane.getNumberOfDocuments() - 1));
            }
        });
        //build menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem  saveItem = new JMenuItem();
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        saveItem.setAction(new SaveDocumentAction(tabbedPane));

        contentPane.add(menuBar, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JNotepadPP().setVisible(true);
        });
    }
}
