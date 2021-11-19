package hr.fer.oprpp1.hw04.db;

import hr.fer.oprpp1.hw04.db.lexer.*;
import hr.fer.oprpp1.hw04.db.parser.QueryParserException;
import hr.fer.oprpp1.hw04.db.parser.QueryParserState;

import java.text.ParseException;
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
                expressionTokensArray[1] = lexer.nextToken();
                if (lexer.nextToken().getType() != TokenType.DOUBLE_QUOTES)
                    throw new QueryParserException("Value or pattern must be in double quotes");
                lexer.setState(LexerState.STRING);
                if (lexer.nextToken().getType() != TokenType.STRING)
                    throw new QueryParserException("Error during parsing value or pattern");
                lexer.setState(LexerState.BASIC);
                expressionTokensArray[2] = lexer.getToken();
                if (lexer.nextToken().getType() != TokenType.DOUBLE_QUOTES)
                    throw new QueryParserException("Value or pattern must be in double quotes");
            } catch (LexerException e) {
                throw new QueryParserException("Missing rest of expression after: " + lexer.getToken().getValue());
            }
            if (expressionTokensArray[0].getType() != TokenType.FIELD)
                throw new QueryParserException("Invalid expression, expression must specify one of the available fields!");
            valueGetter = FieldValueGetters.getFieldValueGetter((String) expressionTokensArray[0].getValue());

            if (expressionTokensArray[1].getType() != TokenType.OPERATOR)
                throw new QueryParserException("Invalid expression, expected operator, received: " + lexer.getToken().getValue());
            operator = ComparisonOperators.getComparisonOperator((String) expressionTokensArray[1].getValue());

            if (expressionTokensArray[2].getType() != TokenType.STRING)
                throw new QueryParserException("Invalid expression, expected string or pattern, received: " + lexer.getToken().getValue());
            operand = (String) expressionTokensArray[2].getValue();
            this.query.add(new ConditionalExpression(valueGetter, operator, operand));

            if(lexer.nextToken().getType() == TokenType.EOF) {
                break;
            } else if(lexer.getToken().getType() != TokenType.LOGICAL) {
                throw new QueryParserException("Expected AND, recieved: " + (String) lexer.getToken().getValue());
            };
        }

    }

    /**
     * Checks if parsed query is direct (consists only of an "jmbag equals" expression);
     *
     * @return true if direct, false otherwise
     */
    boolean isDirectQuery() {
        return query.size() == 1 && query.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG);
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
}
