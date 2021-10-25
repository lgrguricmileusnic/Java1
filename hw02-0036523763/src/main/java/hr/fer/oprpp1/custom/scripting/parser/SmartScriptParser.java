package hr.fer.oprpp1.custom.scripting.parser;


import hr.fer.oprpp.hw02.prob1.LexerException;
import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.LexerState;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.lexer.TokenType;
import hr.fer.oprpp1.custom.scripting.nodes.*;

import java.util.Objects;

/**
 * Simple parser implementation. Parses documents by rules defined in problem 3 of this homework.
 */
public class SmartScriptParser {
    private SmartScriptLexer lexer;
    private ObjectStack stack;
    private Token currentToken;
    private DocumentNode documentNode;

    /**
     * Constructs a {@code SmartScriptParser} instance and initialises stack, lexer and
     * root document node. Prepares and delegates parsing to {@code parse()} method.
     * @param documentBody string containing document which should be parsed
     * @throws SmartScriptParserException if the {@code parse} method encounters a problem during parsing
     * @throws NullPointerException if {@code documentBody} is null
     */
    public SmartScriptParser(String documentBody) {
        Objects.requireNonNull(documentBody);
        lexer = new SmartScriptLexer(documentBody);
        stack = new ObjectStack();
        documentNode = new DocumentNode();
        stack.push(documentNode);
        lexer.setState(LexerState.TEXT);
        try{
            parse();
        } catch (LexerException e){
            throw new SmartScriptParserException(e.getMessage());
        } catch(EmptyStackException e){
            throw new SmartScriptParserException("Document structure invalid: " + e.getMessage());
        }

    }

    /**
     * Gets root document node.
     * @return root {@code DocumentNode}
     */
    public DocumentNode getDocumentNode() {
        return documentNode;
    }

    /**
     * Starts parser and creates document tree structure starting from {@code documentNode}.
     * @throws SmartScriptParserException if a problem is encountered during parsing
     */
    private void parse() {
        try {
            currentToken = lexer.nextToken();
        } catch (LexerException e) {
            throw new SmartScriptParserException(e.getMessage());
        }
        while (!currentToken.equals(new Token(TokenType.EOF, null))) {
            switch (currentToken.getType()) {
                case TEXT -> {
                    ((Node) stack.peek()).addChildNode(new TextNode((String) currentToken.getValue()));
                }
                case SPECIAL -> {
                    String marker = (String) currentToken.getValue();
                    if (marker.equals("{$")) {
                        lexer.setState(LexerState.TAG);
                        try {
                            currentToken = lexer.nextToken();
                        } catch (LexerException e) {
                            throw new SmartScriptParserException(e.getMessage());
                        }
                        if (currentToken.getType().equals(TokenType.NAMETYPEVAR)) {
                            if (((String) currentToken.getValue()).equalsIgnoreCase("for")) {
                                ForLoopNode forLoopNode = parseForLoopNodeTag();
                                ((Node) stack.peek()).addChildNode(forLoopNode);
                                stack.push(forLoopNode);

                            } else if (((String) currentToken.getValue()).equalsIgnoreCase("end")) {
                                if(!lexer.nextToken().equals(new Token(TokenType.SPECIAL, "$}"))) throw new SmartScriptParserException("Invalid END tag!");
                                try {
                                    stack.pop();
                                } catch (EmptyStackException e) {
                                    throw new SmartScriptParserException("Too many END tags");
                                }
                            } else {
                                //implement other tags here
                                throw new SmartScriptParserException("Unknown tag: " + currentToken.getValue());
                            }
                        } else if (currentToken.equals(new Token(TokenType.SPECIAL, "="))) {
                            ((Node) stack.peek()).addChildNode(parseEchoNode());
                        }
                        lexer.setState(LexerState.TEXT);
                    }
                }
            }
            try {
                currentToken = lexer.nextToken();
            } catch (LexerException e) {
                currentToken = new Token(TokenType.EOF, null);
            }
        }
        if(stack.size() > 1) throw new SmartScriptParserException("Invalid document structure");
        try {
            stack.pop();
        } catch (EmptyStackException e) {
            throw new SmartScriptParserException("Too many END tags");
        }

    }

