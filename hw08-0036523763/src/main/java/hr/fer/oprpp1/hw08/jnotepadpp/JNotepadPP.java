package hr.fer.oprpp1.hw08.jnotepadpp;


import hr.fer.oprpp1.hw08.jnotepadpp.actions.edit.LocalizableCopyAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.edit.LocalizableCutAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.edit.LocalizablePasteAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.file.*;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.info.ShowStatisticsAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.language.ChangeLanguageAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.ReplaceSelectionAction;
import hr.fer.oprpp1.hw08.jnotepadpp.actions.tools.SortSelectionAction;
import hr.fer.oprpp1.hw08.jnotepadpp.components.Clock;
import hr.fer.oprpp1.hw08.jnotepadpp.localization.*;
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
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

import static hr.fer.oprpp1.hw08.jnotepadpp.utils.HelperMethods.*;

/**
 * JNotepad++ is a GUI application for editing simple text documents.
 * Multiple documents can be open in a tabbed view.
 * Provides the user with tools such as case switching, line sorting and removing duplicate lines.
 * File statistics are also available, displaying line, character and non-blank character counts.
 * The application features a floatable toolabar, built-in clock and status bar for tracking line and column indexes,
 * selection and file length.
 * Includes 4 localizations: Russian, German, Croatian and English
 *
 */
public class JNotepadPP extends JFrame {
    /**
     * localization provider
     */
    ILocalizationProvider flp;
    /**
     * multiple documents model
     */
    MultipleDocumentModel documentsModel;
    /**
     * cached icons map
     */
    Map<String, ImageIcon> icons;

    /**
     * Constructor
     */
    public JNotepadPP() {
        super();
        setTitle("JNotepad++");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
        documentsModel = new DefaultMultipleDocumentModel();
        this.addWindowListener(new WindowAdapter() {
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

    /**
     * Caches icons from resources.
     */
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
        fileActions.add(new CreateBlankDocumentAction("new", documentsModel, flp));
        fileActions.add(new OpenDocumentAction("open", documentsModel, flp));
        fileActions.add(new SaveDocumentAction("save", documentsModel, flp));
        fileActions.add(new SaveAsDocumentAction("save_as", documentsModel, flp));
        fileActions.add(new CloseDocumentAction("close", documentsModel, flp));
        fileActions.add(new ExitAction("exit", documentsModel, flp, this));

        for (var action : fileActions) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setAction(action);
            fileMenu.add(menuItem);
        }

        JMenu editMenu = new LJMenu("edit", flp);
        List<TextAction> editActions = new ArrayList<>();
        editActions.add(new LocalizableCopyAction("copy", flp));
        editActions.add(new LocalizableCutAction("cut", flp));
        editActions.add(new LocalizablePasteAction("paste",flp));

        for (var action : editActions) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setAction(action);
            editMenu.add(menuItem);
        }

        JMenu toolsMenu = new LJMenu("tools", flp);
        JMenu changeCaseSubMenu = new LJMenu("change_case", flp);
        toolsMenu.add(changeCaseSubMenu);
        List<ReplaceSelectionAction> replaceSelectionActions = new ArrayList<>(3);
        replaceSelectionActions.add(new ReplaceSelectionAction("upper", flp, documentsModel, String::toUpperCase));
        replaceSelectionActions.add(new ReplaceSelectionAction("lower", flp, documentsModel, String::toLowerCase));
        replaceSelectionActions.add(new ReplaceSelectionAction("invert", flp, documentsModel, s -> {
            StringBuilder sb = new StringBuilder();
            for (char c : s.toCharArray()) {
                sb.append(Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c));
            }
            return sb.toString();
        }));

