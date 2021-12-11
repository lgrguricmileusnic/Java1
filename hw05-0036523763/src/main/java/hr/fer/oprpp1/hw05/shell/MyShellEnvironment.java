package hr.fer.oprpp1.hw05.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.SortedMap;

/**
 * Environment implementation for MyShell
 */
public class MyShellEnvironment implements Environment{
    /**
     * Prompt symbol for MyShell
     */
    private Character promptSymbol;
    /**
     * Morelines symbol for MyShell
     */
    private Character morelinesSymbol;
    /**
     * Multiline symbol for MyShell
     */
    private Character multilineSymbol;

    /**
     * underlying map storing all available commands and their {@code ShellCommand} representations for this shell
     */
    private SortedMap<String,ShellCommand> commands;

    /**
     * Reads line from input.
     *
     * @return line
     * @throws ShellIOException if there was a problem when reading from input
     */
    @Override
    public String readLine() throws ShellIOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new ShellIOException("Reading from stdin resulted in an error");
        }
    }

    /**
     * Writes {@code String} to output.
     *
     * @param text {@code String} which will be written to output
     * @throws ShellIOException if there was a problem when writing to output
     */
    @Override
    public void write(String text) throws ShellIOException {
        System.out.print(text);
    }

    /**
     * Writes {@code String} to output, appending newline symbol.
     *
     * @param text {@code String} which will be written to output
     * @throws ShellIOException if there was a problem when writing to output
     */
    @Override
    public void writeln(String text) throws ShellIOException {
        System.out.println(text);
    }

    /**
     * Returns {@code UnmodifiableSortedMap<String,ShellCommand>} of command names and its {@code ShellCommand} representations.
     *
     * @return {@code UnmodifiableSortedMap<String,ShellCommand>} of command names and its {@code ShellCommand} representations
     */
    @Override
    public SortedMap<String, ShellCommand> commands() {
        return Collections.unmodifiableSortedMap(commands);
    }

    /**
     * Gets multiline symbol.
     *
     * @return multiline symbol
     */
    @Override
    public Character getMultilineSymbol() {
        return multilineSymbol;
    }

    /**
     * Sets multiline symbol
     *
     * @param symbol new multiline symbol
     */
    @Override
    public void setMultilineSymbol(Character symbol) {
        multilineSymbol = symbol;
    }

    /**
     * Gets prompt symbol.
     *
     * @return prompt symbol
     */
    @Override
    public Character getPromptSymbol() {
        return promptSymbol;
    }

    /**
     * Sets prompt symbol
     *
     * @param symbol new prompt symbol
     */
    @Override
    public void setPromptSymbol(Character symbol) {
        promptSymbol = symbol;
    }

    /**
     * Gets morelines symbol.
     *
     * @return morelines symbol
     */
    @Override
    public Character getMorelinesSymbol() {
        return morelinesSymbol;
    }

    /**
     * Sets morelines symbol
     *
     * @param symbol new multiline symbol
     */
    @Override
    public void setMorelinesSymbol(Character symbol) {
        morelinesSymbol = symbol;
    }
}
