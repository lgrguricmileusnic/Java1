package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * {@code ShellCommand} implementation of copy command.
 */
public class CopyShellCommand implements ShellCommand {
    /**
     * command name
     */
    String name = "copy";
    /**
     * command description
     */
    List<String> desc = Arrays.asList("Copies source file to specified destination.",
            "If the specified destination is a directory, copies source file into that directory.",
            "usage: copy <src> <dest>");


    /**
     * Executes command.
     * Copies source file to specified destination.
     * Source and destination must be defined as whitespace separated arguments.
     * If a path contain spaces, surround them with quotes to allow for proper argument parsing.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status {@code CONTINUE}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = CommandUtils.parsePathArguments(arguments);
        if (args.size() != 2) {
            env.writeln("Invalid number of arguments. Use command help for more information.");
            return ShellStatus.CONTINUE;
        }
        Path src = Paths.get(args.get(0));
        Path dest = Paths.get(args.get(1));

        if (!Files.isRegularFile(src)) {
            env.writeln(args.get(0) + " is not a file.");
            return ShellStatus.CONTINUE;
        }

        if (!Files.isRegularFile(dest)) {
            if (Files.isDirectory(dest)) {
                dest = dest.resolve(src.getFileName());
            }
        }
        try (InputStream is = Files.newInputStream(src); OutputStream os = Files.newOutputStream(dest)) {
            byte[] buffer = new byte[4096];
            int r;
            while (true) {
                r = is.read(buffer);
                if (r < 1) break;
                os.write(buffer, 0, r);
            }
        } catch (IOException e) {
            env.writeln("Couldn't copy file");
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Gets copy command name.
     *
     * @return command name
     */
    @Override
    public String getCommandName() {
        return name;
    }

    /**
     * Gets copy command description.
     *
     * @return command description.
     */
    @Override
    public List<String> getCommandDescription() {
        return desc;
    }
}
