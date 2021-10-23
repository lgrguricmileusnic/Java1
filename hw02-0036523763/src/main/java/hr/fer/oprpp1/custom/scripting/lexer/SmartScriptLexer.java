package hr.fer.oprpp1.custom.scripting.lexer;


import hr.fer.oprpp.hw02.prob1.LexerException;

import java.util.Objects;

/**
 * Simple lexical analyzer, supplies the client with methods that convert input data into tokens.
 */
public class SmartScriptLexer {
    /**
     * text inputted for lexical analysis
     */
    private char[] data;
    /**
     * current token
     */
    private Token token;
    /**
     * index of the first non-analysed character
     */
    private int currentIndex;


    private LexerState state;

    /**
     * Constructs a {@code Lexer} for the passed input text
     *
     * @param text text for lexical analysis
     */
    public SmartScriptLexer(String text) {
        Objects.requireNonNull(text);
        data = text.toCharArray();
        token = null;
        currentIndex = 0;
        state = LexerState.TEXT;
    }

    /**
     * Generates and returns next token
     *
     * @return next token
     * @throws LexerException if a problem occurs
     */
    public Token nextToken() {
        isDone();
        String tokenValue = "";
        Character current = data[currentIndex];
        switch (state) {
            case TAG -> {
                while (current == '\n' || current == '\r' || current == '\t' || Character.isWhitespace(current)) {
                    if (isDone()) {
                        currentIndex++;
                        return token = new Token(TokenType.EOF, null);
                    }
                    current = data[++currentIndex];
                }

                if (Character.isLetter(current)) {
                    while (Character.isLetter(current) || Character.isDigit(current) || current.equals('_')) {
                        tokenValue += current;
                        if (isDone()) break;
                        current = data[++currentIndex];
                    }
                }
                if (!tokenValue.isEmpty()) return token = new Token(TokenType.VARIABLE, tokenValue);

                if (current.equals('@')) {
                    if (isDone()) throw new LexerException("Invalid function name: reached EOF after @");
                    current = data[++currentIndex];
                    if (!Character.isLetter(current))
                        throw new LexerException("Invalid function name: must start with a letter");
                    while (Character.isLetter(current) || Character.isDigit(current) || current.equals('_')) {
                        tokenValue += current;
                        if (isDone()) break;
                        current = data[++currentIndex];
                    }

                    return token = new Token(TokenType.FUNCTION, tokenValue);
                }

                if (current.equals('-') && !isDone()) {
                    if (Character.isDigit(data[currentIndex + 1])) {
                        tokenValue += current;
                        current = data[++currentIndex];
                    }
                }
                boolean isDouble = false;
                while (Character.isDigit(current)) {
                    tokenValue += current;
                    if (isDone()) break;
                    current = data[++currentIndex];
                    if (current.equals('.') && !isDone() && !isDouble) {
                        if (Character.isDigit(data[currentIndex + 1])) {
                            tokenValue += current;
                            current = data[++currentIndex];
                            isDouble = true;
                        }
                    }
                }
                if (!tokenValue.isEmpty()) {
                    if (isDouble) {
                        return token = new Token(TokenType.DOUBLE, Double.valueOf(tokenValue));
                    } else {
                        return token = new Token(TokenType.INTEGER, Integer.valueOf(tokenValue));
                    }
                }
                if (current.equals('"') || current.equals('=')) {
                    currentIndex++;
                    return token = new Token(TokenType.SPECIAL, current.toString());
                }

                if (current.equals('$')) {
                    if (!isDone()) {
                        if (((Character) data[currentIndex + 1]).equals('}')) {
                            currentIndex = currentIndex + 2;
                            return token = new Token(TokenType.SPECIAL, "$}");
                        }
                    }
                }

                for (Character operator : new Character[]{'+', '-', '/', '*', '^'}) {
                    if (current.equals(operator)) {
                        currentIndex++;
                        return token = new Token(TokenType.OPERATOR, current);
                    }
                }
                throw new LexerException("Illegal character inside tag: " + current);
            }
            case TEXT -> {
                if (isDone()) {
                    currentIndex++; // increments index to out of bounds, so next call of nextToken can throw error
                    return token = new Token(TokenType.EOF, null);
                }

                if (current.equals('{')) {
                    if (!isDone()) {
                        if (((Character) data[currentIndex + 1]).equals('$')) {
                            currentIndex = currentIndex + 2;
                            return token = new Token(TokenType.SPECIAL, "{$");
                        }
                    }
                }

                while (true) {
                    if (current.equals('\\')) {
                        if (isDone()) throw new LexerException("Invalid escape sequence: \\");
                        current = data[++currentIndex];
                        if (!current.equals('\\') && !current.equals('{'))
                            throw new LexerException("Invalid escape sequence: \\" + current);
                        tokenValue += current;
                        if (isDone()) break;
                        current = data[++currentIndex];
                        continue;
                    }
                    if (current.equals('{') && !isDone()) {
                        if (((Character) data[currentIndex + 1]).equals('$')) {
                            break;
                        }
                    }

                    tokenValue += current;
                    if (isDone()) break;
                    current = data[++currentIndex];
                }
                return token = new Token(TokenType.TEXT, tokenValue);
            }
            case STRING -> {
                if (current.equals('"')) return token = new Token(TokenType.SPECIAL, '"');
                while (true) {
                    if (current.equals('\\')) {
                        if (isDone()) throw new LexerException("Invalid escape sequence: \\");
                        current = data[++currentIndex];
                        if (!current.equals('\\') && !current.equals('"'))
                            throw new LexerException("Invalid escape sequence: \\" + current);
                        tokenValue += current;
                        if (isDone()) break;
                        current = data[++currentIndex];
                        continue;
                    }

                    if (current.equals('"')) break;

                    tokenValue += current;
                    if (isDone()) {
                        throw new LexerException("String not terminated!");
                    }
                    current = data[++currentIndex];
                }
//                if (tokenValue.contains(".")) {
//                    try {
//                        return token = new Token(TokenType.DOUBLE, Double.parseDouble(tokenValue));
//                    } catch (NumberFormatException e) {
//                        return token = new Token(TokenType.TEXT, tokenValue);
//                    }
//                }
//                try {
//                    return token = new Token(TokenType.INTEGER, Integer.parseInt(tokenValue));
//                } catch (NumberFormatException ignored) {}

                return token = new Token(TokenType.STRING, tokenValue);
            }
            default -> {
                return null;
            }
        }

    }

    /**
     * Returns last generated token. Can be called multiple times without generating next token.
     *
     * @return last generated token
     */
    public Token getToken() {
        return token;
    }


    public void setState(LexerState newState) {
        Objects.requireNonNull(newState);
        state = newState;
    }

    /**
     * Checks if {@code currentIndex} reached data array upper bound.
     *
     * @return true if currentIndex equals {@code data} array {@code length}, otherwise false
     */
    private boolean isDone() {
        if (currentIndex == data.length) {
            return true;
        } else if (currentIndex > data.length) throw new LexerException("End of field reached!");
        return false;
    }


}
