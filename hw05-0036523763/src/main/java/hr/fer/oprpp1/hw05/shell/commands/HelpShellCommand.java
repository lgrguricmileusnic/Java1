package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * {@code ShellCommand} representation of help command.
 */
public class HelpShellCommand implements ShellCommand {
    /**
     * command name
     */
    String name = "help";
    /**
     * command description
     */
    List<String> desc = Arrays.asList("Help command can be used to find more information on the specified command and its usage.",
            "Using help without any arguments prints a list of all available commands.");

    /**
     * Executes command.
     * Help command takes up to one argument.
     * If there is no arguments, prints command name and description using passed environment for all available commands.
     * Otherwise, prints command name and description for specified command.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status {@code CONTINUE}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" ");
        if (args.length > 1) {
            env.writeln("Invalid usage. Use command help for more information.");
            return ShellStatus.CONTINUE;
        }
        if (args[0].equals("")) {
            Collection<ShellCommand> commands = env.commands().values();
            for (ShellCommand d : commands) {
                env.writeln(d.getCommandName());
                d.getCommandDescription().forEach(env::writeln);
                env.writeln("");
            }
        } else {
            ShellCommand command = env.commands().get(args[0]);
            if (command == null) {
                env.writeln("Unknown command: " + args[0]);
                return ShellStatus.CONTINUE;
            }
            env.writeln(command.getCommandName());
            command.getCommandDescription().forEach(env::writeln);
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
