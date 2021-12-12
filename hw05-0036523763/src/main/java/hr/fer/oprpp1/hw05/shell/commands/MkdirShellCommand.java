package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * {@code ShellCommand} implementation of mkdir command.
 */
public class MkdirShellCommand implements ShellCommand {
    /**
     * command name
     */
    String name = "mkdir";
    /**
     * command description
     */
    List<String> desc = Arrays.asList("Creates specified directory structure",
            "usage: mkdir <path>");


    /**
     * Executes command.
     * Creates specified directory structure.
     * If specified directory structure contains spaces, it should be surrounded with quotes to allow for proper parsing.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = CommandUtils.parsePathArguments(arguments);
        if (args.size() != 1) {
            env.writeln("Invalid number of arguments. Use command help for more information.");
            return ShellStatus.CONTINUE;
        }
        Path p = Paths.get(args.get(0));
        if (!p.toFile().mkdirs()) {
            env.writeln("Creating directory structure failed.");
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
