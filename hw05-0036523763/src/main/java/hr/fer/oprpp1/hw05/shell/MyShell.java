package hr.fer.oprpp1.hw05.shell;

/**
 * Simple shell command-line application.
 */
public class MyShell {
    /**
     * Builds environment and starts shell.
     * @param args none
     */
    public static void main(String[] args) {
        ShellStatus status = ShellStatus.CONTINUE;
        Environment env = new MyShellEnvironment();
        env.writeln("Welcome to MyShell v 1.0");

        String line, commandName, arguments;
        ShellCommand command;
        do {
            env.write(env.getPromptSymbol() + " ");
            try{
                line = env.readLine().strip();
                if (line.endsWith("" + env.getMorelinesSymbol())) {
                    do {
                        env.write(env.getMultilineSymbol() + " ");
                        line = line.substring(0, line.length() - 1);
                        line += env.readLine().stripLeading().stripTrailing();
                    } while (line.endsWith("" + env.getMorelinesSymbol()));
                }
            }catch(ShellIOException e) {
                env.writeln("There was a problem reading input, terminating shell");
                return;
            }

            int i = line.indexOf(' ');
            if (i != -1) {
                commandName = line.substring(0, i);
                arguments = line.substring(i + 1);
            } else {
                commandName = line;
                arguments = "";
            }
            command = env.commands().get(commandName);
            if (command == null) {
                env.writeln("Unknown command: " + commandName);
                continue;
            }
            status = command.executeCommand(env, arguments);
        } while (status != ShellStatus.TERMINATE);
    }
}
