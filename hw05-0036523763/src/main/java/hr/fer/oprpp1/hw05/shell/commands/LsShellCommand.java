package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class LsShellCommand implements ShellCommand {
    /**
     * command name
     */
    String name = "ls";
    /**
     * command description
     */
    List<String> desc = Arrays.asList("lists all files in specified directory",
            "first column indicates if current object is directory (d), readable (r), writable (w) and executable (x)",
            "second column contains object size in bytes",
            "third and forth columns contain date and time of creation",
            "usage: ls <directory>");

    
    /**
     * Executes command.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = CommandUtils.parseArguments(arguments);
        if(args.size() != 1) {
            env.writeln("Invalid number of arguments. Use command help for more information.");
            return ShellStatus.CONTINUE;
        }
        Path p = Paths.get(args.get(0));
        if(!Files.isDirectory(p)) {
            env.writeln(args.get(0) + " is not a directory.");
            return ShellStatus.CONTINUE;
        }
        try {
            List<Path> list = Files.list(p).toList();
            for(Path path : list){
                String line = Files.isDirectory(path) ? "d" : "-";
                line += Files.isReadable(path) ? "r" : "-";
                line += Files.isWritable(path) ? "w" : "-";
                line += Files.isExecutable(path) ? "x " : "- ";
                line += String.format("%10d ", Files.size(path));
                line += CommandUtils.formatPathDateAndTime(path) + " ";
                line += path.getFileName().toString();
                env.writeln(line);
            }
        } catch (IOException e) {
            throw new ShellIOException("Unable to list files in directory.");
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Gets command name.
     *
     * @return command name
     */
    @Override
    public String getCommandName() {
        return name;
    }

    /**
     * Gets command description.
     *
     * @return command description.
     */
    @Override
    public List<String> getCommandDescription() {
        return desc;
    }
}
