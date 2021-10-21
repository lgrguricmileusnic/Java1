package hr.fer.oprpp.hw02.prob1;

import java.util.Objects;

/**
 * Simple lexical analyzer, supplies the user with methods that convert input data into tokens.
 */
public class Lexer {
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
    public Lexer(String text) {
        Objects.requireNonNull(text);
        data = text.toCharArray();
        token = null;
        currentIndex = 0;
        state = LexerState.BASIC;
    }

    /**
     * Generates and returns next token
     *
     * @return next token
     * @throws LexerException if a problem occurs
     */
    public Token nextToken() {
        char current = 0;
        boolean escaped = false;

        String tokenValue;

        do {
            if (isDone()) {
                token = new Token(TokenType.EOF, null);
                currentIndex++;
                return token;
            }
            current = data[currentIndex++];

        } while (current == '\n' || current == '\r' || current == '\t' || Character.isWhitespace(current));

        tokenValue = "";
        switch (state) {
            case BASIC -> {
                while (Character.isLetter((current)) || escaped || ((Character) current).equals('\\')) {
                    if (((Character) current).equals('\\') && !escaped) {
                        escaped = true;
                        if (isDone()) throw new LexerException("Invalid escape ending!");
                        current = data[currentIndex++];
                        if (Character.isLetter(current))
                            throw new LexerException("Invalid escape sequence! Cannot escape a letter.");
                        continue;
                    }
                    escaped = false;
                    tokenValue += Character.toString(current);
                    if(isDone()) break;
                    current = data[currentIndex++];
                }
                ;
                if (!tokenValue.equals("")) {
                    if (!isDone()) currentIndex--;
                    return token = new Token(TokenType.WORD, tokenValue);
                }


                while (Character.isDigit((current))) {
                    tokenValue += Character.toString(current);
                    if (isDone()) break;
                    current = data[currentIndex++];
                }
                ;

                if (!tokenValue.equals("")) {
                    try {
                        if (!isDone()) currentIndex--;
                        return new Token(TokenType.NUMBER, Long.valueOf(tokenValue));
                    } catch (NumberFormatException e) {
                        throw new LexerException(e.getMessage());
                    }
                }

                return token = new Token(TokenType.SYMBOL, current);
            }
            case EXTENDED -> {
                if (current == '#') {
                    return token = new Token(TokenType.SYMBOL, current);
                }
                while (!Character.isWhitespace(current) && current != '#') {
                    tokenValue += current;
                    if (isDone()) break;
                    current = data[currentIndex++];
                }
                currentIndex--;
                return token = new Token(TokenType.WORD, tokenValue);
            }


        }

        return null;
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
