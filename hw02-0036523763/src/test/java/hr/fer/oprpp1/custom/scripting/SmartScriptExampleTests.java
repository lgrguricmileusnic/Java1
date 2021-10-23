package hr.fer.oprpp1.custom.scripting;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SmartScriptExampleTests {
    @Test
    public void testExtra1() {
        String text = readExample(1);
        SmartScriptParser parser = new SmartScriptParser(text);
        assertInstanceOf(TextNode.class, parser.getDocumentNode().getChild(0));
        assertEquals(1, parser.getDocumentNode().numberOfChildren());
    }

    @Test
    public void testExtra2() {
        String text = readExample(2);
        SmartScriptParser parser = new SmartScriptParser(text);
        assertInstanceOf(TextNode.class, parser.getDocumentNode().getChild(0));
        assertEquals(1, parser.getDocumentNode().numberOfChildren());
    }

    @Test
    public void testExtra3() {
        String text = readExample(3);
        SmartScriptParser parser = new SmartScriptParser(text);
        assertInstanceOf(TextNode.class, parser.getDocumentNode().getChild(0));
        assertEquals(1, parser.getDocumentNode().numberOfChildren());
    }

    @Test
    public void testExtra4() {
        String text = readExample(4);
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));


    }

    @Test
    public void testExtra5() {
        String text = readExample(5);
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));

    }

    @Test
    public void testExtra6() {
        String text = readExample(6);
        SmartScriptParser parser = new SmartScriptParser(text);
        DocumentNode documentNode = parser.getDocumentNode();
        assertEquals(2, documentNode.numberOfChildren());
        assertInstanceOf(TextNode.class, documentNode.getChild(0));
        assertInstanceOf(EchoNode.class, documentNode.getChild(1));

    }

    @Test
    public void testExtra7() {
        String text = readExample(7);
        SmartScriptParser parser = new SmartScriptParser(text);
        DocumentNode documentNode = parser.getDocumentNode();
        assertEquals(2, documentNode.numberOfChildren());
        assertInstanceOf(TextNode.class, documentNode.getChild(0));
        assertInstanceOf(EchoNode.class, documentNode.getChild(1));
    }

    @Test
    public void testExtra8() {
        String text = readExample(8);
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testExtra9() {
        String text = readExample(9);
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testDocument1(){
        String docBody = readExampleFromResources("document1.txt");
        SmartScriptParser parser = new SmartScriptParser(docBody);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        assertEquals(document,document2);

    }

    private String readExample(int n) {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer" + n + ".txt")) {
            if (is == null) throw new RuntimeException("Datoteka extra/primjer" + n + ".txt je nedostupna.");
            byte[] data = is.readAllBytes();
            String text = new String(data, StandardCharsets.UTF_8);
            return text;
        } catch (IOException ex) {
            throw new RuntimeException("Greška pri čitanju datoteke.", ex);
        }
    }

    private String readExampleFromResources(String filename) {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
            if (is == null) throw new RuntimeException("Datoteka extra/" + filename + " je nedostupna.");
            byte[] data = is.readAllBytes();
            String text = new String(data, StandardCharsets.UTF_8);
            return text;
        } catch (IOException ex) {
            throw new RuntimeException("Greška pri čitanju datoteke.", ex);
        }
    }

}
