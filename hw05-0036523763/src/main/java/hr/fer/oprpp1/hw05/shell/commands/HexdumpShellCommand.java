package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * {@code ShellCommand} implementation of hexdump command.
 */
public class HexdumpShellCommand implements ShellCommand {
    /**
     * command name
     */
    String name = "hexdump";
    /**
     * command description
     */
    List<String> desc = Arrays.asList("Prints hexdump of specified file to console.",
            "usage: hexdump <file>");

    /**
     * Executes command.
     * Command prints hexdump of specified file.
     * If file path contains spaces, it should be surrounded with quotes to allow for proper parsing.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status {@code CONTINUE}
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = CommandUtils.parsePathArguments(arguments);
        if (args.size() != 1) {
            env.writeln("Invalid number of arguments. Use command help for more information.");
            return ShellStatus.CONTINUE;
        }
        Path p = Paths.get(args.get(0));
        if (!Files.isRegularFile(p)) {
            env.writeln(args.get(0) + " is not a file.");
            return ShellStatus.CONTINUE;
        }

        try (InputStream is = Files.newInputStream(p)) {
            byte[] buff = new byte[16];
            int r;
            int address = 0;
            String line;
            while (true) {
                r = is.read(buff);
                if (r < 1) break;
                line = String.format("%8s:", Integer.toHexString(address)).replace(' ', '0');
                line += " ";
                for (int i = 0; i < buff.length; i++) {
                    if (i <= r - 1) {
                        line += String.format("%02X", buff[i]);
                    } else {
                        line += "  ";
                    }
                    line += (i == 7) ? "|" : " ";
                }
                line += "| ";
                for (int i = 0; i < buff.length; i++) {
                    if (i > r - 1) {
                        line += " ";
                        continue;
                    }
                    char c = (char) (buff[i] & 0xFF);
                    if (c < 32 || c > 127) c = '.';
                    line += c;
                }
                env.writeln(line);
                address += 16;
            }


        } catch (IOException e) {
            env.writeln("Couldn't read file.");
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
