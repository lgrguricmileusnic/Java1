package hr.fer.oprpp1.hw08.vjezba;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;

public class Prozor extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private JButton gumb;
    private ILocalizationProvider provider;

    public Prozor() throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(0, 0);
        setTitle("Demo");
        initGUI();
//        LocalizationProvider.getInstance().addLocalizationListener(new ILocalizationListener() {
//            @Override
//            public void localizationChanged() {
//                gumb.setText(LocalizationProvider.getInstance().getString("login"));
//            }
//        });
        provider = new FormLocalizationProvider(LocalizationProvider.getInstance(),this);
        provider.addLocalizationListener(() -> {
            gumb.setText(LocalizationProvider.getInstance().getString("login"));
        });
        pack();
    }

    private void initGUI() {
        getContentPane().setLayout(new BorderLayout());
        gumb = new JButton(LocalizationProvider.getInstance().getString("login"));
//        Locale locale = Locale.forLanguageTag(language);
//        ResourceBundle bundle =
//                ResourceBundle.getBundle("hr.fer.oprpp1.hw08.vjezba.prijevodi", locale);
//        JButton gumb = new JButton(
//                bundle.getString("login")
//        );

//        JButton gumb = new JButton(
//                language.equals("hr") ? "Prijava" : "Login"
//        );
        getContentPane().add(gumb, BorderLayout.CENTER);
        gumb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Napravi prijavu...
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Languages");
        List<JMenuItem> items = new ArrayList<>();
        items.add(new JMenuItem("en"));
        items.add(new JMenuItem("de"));
        items.add(new JMenuItem("hr"));

        for (var item : items) {
            item.addActionListener((l) -> {
                JMenuItem it = (JMenuItem) l.getSource();
                LocalizationProvider.getInstance().setLanguage(it.getText());
            });
            menu.add(item);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Očekivao sam oznaku jezika kao argument!");
            System.err.println("Zadajte kao parametar hr ili en.");
            System.exit(-1);
        }
        final String jezik = args[0];
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LocalizationProvider.getInstance().setLanguage(jezik);
                new Prozor().setVisible(true);
            }
        });
    }
}