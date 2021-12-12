package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Class represents a shell command and supplies the client with getters for its name and description and
 * a method for its execution.
 */
public interface ShellCommand {
    /**
     * Executes command.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     * Gets command name.
     *
     * @return command name
     */
    String getCommandName();

    /**
     * Gets command description.
     *
     * @return command description.
     */
    List<String> getCommandDescription();
}