        for (var action : replaceSelectionActions) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setAction(action);
            changeCaseSubMenu.add(menuItem);
        }

        JMenu sortSubMenu = new LJMenu("sort", flp);
        SortSelectionAction[] sortActions = new SortSelectionAction[]{
                new SortSelectionAction("ascending", flp, documentsModel, false),
                new SortSelectionAction("descending", flp, documentsModel, true)
        };
        for (var action : sortActions) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setAction(action);
            sortSubMenu.add(menuItem);
        }
        toolsMenu.add(sortSubMenu);
        ReplaceSelectionAction uniqueAction = new ReplaceSelectionAction("unique", flp, documentsModel, text -> {
            Set<String> lines = new LinkedHashSet<>(Arrays.asList(text.split("\\n")));
            return String.join("\n", lines) + "\n";
        });
        toolsMenu.add(new JMenuItem(uniqueAction));

        JMenu infoMenu = new LJMenu("info", flp);
        JMenuItem stats = new JMenuItem();
        stats.setAction(new ShowStatisticsAction("stats", documentsModel, flp));
        infoMenu.add(stats);

        List<ChangeLanguageAction> languageActions = new ArrayList<>(3);
        languageActions.add(new ChangeLanguageAction("en", flp));
        languageActions.add(new ChangeLanguageAction("hr", flp));
        languageActions.add(new ChangeLanguageAction("de", flp));
        languageActions.add(new ChangeLanguageAction("ru", flp));
        JMenu languageMenu = new LJMenu("language", flp);
        for (var action : languageActions) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setAction(action);
            languageMenu.add(menuItem);
        }

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(infoMenu);
        menuBar.add(toolsMenu);
        menuBar.add(languageMenu);

        contentPane.add(menuBar, BorderLayout.NORTH);

        //build toolbar

        innerContainer.add(buildToolbar(), BorderLayout.NORTH);

        Container statusBar = new Container();
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.LINE_AXIS));
        LJCountBar countBar = new LJCountBar(flp);
        Clock clock = new Clock();
        statusBar.add(countBar);
        statusBar.add(Box.createHorizontalGlue());
        statusBar.add(clock);
        contentPane.add(statusBar, BorderLayout.SOUTH);

        documentsModel.addMultipleDocumentListener(new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                String fileName;
                if (currentModel == null) {
                    fileName = "";
                } else if (currentModel.getFilePath() == null) {
                    fileName = flp.getString("unnamed");
                } else {
                    fileName = currentModel.getFilePath().getFileName().toString();
                }
                frame.setTitle(fileName + " - JNotepad++");

                if (currentModel != null) {
                    JTextComponent currentTextComponent = currentModel.getTextComponent();
                    currentTextComponent.addCaretListener(countBar.lineColumnSelectionListener);
                    for (var action : replaceSelectionActions) {
                        currentTextComponent.addCaretListener(action.enableBySelectionListener);
                    }
                    for (var action : sortActions) {
                        currentTextComponent.addCaretListener(action.enableBySelectionListener);
                    }
                    currentTextComponent.addCaretListener(uniqueAction.enableBySelectionListener);
                }
                if (previousModel != null) {
                    previousModel.getTextComponent().removeCaretListener(countBar.lineColumnSelectionListener);
                    for (var action : replaceSelectionActions) {
                        previousModel.getTextComponent().removeCaretListener(action.enableBySelectionListener);
                    }
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
        flp.addLocalizationListener(new ILocalizationListener() {
            @Override
            public void localizationChanged() {
                for(SingleDocumentModel doc : documentsModel) {
                    if (doc.getFilePath() == null) {
                        int index = documentsModel.getIndexOfDocument(doc);
                        tabbedPane.setTitleAt(index, flp.getString("unnamed"));
                        tabbedPane.setToolTipTextAt(index, flp.getString("unnamed"));
                    }
                }
            }
        });
    }

    /**
     * Creates and populates {@link JToolBar}
     * @return toolbar
     */
    private JToolBar buildToolbar() {
        JToolBar toolBar = new JToolBar("");

        JButton copyToolBarButton = new LJButton("copy", flp);
        copyToolBarButton.setAction(new LocalizableCopyAction("none", flp));
        copyToolBarButton.setIcon(icons.get("copy"));

        JButton cutToolBarButton = new LJButton("cut", flp);
        cutToolBarButton.setAction(new LocalizableCutAction("none", flp));
        cutToolBarButton.setIcon(icons.get("cut"));

        JButton pasteToolBarButton = new LJButton("paste", flp);
        pasteToolBarButton.setAction(new LocalizablePasteAction("none", flp));
        pasteToolBarButton.setIcon(icons.get("paste"));

        JButton statsToolBarButton = new LJButton("stats", flp);
        statsToolBarButton.setAction(new ShowStatisticsAction("none", documentsModel, flp));
        statsToolBarButton.setIcon(icons.get("stats"));

        toolBar.add(copyToolBarButton);
        toolBar.add(cutToolBarButton);
        toolBar.add(pasteToolBarButton);

        toolBar.addSeparator();

        List<LocalizableAction> fileActions = new ArrayList<>();
        fileActions.add(new CreateBlankDocumentAction("new", documentsModel, flp));
        fileActions.add(new OpenDocumentAction("open", documentsModel, flp));
        fileActions.add(new SaveDocumentAction("save", documentsModel, flp));
        fileActions.add(new SaveAsDocumentAction("save_as", documentsModel, flp));
        fileActions.add(new CloseDocumentAction("close", documentsModel, flp));
        fileActions.add(new ExitAction("exit", documentsModel, flp, this));
        for (LocalizableAction action : fileActions) {
            JButton toolBarButton = new LJButton(action.getKey(), flp);
            action.putValue(Action.SMALL_ICON, icons.get(action.getKey()));
            toolBarButton.setAction(action);
            toolBar.add(toolBarButton);
        }

        toolBar.addSeparator();
        toolBar.add(statsToolBarButton);
        return toolBar;
    }

    /**
     * Creates JNotepadPP {@link JFrame}
     * @param args none
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JNotepadPP().setVisible(true);
        });
    }
}
