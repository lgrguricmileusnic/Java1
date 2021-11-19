package hr.fer.oprpp1.hw04.db;

import hr.fer.oprpp1.hw04.db.lexer.LexerException;
import hr.fer.oprpp1.hw04.db.parser.QueryParserException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueryParserTest {
    @Test
    public void testNullInput() {
        assertThrows(NullPointerException.class, () -> new QueryParser(null));
    }

    @Test
    public void testEmptyQuery() {
        assertThrows(QueryParserException.class, () -> new QueryParser("    "));
    }

    @Test
    public void testStringNotClosed() {
        assertThrows(QueryParserException.class, () -> new QueryParser("\"0123456789 "));
    }

    @Test
    public void testNoExpressionAfterAND() {
        assertThrows(QueryParserException.class, () -> new QueryParser("jmbag=\"003\" AND"));
    }

    @Test
    public void testMissingSecondOperand() {
        assertThrows(QueryParserException.class, () -> new QueryParser("jmbag="));
    }

    @Test
    public void testMissingOperator() {
        assertThrows(QueryParserException.class, () -> new QueryParser("jmbag \"003\""));
    }

    @Test
    public void testMissingField() {
        assertThrows(QueryParserException.class, () -> new QueryParser("=\"003\""));
    }

    @Test
    public void testExample1() {
        QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
        assertTrue(qp1.isDirectQuery()); // true
        assertEquals("0123456789", qp1.getQueriedJMBAG()); //
        assertEquals(1, qp1.getQuery().size()); // 1
        QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
        assertFalse(qp2.isDirectQuery()); // false
        assertThrows(IllegalStateException.class, qp2::getQueriedJMBAG); // would throw!
        List<ConditionalExpression> query = qp2.getQuery();
        assertEquals(2, qp2.getQuery().size()); // 2

    }
}