package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Interface describes a shell environment, supplying the client with
 * methods for writing and reading to the shell.
 * Implementations of {@code Environment} must also implement getters and setters for
 * prompt symbol, multiline symbol and morelines symbol.
 */
public interface Environment {
    /**
     * Reads line from input.
     * @return line
     * @throws ShellIOException if there was a problem when reading from input
     */
    String readLine() throws ShellIOException;

    /**
     * Writes {@code String} to output.
     * @param text {@code String} which will be written to output
     * @throws ShellIOException if there was a problem when writing to output
     */
    void write(String text) throws ShellIOException;

    /**
     * Writes {@code String} to output, appending newline symbol.
     * @param text {@code String} which will be written to output
     * @throws ShellIOException if there was a problem when writing to output
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Returns unmodifiable map of command names and its {@code ShellCommand} representations.
     * @return map of command names and its {@code ShellCommand} representations
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Gets multiline symbol.
     * @return multiline symbol
     */
    Character getMultilineSymbol();

    /**
     * Sets multiline symbol
     * @param symbol new multiline symbol
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Gets prompt symbol.
     * @return prompt symbol
     */
    Character getPromptSymbol();

    /**
     * Sets prompt symbol
     * @param symbol new prompt symbol
     */
    void setPromptSymbol(Character symbol);

    /**
     * Gets morelines symbol.
     * @return morelines symbol
     */
    Character getMorelinesSymbol();

    /**
     * Sets morelines symbol
     * @param symbol new multiline symbol
     */
    void setMorelinesSymbol(Character symbol);
}
