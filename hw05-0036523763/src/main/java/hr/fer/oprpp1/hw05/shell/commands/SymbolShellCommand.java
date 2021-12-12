package hr.fer.oprpp1.hw05.shell.commands;


import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents symbol command, used for changing prompt, multiline and morelines symbols.
 */
public class SymbolShellCommand implements ShellCommand {
    List<String> desc = Arrays.asList("Command used for changing prompt, multiline and morelines symbols.",
            "usage: symbol [PROMPT|MORELINES|MULTILINE] <new symbol>");
    String name = "symbol";

    /**
     * Executes command.
     * Command specified environment symbol to the passed symbol.
     * Arguments should be formatted as {@code [PROMPT|MORELINES|MULTILINE] <new symbol>}.
     * If new symbol is not passed, information about the current symbol will be printed using passed environment.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status {@code CONTINUE}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] args = arguments.split(" ");
        if (args[0].equals("") || args.length > 2) {
            env.writeln("Invalid usage. Use help " + name + " to see appropriate usage.");
            return ShellStatus.CONTINUE;
        }
        switch (args[0]) {
            case "PROMPT" -> {
                if (args.length == 1) {
                    env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
                } else {
                    if (args[1].length() > 1) {
                        env.writeln("Symbol must be one character long.");
                        return ShellStatus.CONTINUE;
                    }
                    env.writeln(String.format("Symbol for PROMPT changed from '%c' to '%c'", env.getPromptSymbol(), args[1].charAt(0)));
                    env.setPromptSymbol(args[1].charAt(0));
                }
            }
            case "MORELINES" -> {
                if (args.length == 1) {
                    env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
                } else {
                    if (args[1].length() > 1) {
                        env.writeln("Symbol must be one character long.");
                        return ShellStatus.CONTINUE;
                    }
                    env.writeln(String.format("Symbol for MORELINES changed from '%c' to '%c'", env.getMorelinesSymbol(), args[1].charAt(0)));
                    env.setMorelinesSymbol(args[1].charAt(0));
                }

            }
            case "MULTILINE" -> {
                if (args.length == 1) {
                    env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
                } else {
                    if (args[1].length() > 1) {
                        env.writeln("Symbol must be one character long.");
                        return ShellStatus.CONTINUE;
                    }
                    env.writeln(String.format("Symbol for MULTILINE changed from '%c' to '%c'", env.getMultilineSymbol(), args[1].charAt(0)));
                    env.setMultilineSymbol(args[1].charAt(0));
                }
            }

            default -> {
                env.writeln("Unknown symbol: " + args[0]);
            }
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
        return Collections.unmodifiableList(desc);
    }
}
