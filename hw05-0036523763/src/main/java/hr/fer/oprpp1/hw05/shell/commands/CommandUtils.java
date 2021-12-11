package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CommandUtils {
    public static List<Path> parsePathArguments(String arguments) {
        List<Path> paths = new ArrayList<>();
        int i = 0;
        boolean insideString = false;
        String currentPath = "";

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
                            currentPath += c;
                        } else {
                            currentPath += '\\' + c;
                        }
                    }else if(c.equals('"')) {
                        insideString = false;
                        if(i < arguments.length() - 1) { //ako ima još znakova, provjeri da nakon navodnika ide razmak
                            if(arguments.charAt(i + 1) != ' ') throw new IllegalArgumentException("Invalid path format");
                        }
                        paths.add(Paths.get(currentPath));
                    }else {
                        currentPath += c;
                    }
                }catch(IndexOutOfBoundsException e) {
                    throw new IllegalArgumentException("Double quotes not closed");
                }
            }
            else {
                c = arguments.charAt(i);
                if(c.equals(' ') || c.equals('\t')){
                    if(!currentPath.isEmpty()) paths.add(Paths.get(currentPath));
                    currentPath = "";
                }
                else if(c.equals('"')){
                    if (!currentPath.isEmpty()) {
                        throw new IllegalArgumentException("Invalid path: " + currentPath + '"');
                    }
                    insideString = true;
                }
                else {
                    currentPath += c;
                }
            }
            i++;
        }
        if (insideString) throw new IllegalArgumentException("Double quotes not closed.");
        if (!currentPath.isEmpty()) paths.add(Paths.get(currentPath));
        return paths;
    }
}
