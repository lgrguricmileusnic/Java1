package hr.fer.oprpp1.hw04.db.lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryLexerTest {

    @Test
    public void testNotNull() {
        QueryLexer lexer = new QueryLexer("");
        assertNotNull(lexer.nextToken());
    }


    @Test
    public void testNullInput() {
        assertThrows(NullPointerException.class, () -> new QueryLexer(null));
    }


    @Test
    public void testEmptyInput() {
        QueryLexer lexer = new QueryLexer("");
        assertEquals(TokenType.EOF, lexer.nextToken().getType());
    }


    @Test
    public void testGetReturnsLastToken() {
        QueryLexer lexer = new QueryLexer("");

        Token token = lexer.nextToken();
        assertEquals(token, lexer.getToken());
        assertEquals(token, lexer.getToken());

    }


    @Test
    public void testExceptionAfterEOF() {
        QueryLexer lexer = new QueryLexer("");

        assertEquals(TokenType.EOF, lexer.nextToken().getType());
        assertThrows(LexerException.class, lexer::nextToken);
    }


    @Test
    public void testBlankInput() {
        QueryLexer lexer = new QueryLexer(" \t");
        assertEquals(TokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testInvalidStringTermination() {
        QueryLexer lexer = new QueryLexer("\"String not terminated");
        lexer.setState(LexerState.BASIC); //setting state to BASIC so lexer can start detecting quotes
        lexer.nextToken(); //here the client would get the " token, meaning they should switch the lexer state to string
        lexer.setState(LexerState.STRING);
        assertThrows(LexerException.class, lexer::nextToken);
    }

    @Test
    public void testOperators() {
        String text = "< <= > >= = != LIKE";
        QueryLexer lexer = new QueryLexer(text);
        Token token;
        for (String operator: text.split(" ")) {
            token = lexer.nextToken();
            assertEquals(new Token(TokenType.OPERATOR, operator), token);
        }
    }

    @Test
    public void testFields() {
        String text = "firstName lastName jmbag";
        QueryLexer lexer = new QueryLexer(text);
        Token token;
        for (String field: text.split(" ")) {
            token = lexer.nextToken();
            assertEquals(new Token(TokenType.FIELD, field), token);
        }
    }
    @Test
    public void testExample() {
        String text = "firstName=\"Ivan\" AND lastName LIKE \"AA*AA\"";
        QueryLexer queryLexer = new QueryLexer(text);
        assertEquals(new Token(TokenType.FIELD, "firstName"), queryLexer.nextToken());
        assertEquals(new Token(TokenType.OPERATOR, "="), queryLexer.nextToken());
        assertEquals(new Token(TokenType.DOUBLE_QUOTES, "\""), queryLexer.nextToken());
        queryLexer.setState(LexerState.STRING);
        assertEquals(new Token(TokenType.STRING, "Ivan"), queryLexer.nextToken());
        queryLexer.setState(LexerState.BASIC);
        assertEquals(new Token(TokenType.DOUBLE_QUOTES, "\""), queryLexer.nextToken());
        assertEquals(new Token(TokenType.LOGICAL, "AND"), queryLexer.nextToken());
        assertEquals(new Token(TokenType.FIELD, "lastName"), queryLexer.nextToken());
        assertEquals(new Token(TokenType.OPERATOR, "LIKE"), queryLexer.nextToken());
        assertEquals(new Token(TokenType.DOUBLE_QUOTES, "\""), queryLexer.nextToken());
        queryLexer.setState(LexerState.STRING);
        assertEquals(new Token(TokenType.STRING, "AA*AA"), queryLexer.nextToken());
    }
    
}