    /**
     * Parses for loop node opening tag, checks number of parameters and their validity.
     * @return {@code ForLoopNode} instance with parsed parameters
     * @throws SmartScriptParserException if for loop paramaters are invalid or there was a problem during parsing
     */
    private ForLoopNode parseForLoopNodeTag() {
        ArrayIndexedCollection forParams = new ArrayIndexedCollection(4);
        if (!lexer.nextToken().getType().equals(TokenType.NAMETYPEVAR))
            throw new SmartScriptParserException("For loop first argument is not a variable");
        forParams.add(new ElementVariable((String) lexer.getToken().getValue()));
        try {
            currentToken = lexer.nextToken();
        } catch (LexerException e) {
            throw new SmartScriptParserException(e.getMessage());
        }
        TokenType type;
        while (forParams.size() < 5 && !currentToken.equals(new Token(TokenType.SPECIAL, "$}"))) {
            type = currentToken.getType();
            if (type.equals(TokenType.DOUBLE) || type.equals(TokenType.INTEGER) || type.equals(TokenType.NAMETYPEVAR)) {
                switch (type) {
                    case DOUBLE -> forParams.add(new ElementConstantDouble((Double) currentToken.getValue()));
                    case INTEGER -> forParams.add(new ElementConstantInteger((Integer) currentToken.getValue()));
                    case NAMETYPEVAR -> forParams.add(new ElementVariable((String) currentToken.getValue()));
                }
            } else if (currentToken.equals(new Token(TokenType.SPECIAL, "\""))) {
                lexer.setState(LexerState.STRING);
                try {
                    currentToken = lexer.nextToken();
                } catch (LexerException e) {
                    throw new SmartScriptParserException(e.getMessage());
                }

                String value = (String) currentToken.getValue();
                // Uncomment if parser has to check if string consists only of numeric characters
//                if (value.contains(".")) {
//                    try {
//                         Double.parseDouble(value);
//                    } catch (NumberFormatException ignored) {
//                        throw new SmartScriptParserException("String as for argument can't contain non-numeric characters");
//                    }
//                }
//                try {
//                    Integer.parseInt(value);
//                } catch (NumberFormatException ignored) {
//                    throw new SmartScriptParserException("String as for argument can't contain non-numeric characters");
//                }

                forParams.add(new ElementString((String) currentToken.getValue()));
                lexer.nextToken(); // skip closing "
                lexer.setState(LexerState.TAG);

            } else {
                throw new SmartScriptParserException("Invalid for loop argument: " + currentToken.getValue());
            }
            try {
                currentToken = lexer.nextToken();
            } catch (LexerException e) {
                throw new SmartScriptParserException(e.getMessage());
            }
            lexer.setState(LexerState.TAG);
        }

        ForLoopNode forLoopNode;
        switch (forParams.size()) {
            case 3 -> forLoopNode = new ForLoopNode((ElementVariable) forParams.get(0), (Element) forParams.get(1), (Element) forParams.get(2), null);
            case 4 -> forLoopNode = new ForLoopNode((ElementVariable) forParams.get(0), (Element) forParams.get(1), (Element) forParams.get(2), (Element) forParams.get(3));
            default -> throw new SmartScriptParserException("For loop expects 3 or 4 arguments");
        }
        return forLoopNode;
    }

    /**
     * Parses echo node, checks number of parameters and their validity.
     * @return {@code EchoNode} instance with validated parameters
     * @throws SmartScriptParserException if there was a problem during parsing
     */
    private EchoNode parseEchoNode() {
        try {
            currentToken = lexer.nextToken();
        } catch (LexerException e) {
            throw new SmartScriptParserException("Echo tag not closed!");
        }
        ArrayIndexedCollection elements = new ArrayIndexedCollection();
        while (!currentToken.equals(new Token(TokenType.SPECIAL, "$}"))) {
            switch (currentToken.getType()) {
                case INTEGER -> elements.add(new ElementConstantInteger((Integer) currentToken.getValue()));
                case DOUBLE -> elements.add(new ElementConstantDouble((Double) currentToken.getValue()));
                case SPECIAL -> {
                    if(currentToken.getValue().equals("\"") && !currentToken.getValue().equals("$}")) {
                        lexer.setState(LexerState.STRING);
                        try {
                            currentToken = lexer.nextToken();
                        } catch (LexerException e) {
                            throw new SmartScriptParserException(e.getMessage());
                        }
                        elements.add(new ElementString((String) currentToken.getValue()));
                        lexer.nextToken(); //skips closing "
                        lexer.setState(LexerState.TAG);
                    } else {
                        throw new SmartScriptParserException("Invalid element in echo tag");
                    }
                }
                case FUNCTION -> elements.add(new ElementFunction((String) currentToken.getValue()));
                case OPERATOR -> elements.add(new ElementOperator((String) currentToken.getValue()));
                case NAMETYPEVAR -> elements.add(new ElementVariable((String) currentToken.getValue()));
            }
            try {
                currentToken = lexer.nextToken();
            } catch (LexerException e) {
                throw new SmartScriptParserException(e.getMessage());
            }
        }
        Element[] elementsArray = new Element[elements.size()];
        Object[] helperArray = elements.toArray();
        for (int i = 0; i < elements.size(); i++) {
            elementsArray[i] = (Element) helperArray[i];
        }
        return new EchoNode(elementsArray);
    }

}



