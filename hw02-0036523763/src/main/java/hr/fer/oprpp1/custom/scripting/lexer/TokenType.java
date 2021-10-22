package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Token types enumeration made for {@code SmartScriptLexer} tokenization
 */
public enum TokenType {
    EOF,
    TEXT,
    WORD,
    NUMBER,
    SYMBOL,
    MARKER // {$ , $}
}
