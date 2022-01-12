package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Clock component displays time in yyyy/MM/dd hh:mm:ss format.
 * Updates date and time every 1000ms.
 */
public class Clock extends JLabel {
    /**
     * Creates {@code Clock}
     */
    public Clock() {
        super();
        setHorizontalAlignment(SwingConstants.RIGHT);
        Timer timer = new Timer(1000, e -> update());
        timer.start();
    }

    /**
     * Updates clock date and time.
     */
    public void update() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        setText(formatter.format(date));
    }

}
