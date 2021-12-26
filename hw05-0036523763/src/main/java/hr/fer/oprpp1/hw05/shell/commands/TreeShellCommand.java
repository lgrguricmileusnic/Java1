package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

/**
 * {@code ShellCommand} implemention of tree command.
 */
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

    /**
     * {@code FileVisitor} implementation for {@code TreeShellCommand}.
     * Prints file tree, indenting file/directory names by level.
     */
    private static class TreeShellCommandFileVisitor implements FileVisitor<Path> {
        /**
         * level of file (depth)
         */
        int level;
        /**
         * environment used for printing
         */
        Environment env;

        /**
         * Constructs {@code TreeShellCommandFileVisitor} with passed {@code Environment}.
         * Initializes level (depth) to 0.
         * @param env environment used for printing
         */
        public TreeShellCommandFileVisitor(Environment env) {
            this.env = env;
            level = 0;
        }

        /**
         * Invoked before visiting a directory.
         * Prints directory name appending {@code level} number of indentations before it.
         * Increments one depth level.
         * @param dir reference to directory
         * @param attrs directory attributes
         * @return {@code FileVisitResult.CONTINUE}
         */
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs){
            env.writeln("  ".repeat(level) + "|" + dir.getFileName().toString());
            level++;
            return FileVisitResult.CONTINUE;
        }

        /**
         * Invoked for a file in a directory.
         * Prints file name appending {@code level} number of indentations before it.
         * @param file  a reference to the file
         * @param attrs the file's basic attributes
         * @return the visit result
         */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
            env.writeln("  ".repeat(level) + "|" + file.getFileName().toString());
            return FileVisitResult.CONTINUE;
        }

        /**
         * Invoked for a file that could not be visited. This method is invoked
         * if the file's attributes could not be read, the file is a directory
         * that could not be opened, and other reasons.
         *
         * @param file a reference to the file
         * @param exc  the I/O exception that prevented the file from being visited
         * @return {@code FileVisitResult.TERMINATE}
         */
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            env.writeln("Failed visitng a file.");
            return FileVisitResult.TERMINATE;
        }

        /**
         * Invoked after visiting a directory.
         * Decrements one depth level.
         * @param dir reference to directory
         * @param exc null if the iteration of the directory completes without an error;
         *            otherwise the I/O exception that caused the iteration of the directory to complete prematurely
         * @return {@code FileVisitResult.CONTINUE}
         */
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            level--;
            return FileVisitResult.CONTINUE;
        }
    }


    /**
     * Executes command.
     * Command prints tree of specified directory using passed environment.
     * If directory path contains spaces, it should be surronded with quotes.
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
