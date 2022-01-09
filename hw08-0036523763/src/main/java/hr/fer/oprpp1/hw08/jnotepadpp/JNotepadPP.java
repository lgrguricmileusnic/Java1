package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.*;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.components.LJMenu;
import hr.fer.oprpp1.hw08.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.SingleDocumentModel;
import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.MultipleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.models.listeners.SingleDocumentListener;
import hr.fer.oprpp1.hw08.jnotepadpp.utils.HelperMethods;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class JNotepadPP extends JFrame {
    ILocalizationProvider flp;
    MultipleDocumentModel documentsModel;

    public JNotepadPP() {
        super();
        setTitle("JNotepad++");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        flp = new FormLocalizationProvider(LocalizationProvider.getInstance(),this);
        documentsModel = new DefaultMultipleDocumentModel();
        this.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                if(HelperMethods.checkModifiedDocuments(documentsModel, flp)) {
                    dispose();
                };
            }
        });

        setLocation(20, 20);
        setSize(1300, 900);
        initGUI();
    }

    /**
     * Initialises GUI
     */
    public void initGUI() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(documentsModel.getVisualComponent(), BorderLayout.CENTER);
        JTabbedPane tabbedPane = (JTabbedPane) documentsModel.getVisualComponent();
        JFrame frame = this;

        ImageIcon modifiedIcon = HelperMethods.loadIcon("modified", this);
        ImageIcon savedIcon = HelperMethods.loadIcon("saved", this);

        documentsModel.addMultipleDocumentListener(new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                SingleDocumentModel sdm = documentsModel.getCurrentDocument();
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
                        int index = documentsModel.getIndexOfDocument(model);
                        if(model.isModified()) {
                            tabbedPane.setIconAt(index, modifiedIcon);
                        } else {
                            tabbedPane.setIconAt(index, savedIcon);
                        }
                    }

                    @Override
                    public void documentFilePathUpdated(SingleDocumentModel model) {
                        int index = documentsModel.getIndexOfDocument(model);
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

                int index = documentsModel.getIndexOfDocument(model);
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

                tabbedPane.setIconAt(index, savedIcon);
                tabbedPane.setTitleAt(index, title);
                tabbedPane.setToolTipTextAt(index,toolTip);

            }

            @Override
            public void documentRemoved(SingleDocumentModel model) {

            }
        });


        //build menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new LJMenu("file", flp);
        List<LocalizableAction> fileActions = new ArrayList<>();
        fileActions.add(new CreateBlankDocumentAction(documentsModel, flp));
        fileActions.add(new SaveDocumentAction(documentsModel, flp));
        fileActions.add(new SaveAsDocumentAction(documentsModel, flp));
        fileActions.add(new OpenDocumentAction(documentsModel, flp));
        fileActions.add(new CloseDocumentAction(documentsModel, flp));
        fileActions.add(new ExitAction(documentsModel, flp, this));
        for(var action : fileActions) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setAction(action);
            fileMenu.add(menuItem);
        }
        JMenu editMenu = new LJMenu("edit", flp);
        List<TextAction> editActions = new ArrayList<>();
        editActions.add(new CopyAction("copy",flp));
        editActions.add(new CutAction("cut",flp));
        editActions.add(new PasteAction("paste",flp));

        for(var action : editActions) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setAction(action);
            editMenu.add(menuItem);
        }
        JMenu infoMenu = new LJMenu("info", flp);
        JMenuItem stats = new JMenuItem();
        stats.setAction(new ShowStatisticsAction(documentsModel, flp));
        infoMenu.add(stats);


        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(infoMenu);

        contentPane.add(menuBar, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JNotepadPP().setVisible(true);
        });
    }
}
