package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * {@code ShellCommand} implementation of command cat.
 */
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
     * Executes cat command.
     * Command prints file contents using passed environment and charset specified from environment input.
     * If file path contains spaces, it should be surrounded with quotes to allow for proper parsing.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status {@code CONTINUE}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args;
        try {
            args = CommandUtils.parsePathArguments(arguments);
        } catch (IllegalArgumentException e) {
            env.writeln(e.getMessage());
            return ShellStatus.CONTINUE;
        }
        if (args.size() > 2 || args.size() < 1) {
            env.writeln("Invalid number of arguments. Use command help for more information.");
            return ShellStatus.CONTINUE;
        }
        Charset charset = Charset.defaultCharset();
        if (args.size() == 2) {
            charset = Charset.availableCharsets().get(args.get(1));
            if (charset == null) {
                env.writeln("Invalid charset.");
                return ShellStatus.CONTINUE;
            }
        }
        Path path = Paths.get(args.get(0));
        if (!Files.isRegularFile(path)) {
            env.writeln(args.get(0) + " is not a file.");
            return ShellStatus.CONTINUE;
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(Files.newInputStream(path)), charset))) {
            String l;
            while (true) {
                l = br.readLine();
                if(l == null) break;
                env.writeln(l);
            }
        } catch (IOException e) {
            env.writeln("Unable to cat file.");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Gets cat command name.
     *
     * @return cat command name
     */
    @Override
    public String getCommandName() {
        return name;
    }

    /**
     * Gets cat command description.
     *
     * @return cat command description.
     */
    @Override
    public List<String> getCommandDescription() {
        return desc;
    }
}
