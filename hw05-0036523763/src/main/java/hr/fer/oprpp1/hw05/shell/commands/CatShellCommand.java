package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        List<String> args;
        try{
            args = CommandUtils.parseArguments(arguments);
        }catch (IllegalArgumentException e) {
            env.writeln(e.getMessage());
            return ShellStatus.CONTINUE;
        }
        if(args.size() > 2 || args.size() < 1) {
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
        if(!Files.isRegularFile(path)) {
            env.writeln(args.get(0) + " is not a file.");
            return ShellStatus.CONTINUE;
        }
        BufferedReader is;
        try {
            is = Files.newBufferedReader(path, charset);
            char[] buff = new char[100];
            int r;
            while(true) {
                r = is.read(buff);
                if(r < 1) break;
                env.write(String.valueOf(buff, 0, r));
            }
            env.writeln("");
        } catch (IOException e) {
            throw new ShellIOException("Unable to read file.");
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
