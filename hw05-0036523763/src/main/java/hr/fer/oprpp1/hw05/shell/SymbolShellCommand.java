package hr.fer.oprpp1.hw05.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents symbol command, used for changing prompt, multiline and morelines symbols.
 */
public class SymbolShellCommand implements ShellCommand {
    /**
     * Executes command.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        return null;
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
        List<String> desc = new ArrayList<>();
        desc.add("Command used for changing prompt, multiline and morelines symbols.\n");
        desc.add("\nusage: symbol [PROMPT|MORELINES|MULTILINE] <new symbol>")
        return desc;
    }
}
