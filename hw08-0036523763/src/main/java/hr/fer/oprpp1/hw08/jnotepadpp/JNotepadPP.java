package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.*;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.components.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.SingleDocumentListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JNotepadPP extends JFrame {
    ILocalizationProvider flp;

    public JNotepadPP() {
        super();
        setTitle("JNotepad++");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocation(20, 20);
        setSize(1300, 900);
        flp = new FormLocalizationProvider(LocalizationProvider.getInstance(),this);
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
                SingleDocumentModel sdm = tabbedPane.getCurrentDocument();
                String fileName;
                if(sdm == null || sdm.getFilePath() == null) {
                    fileName = flp.getString("unnamed");
                } else {
                    fileName = sdm.getFilePath().getFileName().toString();
                }
                frame.setTitle(fileName + " - JNotepad++");
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {
                model.addSingleDocumentListener(new SingleDocumentListener() {
                    @Override
                    public void documentModifyStatusUpdated(SingleDocumentModel model) {
                        int index = tabbedPane.getIndexOfDocument(model);
                        if(model.isModified()) {
                            tabbedPane.setIconAt(index, loadIcon("modified"));
                        } else {
                            tabbedPane.setIconAt(index, loadIcon("saved"));
                        }
                    }

                    @Override
                    public void documentFilePathUpdated(SingleDocumentModel model) {
                        int index = tabbedPane.getIndexOfDocument(model);
                        tabbedPane.setTitleAt(index, model.getFilePath().getFileName().toString());
                        tabbedPane.setToolTipTextAt(index, model.getFilePath().toString());
                    }
                });

                JTextArea textArea = model.getTextComponent();
                textArea.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        model.setModified(true);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        model.setModified(true);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        model.setModified(true);
                    }
                });
                Path filePath = model.getFilePath();
                String title;
                String toolTip;
                if(filePath == null) {
                    title = flp.getString("unnamed");
                    toolTip = title;
                } else {
                    title = filePath.getFileName().toString();
                    toolTip = filePath.toString();
                }
                tabbedPane.addTab(title, loadIcon("saved"),model.getTextComponent(), toolTip);
                tabbedPane.setCurrentDocument(model);
                tabbedPane.setSelectedIndex(tabbedPane.getIndexOfDocument(model));
            }

            @Override
            public void documentRemoved(SingleDocumentModel model) {
                tabbedPane.removeTabAt(tabbedPane.indexOfComponent(model.getTextComponent()));
                int newDocIndex = tabbedPane.getNumberOfDocuments() - 1;
                if(newDocIndex < 0) {
                    tabbedPane.setCurrentDocument(null);
                    return;
                }
                tabbedPane.setCurrentDocument(tabbedPane.getDocument(newDocIndex));
            }
        });


        //build menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new LJMenu("file", flp);
        List<LocalizableAction> fileActions = new ArrayList<>();
        fileActions.add(new CreateBlankDocumentAction(tabbedPane, flp));
        fileActions.add(new SaveDocumentAction(tabbedPane, flp));
        fileActions.add(new SaveAsDocumentAction(tabbedPane, flp));
        fileActions.add(new OpenDocumentAction(tabbedPane, flp));
        fileActions.add(new CloseDocumentAction(tabbedPane, flp));
        for(var action : fileActions) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setAction(action);
            fileMenu.add(menuItem);
        }

        JMenu infoMenu = new LJMenu("info", flp);
        JMenuItem stats = new JMenuItem();
        stats.setAction(new ShowStatisticsAction(tabbedPane, flp));
        infoMenu.add(stats);

        menuBar.add(fileMenu);
        menuBar.add(infoMenu);

        contentPane.add(menuBar, BorderLayout.NORTH);
    }

    private ImageIcon loadIcon(String iconName) {
        InputStream is = this.getClass().getResourceAsStream("/icons/" + iconName + ".png");
        if(is==null) {
            throw new IllegalArgumentException("Icon not found: " + iconName);
        }
        byte[] bytes = new byte[0];
        try {
            bytes = is.readAllBytes();
            is.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Couldn't load icon: " + iconName);
        }

        return new ImageIcon(bytes);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JNotepadPP().setVisible(true);
        });
    }
}
