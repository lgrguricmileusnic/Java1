package hr.fer.oprpp1.hw08.jnotepadpp;

import hr.fer.oprpp1.hw08.jnotepadpp.actions.edit.CopyAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.edit.CutAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.edit.PasteAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.file.*;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.info.ShowStatisticsAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.language.ChangeLanguageAction;
import hr.fer.oprpp1.hw08.jnotepadpp.components.Clock;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizableAction;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.LocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.components.LJButton;
import hr.fer.oprpp1.hw08.jnotepadpp.components.LJCountBar;
import hr.fer.oprpp1.hw08.jnotepadpp.components.LJMenu;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hr.fer.oprpp1.hw08.jnotepadpp.utils.HelperMethods.*;

public class JNotepadPP extends JFrame {
    ILocalizationProvider flp;
    MultipleDocumentModel documentsModel;
    Map<String, ImageIcon> icons;

    public JNotepadPP() {
        super();
        setTitle("JNotepad++");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
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
                if (HelperMethods.checkModifiedDocuments(documentsModel, flp)) {
                    dispose();
                }
            }
        });

        setLocation(20, 20);
        setSize(1300, 900);
        loadIcons();
        initGUI();
    }

    private void loadIcons() {
        icons = new HashMap<>(12);
        icons.put("close", loadIcon("close", this));
        icons.put("copy", loadIcon("copy", this));
        icons.put("new", loadIcon("new", this));
        icons.put("cut", loadIcon("cut", this));
        icons.put("exit", loadIcon("exit", this));
        icons.put("modified", loadIcon("modified", this));
        icons.put("open", loadIcon("open", this));
        icons.put("paste", loadIcon("paste", this));
        icons.put("save", loadIcon("save", this));
        icons.put("save_as", loadIcon("save_as", this));
        icons.put("saved", loadIcon("saved", this));
        icons.put("stats", loadIcon("stats", this));
    }

    /**
     * Initialises GUI
     */
    public void initGUI() {
        Container contentPane = getContentPane();
        JTabbedPane tabbedPane = (JTabbedPane) documentsModel.getVisualComponent();

        contentPane.setLayout(new BorderLayout());
        Container innerContainer = new Container();
        innerContainer.setLayout(new BorderLayout());
        innerContainer.add(tabbedPane, BorderLayout.CENTER);
        contentPane.add(innerContainer, BorderLayout.CENTER);
        JFrame frame = this;

        //build menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new LJMenu("file", flp);
        List<LocalizableAction> fileActions = new ArrayList<>();
        fileActions.add(new CreateBlankDocumentAction(documentsModel, flp));
        fileActions.add(new OpenDocumentAction(documentsModel, flp));
        fileActions.add(new SaveDocumentAction(documentsModel, flp));
        fileActions.add(new SaveAsDocumentAction(documentsModel, flp));
        fileActions.add(new CloseDocumentAction(documentsModel, flp));
        fileActions.add(new ExitAction(documentsModel, flp, this));

        for (var action : fileActions) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setAction(action);
            fileMenu.add(menuItem);
        }

        JMenu editMenu = new LJMenu("edit", flp);
        List<TextAction> editActions = new ArrayList<>();
        editActions.add(new CopyAction(flp));
        editActions.add(new CutAction(flp));
        editActions.add(new PasteAction(flp));

        for (var action : editActions) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setAction(action);
            editMenu.add(menuItem);
        }

        JMenu infoMenu = new LJMenu("info", flp);
        JMenuItem stats = new JMenuItem();
        stats.setAction(new ShowStatisticsAction(documentsModel, flp));
        infoMenu.add(stats);

        List<ChangeLanguageAction> languageActions = new ArrayList<>(3);
        languageActions.add(new ChangeLanguageAction("en", flp));
        languageActions.add(new ChangeLanguageAction("hr", flp));
        languageActions.add(new ChangeLanguageAction("de", flp));
        JMenu languageMenu = new LJMenu("language", flp);
        for(var action : languageActions) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setAction(action);
            languageMenu.add(menuItem);
        }

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(infoMenu);
        menuBar.add(languageMenu);

        contentPane.add(menuBar, BorderLayout.NORTH);

        //build toolbar
        JToolBar toolBar = new JToolBar("");

        JButton copyToolBarButton = new LJButton("copy", flp);
        copyToolBarButton.setAction(editActions.get(0));
        copyToolBarButton.setIcon(icons.get("copy"));


        JButton cutToolBarButton = new LJButton("cut", flp);
        cutToolBarButton.setAction(editActions.get(1));
        cutToolBarButton.setIcon(icons.get("cut"));


        JButton pasteToolBarButton = new LJButton("paste", flp);
        pasteToolBarButton.setAction(editActions.get(2));
        pasteToolBarButton.setIcon(icons.get("paste"));


        JButton statsToolBarButton = new LJButton("stats", flp);
        statsToolBarButton.setAction(stats.getAction());
        statsToolBarButton.setIcon(icons.get("stats"));


        toolBar.add(copyToolBarButton);
        toolBar.add(cutToolBarButton);
        toolBar.add(pasteToolBarButton);

        toolBar.addSeparator();
        for (var action : fileActions) {
            JButton toolBarButton = new LJButton(action.getKey(), flp);
            toolBarButton.setAction(action);
            toolBarButton.setIcon(icons.get(action.getKey()));
            toolBar.add(toolBarButton);
        }

        toolBar.addSeparator();
        toolBar.add(statsToolBarButton);
        innerContainer.add(toolBar, BorderLayout.NORTH);

        Container statusBar = new Container();
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.LINE_AXIS));
        LJCountBar countBar = new LJCountBar(flp);
        Clock clock = new Clock();
        Timer timer = new Timer(1000, e -> clock.update());
        timer.start();
        statusBar.add(countBar);
        statusBar.add(Box.createHorizontalGlue());
        statusBar.add(clock);
        contentPane.add(statusBar, BorderLayout.SOUTH);


        documentsModel.addMultipleDocumentListener(new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                SingleDocumentModel sdm = documentsModel.getCurrentDocument();
                String fileName;
                if (sdm == null || sdm.getFilePath() == null) {
                    fileName = flp.getString("unnamed");
                } else {
                    fileName = sdm.getFilePath().getFileName().toString();
                }
                frame.setTitle(fileName + " - JNotepad++");
                if(currentModel != null) {
                    currentModel.getTextComponent().addCaretListener(countBar.caretListener);
                }
                if(previousModel!= null) {
                    previousModel.getTextComponent().removeCaretListener(countBar.caretListener);
                }
                countBar.updateFromModel(currentModel);
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {
                model.addSingleDocumentListener(new SingleDocumentListener() {
                    @Override
                    public void documentModifyStatusUpdated(SingleDocumentModel model) {
                        int index = documentsModel.getIndexOfDocument(model);
                        if (model.isModified()) {
                            tabbedPane.setIconAt(index, icons.get("modified"));
                        } else {
                            tabbedPane.setIconAt(index, icons.get("saved"));
                        }

                        countBar.updateFromModel(model);
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

                if (filePath == null) {
                    title = flp.getString("unnamed");
                    toolTip = title;
                } else {
                    title = filePath.getFileName().toString();
                    toolTip = filePath.toString();
                }

                tabbedPane.setIconAt(index, icons.get("saved"));
                tabbedPane.setTitleAt(index, title);
                tabbedPane.setToolTipTextAt(index, toolTip);

            }

            @Override
            public void documentRemoved(SingleDocumentModel model) {

            }
        });

        InputMap inputMap = tabbedPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = tabbedPane.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("control N"), fileActions.get(0).getKey()); //new
        actionMap.put(fileActions.get(0).getKey(), fileActions.get(0));
        inputMap.put(KeyStroke.getKeyStroke("control O"), fileActions.get(1).getKey()); //open
        actionMap.put(fileActions.get(1).getKey(), fileActions.get(1));
        inputMap.put(KeyStroke.getKeyStroke("control S"), fileActions.get(2).getKey()); //save
        actionMap.put(fileActions.get(2).getKey(), fileActions.get(2));
        inputMap.put(KeyStroke.getKeyStroke("control shift S"), fileActions.get(3).getKey()); //save as
        actionMap.put(fileActions.get(3).getKey(), fileActions.get(3));
        inputMap.put(KeyStroke.getKeyStroke("control W"), fileActions.get(4).getKey()); //close tab
        actionMap.put(fileActions.get(4).getKey(), fileActions.get(4));
        inputMap.put(KeyStroke.getKeyStroke("control Q"), fileActions.get(5).getKey()); //exit
        actionMap.put(fileActions.get(5).getKey(), fileActions.get(5));

        inputMap.put(KeyStroke.getKeyStroke("control T"), "stats");
        actionMap.put("stats", stats.getAction());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JNotepadPP().setVisible(true);
        });
    }
}
