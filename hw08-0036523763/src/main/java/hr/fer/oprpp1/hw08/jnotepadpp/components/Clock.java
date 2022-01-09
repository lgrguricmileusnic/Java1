package hr.fer.oprpp1.hw08.jnotepadpp.components;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Clock extends JLabel {
    public Clock() {
        super();
        update();
        setHorizontalAlignment(SwingConstants.RIGHT);
    }

    public void update() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        setText(formatter.format(date));
    }

}
