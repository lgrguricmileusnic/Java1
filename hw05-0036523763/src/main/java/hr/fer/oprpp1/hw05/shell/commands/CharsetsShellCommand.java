package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.List;

/**
 * {@code ShellCommand} implementation of command charsets.
 */
public class CharsetsShellCommand implements ShellCommand {
    /**
     * charsets command description
     */
    List<String> desc = List.of("Command used for displaying available charsets, takes no arguments");
    /**
     * charsets command name
     */
    String name = "charsets";

    /**
     * Executes charsets command.
     * Prints available charsets using passed environment.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status {@code CONTINUE}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (!arguments.equals("")) env.writeln("charsets command takes no arguments");
        for (String c : Charset.availableCharsets().keySet()) {
            env.writeln(c);
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
