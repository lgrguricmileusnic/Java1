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

/**
 * Simple parser implementation. Parses documents by rules defined in problem 3 of this homework.
 */
public class SmartScriptParser {
    private SmartScriptLexer lexer;
    private ObjectStack stack;
    private Token currentToken;
    private DocumentNode documentNode;

    public SmartScriptParser(String documentBody) {
        lexer = new SmartScriptLexer(documentBody);
        stack = new ObjectStack();
        documentNode = new DocumentNode();
        stack.push(documentNode);
        lexer.setState(LexerState.TEXT);
        parse();
    }

    public DocumentNode getDocumentNode() {
        return documentNode;
    }

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
                        if (currentToken.getType().equals(TokenType.VARIABLE)) {
                            if (((String) currentToken.getValue()).equalsIgnoreCase("for")) {
                                ForLoopNode forLoopNode = parseForLoopNode();
                                ((Node) stack.peek()).addChildNode(forLoopNode);
                                stack.push(forLoopNode);

                            } else if (((String) currentToken.getValue()).equalsIgnoreCase("end")) {
                                if(!lexer.nextToken().equals(new Token(TokenType.SPECIAL, "$}"))) throw new SmartScriptParserException("Invalid END tag!");
                                stack.pop();
                            } else{
//                                String tagContent = "";
//                                tagContent += marker;
//                                while(!currentToken.equals(new Token(TokenType.SPECIAL, "$}"))) {
//                                    if(currentToken.)
//                                    if(currentToken.getValue() instanceof String) {
//                                        tagContent += currentToken.getValue();
//                                    } else {
//                                        tagContent += currentToken.getValue().toString();
//                                    }
//                                    tagContent += " ";
//                                    currentToken = lexer.nextToken();
//                                }
//                                tagContent += currentToken.getValue();
//                                ((Node) stack.peek()).addChildNode(new TextNode(tagContent));
                            }
                        } else if (currentToken.equals(new Token(TokenType.SPECIAL, "="))) {
                            try {
                                currentToken = lexer.nextToken();
                            } catch (LexerException e) {
                                throw new SmartScriptParserException(e.getMessage());
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
                                    case VARIABLE -> elements.add(new ElementVariable((String) currentToken.getValue()));
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
                            ((Node) stack.peek()).addChildNode(new EchoNode(elementsArray));
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
        if(stack.size() != 1) throw new SmartScriptParserException("Invalid document structure");
        try {
            stack.pop();
        } catch (EmptyStackException e) {
            throw new SmartScriptParserException("Too many END tags");
        }

    }

    private ForLoopNode parseForLoopNode() {
        ArrayIndexedCollection forParams = new ArrayIndexedCollection(4);
        if (!lexer.nextToken().getType().equals(TokenType.VARIABLE))
            throw new LexerException("For loop first argument is not a variable");
        forParams.add(new ElementVariable((String) lexer.getToken().getValue()));
        try {
            currentToken = lexer.nextToken();
        } catch (LexerException e) {
            throw new SmartScriptParserException(e.getMessage());
        }
        TokenType type;
        while (forParams.size() < 5 && !currentToken.equals(new Token(TokenType.SPECIAL, "$}"))) {
            type = currentToken.getType();
            if (type.equals(TokenType.DOUBLE) || type.equals(TokenType.INTEGER) || type.equals(TokenType.FUNCTION) || type.equals(TokenType.VARIABLE)) {
                switch (type) {
                    case DOUBLE -> forParams.add(new ElementConstantDouble((Double) currentToken.getValue()));
                    case INTEGER -> forParams.add(new ElementConstantInteger((Integer) currentToken.getValue()));
                    case FUNCTION -> forParams.add(new ElementFunction((String) currentToken.getValue()));
                    case VARIABLE -> forParams.add(new ElementVariable((String) currentToken.getValue()));
                }
            } else if (currentToken.equals(new Token(TokenType.SPECIAL, "\""))) {
                lexer.setState(LexerState.STRING);
                try {
                    currentToken = lexer.nextToken();
                } catch (LexerException e) {
                    throw new SmartScriptParserException(e.getMessage());
                }
                String value = (String) currentToken.getValue();
//                switch (currentToken.getType()) {
//                    case DOUBLE -> forParams.add(new ElementConstantDouble((Double) currentToken.getValue()));
//                    case INTEGER -> forParams.add(new ElementConstantInteger((Integer) currentToken.getValue()));
//                    default -> throw new SmartScriptParserException("String as for argument can't contain non-numeric characters");
//                }

                if (value.contains(".")) {
                    try {
                        forParams.add(new ElementConstantDouble((Double) currentToken.getValue()));
                    } catch (NumberFormatException ignored) {
                        throw new SmartScriptParserException("String as for argument can't contain non-numeric characters");
                    }
                }
                try {
                    forParams.add(new ElementConstantInteger((Integer) currentToken.getValue()));
                } catch (NumberFormatException ignored) {
                    throw new SmartScriptParserException("String as for argument can't contain non-numeric characters");
                }
                //forParams.add(new ElementString((String) currentToken.getValue()));
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


}



