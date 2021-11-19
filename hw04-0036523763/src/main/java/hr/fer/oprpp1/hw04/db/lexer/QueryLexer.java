package hr.fer.oprpp1.hw04.db.lexer;


import java.util.Arrays;
import java.util.Objects;

/**
 * Query lexer, supplies the client with methods that convert inputted query into tokens.
 */
public class QueryLexer {
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

    /**
     * Current lexer state
     */
    private LexerState state;


    /**
     * Constructs a {@code QueryLexer} for the passed input text
     *
     * @param text text for lexical analysis
     */
    public QueryLexer(String text) {
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
        switch(state){
            case BASIC -> {
                if(isDone()) {
                    currentIndex++;
                    return token = new Token(TokenType.EOF, null);
                }
                char current = data[currentIndex];
                String value = "";
                while (current == '\n' || current == '\t' || Character.isWhitespace(current)) {
                    currentIndex++;
                    if (isDone()) {
                        currentIndex++;
                        return token = new Token(TokenType.EOF, null);
                    }
                    current = data[currentIndex];
                }

                if(Character.isLetter(current)) {
                    while(Character.isLetter(current)) {
                        value += current;
                        currentIndex++;
                        if(isDone()) break;
                        current = data[currentIndex];
                    }
                    switch (value) {
                        case "LIKE" -> {return token =  new Token(TokenType.OPERATOR, "LIKE");}
                        case "lastName" -> {return token =  new Token(TokenType.FIELD, "lastName");}
                        case "firstName" -> {return token =  new Token(TokenType.FIELD, "firstName");}
                        case "jmbag" -> {return token =  new Token(TokenType.FIELD, "jmbag");}
                        default -> {
                            if(value.equalsIgnoreCase("AND")) return token =  token = new Token(TokenType.LOGICAL, "AND");
                            throw new LexerException("Unknown fieldname: " + value);
                        }
                    }
                }

                switch (current) {
                    case '<', '>' -> {
                        if(isDone()) return token =  new Token(TokenType.OPERATOR, Character.toString(current));
                        if(data[++currentIndex] == '=') {
                            currentIndex++;
                            return token =  new Token(TokenType.OPERATOR, current + "=");
                        }
                        return token =  new Token(TokenType.OPERATOR, Character.toString(current));
                    }
                    case '!' -> {
                        if(isDone()) throw new LexerException("Invalid character: !");
                        if(data[++currentIndex] == '=') {
                            currentIndex++;
                            return token =  new Token(TokenType.OPERATOR, "!=");
                        }
                        throw new LexerException("Invalid character: !");
                    }
                    case '=' -> {
                        currentIndex++;
                        return token =  new Token(TokenType.OPERATOR, "=");}
                    case '"' -> {
                        currentIndex++;
                        return token =  new Token(TokenType.DOUBLE_QUOTES, "\"");}
                }

                throw new LexerException("Invalid character: " + current);
            }
            case STRING -> {
                if(isDone()) throw new LexerException("String not closed, reached EOF");
                char current = data[currentIndex];
                String value = "";
                while(current != '"') {
                    value += current;
                    currentIndex++;
                    if(isDone()) throw new LexerException("String not closed, reached EOF");
                    current=data[currentIndex];
                }
                return token =  new Token(TokenType.STRING, value);
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

    /**
     * Changes lexer state to passed state.
     * @param newState desired {@code LexerState}
     */
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
