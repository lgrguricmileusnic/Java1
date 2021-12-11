package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandUtils {
    public static List<String> parseArguments(String arguments) {
        List<String> args = new ArrayList<>();
        int i = 0;
        boolean insideString = false;
        String currentArg = "";

        arguments = arguments.strip();
        Character c;
        while(i <= arguments.length() - 1) {
            if(insideString) {
                try{
                    c = arguments.charAt(i);
                    if (c.equals('\\')) {
                        i++;
                        c = arguments.charAt(i);
                        if(c.equals('\'') || c.equals('"')){
                            currentArg += c;
                        } else {
                            currentArg += '\\' + c;
                        }
                    }else if(c.equals('"')) {
                        insideString = false;
                        if(i < arguments.length() - 1) { //ako ima još znakova, provjeri da nakon navodnika ide razmak
                            if(arguments.charAt(i + 1) != ' ') throw new IllegalArgumentException("Invalid path format");
                        }
                        if(!currentArg.isEmpty()) {
                            args.add(currentArg);
                            currentArg="";
                        }
                    }else {
                        currentArg += c;
                    }
                }catch(IndexOutOfBoundsException e) {
                    throw new IllegalArgumentException("Double quotes not closed");
                }
            }
            else {
                c = arguments.charAt(i);
                if(c.equals(' ') || c.equals('\t')){
                    if(!currentArg.isEmpty()) args.add(currentArg);
                    currentArg = "";
                }
                else if(c.equals('"')){
                    if (!currentArg.isEmpty()) {
                        throw new IllegalArgumentException("Invalid path: " + currentArg + '"');
                    }
                    insideString = true;
                }
                else {
                    currentArg += c;
                }
            }
            i++;
        }
        if (insideString) throw new IllegalArgumentException("Double quotes not closed.");
        if (!currentArg.isEmpty()) args.add(currentArg);
        return args;
    }

    public static String formatPathDateAndTime(Path path) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS );
        BasicFileAttributes attributes = faView.readAttributes();
        FileTime fileTime = attributes.creationTime();
        return sdf.format(new Date(fileTime.toMillis()));
    }
}
