package hr.fer.oprpp1.hw04.db;

import hr.fer.oprpp1.hw04.db.lexer.*;
import hr.fer.oprpp1.hw04.db.parser.QueryParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Simple query parser.
 */
public class QueryParser {
    /**
     * Query lexer
     */
    private QueryLexer lexer;

    /**
     * Query parsed into a list of conditional expressions.
     */
    private List<ConditionalExpression> query;

    /**
     * Constructs {@code QueryParser} instance and begins parsing passed query.
     *
     * @param query query to be parsed
     * @throws NullPointerException if passed query is null
     */
    public QueryParser(String query) {
        Objects.requireNonNull(query);
        lexer = new QueryLexer(query);
        lexer.setState(LexerState.BASIC);
        this.query = new ArrayList<ConditionalExpression>();
        parse();
    }

    /**
     * Parses passed query
     *
     * @throws QueryParserException if there is a problem with query syntax or another error occured during parsing.
     */
    public void parse() {
        Token expressionTokensArray[] = new Token[3];
        IFieldValueGetter valueGetter;
        IComparisonOperator operator;
        String operand;
        while (true) {
            try {
                expressionTokensArray[0] = lexer.nextToken();
                if (expressionTokensArray[0].getType() != TokenType.FIELD)
                    throw new QueryParserException("Invalid expression, expression must specify one of the available fields!");

                expressionTokensArray[1] = lexer.nextToken();
                if (expressionTokensArray[1].getType() != TokenType.OPERATOR)
                    throw new QueryParserException("Invalid expression, expected operator, received: " + lexer.getToken().getValue());

                expressionTokensArray[2] = parseStringLiteral();
            } catch (LexerException e) {
                if (lexer.getToken() != null)
                    throw new QueryParserException("Invalid expression");
                throw new QueryParserException(e.getMessage());
            }

            valueGetter = FieldValueGetters.getFieldValueGetter((String) expressionTokensArray[0].getValue());

            operator = ComparisonOperators.getComparisonOperator((String) expressionTokensArray[1].getValue());

            operand = (String) expressionTokensArray[2].getValue();
            this.query.add(new ConditionalExpression(valueGetter, operator, operand));

            try {
                if (lexer.nextToken().getType() == TokenType.EOF) break;
            } catch (LexerException e) {
                throw new QueryParserException("Expected AND, received: " + lexer.getToken().getValue());
            }
        }
    }

    /**
     * Checks if parsed query is direct (consists only of an "jmbag equals" expression);
     *
     * @return true if direct, false otherwise
     */
    boolean isDirectQuery() {
        return query.size() == 1 && query.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG) &&
                query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS);
    }

    /**
     * Gets queried jmbag if parsed query is direct, throws otherwise.
     *
     * @return queried jmbag
     * @throws IllegalStateException if parsed query is not direct
     */
    String getQueriedJMBAG() {
        if (!isDirectQuery()) throw new IllegalStateException("Parsed query is not direct");
        return query.get(0).getOperand2();
    }

    /**
     * Gets query parsed into a list of conditional expressions.
     *
     * @return query parsed into a list of conditional expressions
     */
    List<ConditionalExpression> getQuery() {
        return query;
    }

    /**
     * Parses string literal and returns its token representation.
     */
    private Token parseStringLiteral() {
        Token literal;
        if (lexer.nextToken().getType() != TokenType.DOUBLE_QUOTES)
            throw new QueryParserException("Value or pattern must be in double quotes");
        lexer.setState(LexerState.STRING);
        if (lexer.nextToken().getType() != TokenType.STRING)
            throw new QueryParserException("Error during parsing value or pattern");
        lexer.setState(LexerState.BASIC);
        literal = lexer.getToken();
        if (lexer.nextToken().getType() != TokenType.DOUBLE_QUOTES)
            throw new QueryParserException("Value or pattern must be in double quotes");
        return literal;
    }
}
