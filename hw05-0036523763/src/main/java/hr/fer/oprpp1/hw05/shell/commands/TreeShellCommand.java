package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

public class TreeShellCommand implements ShellCommand {
    /**
     * command name
     */
    String name = "tree";
    /**
     * command description
     */
    List<String> desc = Arrays.asList(
            "a recursive directory listing command that produces a depth indented listing of files",
            "usage tree <directory>");

    private static class TreeShellCommandFileVisitor implements FileVisitor<Path> {
        int level;
        Environment env;

        public TreeShellCommandFileVisitor(Environment env) {
            this.env = env;
            level = 0;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            env.writeln("  ".repeat(level) + "|" + dir.getFileName().toString());
            level++;
            return FileVisitResult.CONTINUE;
        }

        /**
         * Invoked for a file in a directory.
         *
         * @param file  a reference to the file
         * @param attrs the file's basic attributes
         * @return the visit result
         * @throws IOException if an I/O error occurs
         */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            env.writeln(" ".repeat(level) + "|" + file.getFileName().toString());
            return FileVisitResult.CONTINUE;
        }

        /**
         * Invoked for a file that could not be visited. This method is invoked
         * if the file's attributes could not be read, the file is a directory
         * that could not be opened, and other reasons.
         *
         * @param file a reference to the file
         * @param exc  the I/O exception that prevented the file from being visited
         * @return the visit result
         * @throws IOException if an I/O error occurs
         */
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            env.writeln("Failed visitng a file.");
            return FileVisitResult.TERMINATE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            level--;
            return FileVisitResult.CONTINUE;
        }
    }


    /**
     * Executes command.
     *
     * @param env       command environment
     * @param arguments command arguments
     * @return shell status
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = CommandUtils.parsePathArguments(arguments);
        if (args.size() != 1) {
            env.writeln("Invalid number of arguments. Use command help for more information.");
            return ShellStatus.CONTINUE;
        }
        Path p = Paths.get(args.get(0));
        if (!Files.isDirectory(p)) {
            env.writeln(args.get(0) + " is not a directory.");
            return ShellStatus.CONTINUE;
        }
        try {
            Files.walkFileTree(p, new TreeShellCommandFileVisitor(env));
        } catch (IOException e) {
            env.writeln("Walking file tree failed.");
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
