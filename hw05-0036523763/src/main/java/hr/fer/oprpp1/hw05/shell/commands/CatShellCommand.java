package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CatShellCommand implements ShellCommand {
    /**
     * command name
     */
    String name = "cat";
    /**
     * command description
     */

    List<String> desc = Arrays.asList(
            "Writes file contents to console using specified charset.",
            "If charset is not specified, uses default charset",
            "usage: cat <file> [charset]"
            );


    /**
     * Executes command.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<Path> paths;
        try{
            paths = CommandUtils.parsePathArguments(arguments);
        }catch (IllegalArgumentException e) {
            env.writeln(e.getMessage());
            return ShellStatus.CONTINUE;
        }
        if(paths.size() > 2 || paths.size() < 1) {
            env.writeln("Invalid usage. Use command help for more information");
            return ShellStatus.CONTINUE;
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
