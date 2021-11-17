package hr.fer.oprpp1.hw04.db;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ComparisonOperatorsTest {

    @Test
    public void testLESSOperator() {
        assertFalse(ComparisonOperators.LESS.satisfied("abc","xyz"));
        assertTrue(ComparisonOperators.LESS.satisfied("xyz", "abc"));
    }
    @Test
    public void testLESS_OR_EQUALSOperator() {
        assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("abc","xyz"));
        assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("xyz", "abc"));
        assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("xyz", "xyz"));

    }

    @Test
    public void testGREATEROperator() {
        assertTrue(ComparisonOperators.GREATER.satisfied("abc","xyz"));
        assertFalse(ComparisonOperators.GREATER.satisfied("xyz", "abc"));
    }
    @Test
    public void testGREATER_OR_EQUALSOperator() {
        assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("abc","xyz"));
        assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("xyz", "abc"));
        assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("xyz", "xyz"));

    }

    @Test
    public void testEQUALSOperator() {
        assertTrue(ComparisonOperators.EQUALS.satisfied("xyz","xyz"));
        assertFalse(ComparisonOperators.EQUALS.satisfied("xyz", "abc"));
    }

    @Test
    public void testNOT_EQUALSOperator() {
        assertTrue(ComparisonOperators.EQUALS.satisfied("xyz","xyz"));
        assertFalse(ComparisonOperators.EQUALS.satisfied("xyz", "abc"));
    }

    @Test
    public void testLIKEOperator() {
        assertTrue(ComparisonOperators.LIKE.satisfied("xyz","xy*z"));
        assertTrue(ComparisonOperators.LIKE.satisfied("xyz","xyz"));
        assertTrue(ComparisonOperators.LIKE.satisfied("xyzaaaa","xyz*"));
        assertFalse(ComparisonOperators.LIKE.satisfied("xy","xyz*"));
        assertTrue(ComparisonOperators.LIKE.satisfied("xyz","*xyz"));
        assertTrue(ComparisonOperators.LIKE.satisfied("aaaaaxyz","*xyz"));
        assertFalse(ComparisonOperators.LIKE.satisfied("AAA","AA*AA"));

    }
}
