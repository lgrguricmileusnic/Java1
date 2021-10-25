package hr.fer.oprpp.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A command-line application which takes one argument, a path to the document which will be parsed.
 * Parses the document using {@code SmartScriptTester} and prints it "reassembled" to the command-line.
 */
public class SmartScriptTester {
    /**
     * Main function of this command-line application
     * @param args command-line arguments, first argument should be a path to the document which will be parsed
     * @throws IOException if there was a problem with reading the file
     */
    public static void main(String[] args) throws IOException {
        String docBody = Files.readString(Paths.get(args[0]));
        SmartScriptParser parser = null;
        try {
            parser = new SmartScriptParser(docBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        System.out.println(originalDocumentBody);
    }

}
