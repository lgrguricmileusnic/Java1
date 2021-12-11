package hr.fer.oprpp1.hw05.shell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents symbol command, used for changing prompt, multiline and morelines symbols.
 */
public class SymbolShellCommand implements ShellCommand {
    List<String> desc = Arrays.asList("Command used for changing prompt, multiline and morelines symbols.\n",
            "\nusage: symbol [PROMPT|MORELINES|MULTILINE] <new symbol>");
    String name = "symbol";
    /**
     * Executes command.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" ");
        if(args.length != 2) {
            throw new IllegalArgumentException("Invalid usage. Use help <command_name> to see appropriate usage.");
        }
        args[]
    }

    /**
     * Gets command name.
     *
     * @return command name
     */
    @Override
    public String getCommandName() {
        return "symbol";
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
