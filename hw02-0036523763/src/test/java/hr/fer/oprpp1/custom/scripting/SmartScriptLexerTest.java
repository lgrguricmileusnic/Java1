package hr.fer.oprpp1.custom.scripting;

import hr.fer.oprpp.hw02.prob1.LexerException;
import hr.fer.oprpp1.custom.scripting.lexer.LexerState;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.lexer.TokenType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SmartScriptLexerTest {

    @Test
    public void testNotNull() {
        SmartScriptLexer lexer = new SmartScriptLexer("");
        assertNotNull(lexer.nextToken());
    }


    @Test
    public void testNullInput() {
        assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
    }


    @Test
    public void testEmptyInput() {
        SmartScriptLexer lexer = new SmartScriptLexer("");
        assertEquals(TokenType.EOF, lexer.nextToken().getType());
    }


    @Test
    public void testGetReturnsLastToken() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        Token token = lexer.nextToken();
        assertEquals(token, lexer.getToken());
        assertEquals(token, lexer.getToken());

    }


    @Test
    public void testExceptionAfterEOF() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        assertEquals(TokenType.EOF, lexer.nextToken().getType());
        assertThrows(LexerException.class, lexer::nextToken);
    }


    @Test
    public void testBlankInput() {
        SmartScriptLexer lexer = new SmartScriptLexer("\r \n \t");
        assertEquals(TokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testInvalidStringTermination() {
        SmartScriptLexer lexer = new SmartScriptLexer("\"String not terminated");
        lexer.setState(LexerState.TAG); //setting state to TAG so lexer can start detecting strings
        lexer.nextToken(); //here the client would get the " token, meaning they should switch the lexer state to string
        lexer.setState(LexerState.STRING);
        assertThrows(LexerException.class, lexer::nextToken);
    }

    @Test
    public void testInvalidEscapeSequenceInString() {
        SmartScriptLexer lexer = new SmartScriptLexer("\"String \\e \"");
        lexer.setState(LexerState.TAG); //setting state to TAG so lexer can start detecting strings
        lexer.nextToken(); //here the client would get the " token, meaning they should switch the lexer state to string
        lexer.setState(LexerState.STRING);
        assertThrows(LexerException.class, lexer::nextToken);
    }

    @Test
    public void testSampleString() {
        SmartScriptLexer lexer = new SmartScriptLexer("\"Joe \\\"Long\\\" Smith\"");
        lexer.setState(LexerState.TAG); //setting state to TAG so lexer can start detecting strings
        lexer.nextToken(); //here the client would get the " token, meaning they should switch the lexer state to string
        lexer.setState(LexerState.STRING);
        assertEquals("Joe \"Long\" Smith", lexer.nextToken().getValue());
    }

    @Test
    public void testIllegalCharacterInTag(){
        SmartScriptLexer lexer = new SmartScriptLexer("{$= $");
        lexer.nextToken();
        lexer.setState(LexerState.TAG);
        lexer.nextToken();
        assertThrows(LexerException.class, lexer::nextToken);
    };

    @Test
    public void testText(){
        SmartScriptLexer lexer = new SmartScriptLexer("word word");
        lexer.nextToken();
        assertEquals(lexer.getToken().getType(), TokenType.TEXT);
        assertEquals(lexer.getToken().getValue(), "word word");
    };

    @Test
    public void testVariable(){
        SmartScriptLexer lexer = new SmartScriptLexer("aa_BB47");
        lexer.setState(LexerState.TAG);
        lexer.nextToken();
        assertEquals(lexer.getToken().getType(), TokenType.NAMETYPEVAR);
        assertEquals(lexer.getToken().getValue(), "aa_BB47");
    };

    @Test
    public void testFunction(){
        SmartScriptLexer lexer = new SmartScriptLexer("@foo");
        lexer.setState(LexerState.TAG);
        lexer.nextToken();
        assertEquals(lexer.getToken().getType(), TokenType.FUNCTION);
        assertEquals(lexer.getToken().getValue(), "foo");
    };

    @Test
    public void testFunctionEmptyName(){
        SmartScriptLexer lexer = new SmartScriptLexer("@");
        lexer.setState(LexerState.TAG);
        assertThrows(LexerException.class, lexer::nextToken);
    };

    @Test
    public void testFunctionInvalidName(){
        SmartScriptLexer lexer = new SmartScriptLexer("@1");
        lexer.setState(LexerState.TAG);
        assertThrows(LexerException.class, lexer::nextToken);
    };

    @Test
    public void testDouble(){
        SmartScriptLexer lexer = new SmartScriptLexer("0.145");
        lexer.setState(LexerState.TAG);
        lexer.nextToken();
        assertEquals(lexer.getToken().getType(), TokenType.DOUBLE);
        assertEquals(lexer.getToken().getValue(), 0.145);
    };
    @Test
    public void testNegativeDouble(){
        SmartScriptLexer lexer = new SmartScriptLexer("-123.5678");
        lexer.setState(LexerState.TAG);
        lexer.nextToken();
        assertEquals(lexer.getToken().getType(), TokenType.DOUBLE);
        assertEquals(lexer.getToken().getValue(), -123.5678);
    };

    @Test
    public void testInteger(){
        SmartScriptLexer lexer = new SmartScriptLexer("123");
        lexer.setState(LexerState.TAG);
        lexer.nextToken();
        assertEquals(lexer.getToken().getType(), TokenType.INTEGER);
        assertEquals(lexer.getToken().getValue(), 123);
    };

    @Test
    public void testNegativeInteger(){
        SmartScriptLexer lexer = new SmartScriptLexer("-123");
        lexer.setState(LexerState.TAG);
        lexer.nextToken();
        assertEquals(lexer.getToken().getType(), TokenType.INTEGER);
        assertEquals(lexer.getToken().getValue(), -123);
    };

    @Test
    public void testOperators() {
        SmartScriptLexer lexer = new SmartScriptLexer("-+*/^");
        lexer.setState(LexerState.TAG);
        checkTokenStream(lexer, new Token[] {
                new Token(TokenType.OPERATOR, "-"),
                new Token(TokenType.OPERATOR, "+"),
                new Token(TokenType.OPERATOR, "*"),
                new Token(TokenType.OPERATOR, "/"),
                new Token(TokenType.OPERATOR, "^")});
    }

    @Test
    public void testBeginTagMarker() {
        SmartScriptLexer lexer = new SmartScriptLexer("aaaa {$");
        lexer.nextToken();
        lexer.nextToken();
        assertEquals(lexer.getToken().getType(), TokenType.SPECIAL);
        assertEquals(lexer.getToken().getValue(), "{$");
    }

    @Test
    public void testEndTagMarker() {
        SmartScriptLexer lexer = new SmartScriptLexer("aaaa $}");
        lexer.setState(LexerState.TAG);
        lexer.nextToken();
        lexer.nextToken();
        assertEquals(lexer.getToken().getType(), TokenType.SPECIAL);
        assertEquals(lexer.getToken().getValue(), "$}");
    }

    private void checkTokenStream(SmartScriptLexer lexer, Token[] correctData) {
        int counter = 0;
        for(Token expected : correctData) {
            Token actual = lexer.nextToken();
            String msg = "Checking token "+counter + ":";
            assertEquals(expected.getType(), actual.getType(), msg);
            assertEquals(expected.getValue(), actual.getValue(), msg);
            counter++;
        }
    }
}
