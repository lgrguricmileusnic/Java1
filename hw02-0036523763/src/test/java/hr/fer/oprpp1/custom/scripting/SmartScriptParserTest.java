package hr.fer.oprpp1.custom.scripting;

import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class SmartScriptParserTest {

    @Test
    public void testNullInput() {
        assertThrows(NullPointerException.class, () -> new SmartScriptParser(null));
    }
    @Test
    public void testForOpeningTag4Params() {
        String text = "{$ foR var 1 \"stop\" step $} {$eNd$}";
        SmartScriptParser parser = new SmartScriptParser(text);
        DocumentNode documentNode = parser.getDocumentNode();
        assertInstanceOf(ForLoopNode.class, documentNode.getChild(0));
        assertEquals(1, documentNode.numberOfChildren());
        assertEquals("{$ FOR var 1 \"stop\" step $}{$END$}", documentNode.getChild(0).toString());
    }

    @Test
    public void testForOpeningTag3Params() {
        String text = "{$ foR var 1 \"stop\" $} {$eNd$}";
        SmartScriptParser parser = new SmartScriptParser(text);
        DocumentNode documentNode = parser.getDocumentNode();
        assertInstanceOf(ForLoopNode.class, documentNode.getChild(0));
        assertEquals(1, documentNode.numberOfChildren());
        assertEquals("{$ FOR var 1 \"stop\" $}{$END$}", documentNode.getChild(0).toString());

    }

    @Test
    public void testForNoClosingTag() {
        String text = "{$ foR var 1 \"stop\" $}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testForNoClosingBracket() {
        String text = "{$ foR var 1 \"stop\" ";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testEchoNoClosingBracket() {
        String text = "{$= foR var 1 \"stop\" ";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testTooManyEndTags() {
        String text = "{$ foR var 1 \"stop\" $} {$END$} {$END$}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testDocumentTextExample() {
        String text = "Example \\{$=1$}. Now actually write one {$=1$}";
        SmartScriptParser parser = new SmartScriptParser(text);
        DocumentNode documentNode = parser.getDocumentNode();
        assertEquals(2, documentNode.numberOfChildren());
        assertInstanceOf(TextNode.class, documentNode.getChild(0));
        assertInstanceOf(EchoNode.class, documentNode.getChild(1));
        assertEquals(1, ((EchoNode)documentNode.getChild(1)).getElements().length);
    }

    @Test
    public void testForParamsExample1() {
        String text = "{$ FOR i-1.35bbb\"1\" $}{$END$}";
        SmartScriptParser parser = new SmartScriptParser(text);
        DocumentNode documentNode = parser.getDocumentNode();
        assertInstanceOf(ForLoopNode.class, documentNode.getChild(0));
        assertEquals(1, documentNode.numberOfChildren());

        ForLoopNode forLoopNode = (ForLoopNode) documentNode.getChild(0);
        assertEquals(-1.35, ((ElementConstantDouble) forLoopNode.getStartExpression()).getValue());
        assertEquals("bbb", forLoopNode.getEndExpression().asText());
        assertEquals("\"1\"", forLoopNode.getStepExpression().asText());
    }

    @Test
    public void testForParamFunction() {
        String text = "{$ FOR year @sin 10 $}{$END$}";
        assertThrows(SmartScriptParserException.class,() -> new SmartScriptParser(text));
    }

    @Test
    public void testTooFewForParams() {
        String text = "{$ FOR a $}{$END$}";
        assertThrows(SmartScriptParserException.class,() -> new SmartScriptParser(text));
    }


    @Test
    public void testTooManyForParams() {
        String text = "{$ FOR a 1 2 3 4 5 6 $}{$END$}";
        assertThrows(SmartScriptParserException.class,() -> new SmartScriptParser(text));
    }

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

    @Test
    public void testDocument2(){
        String docBody = readExampleFromResources("document2.txt");
        SmartScriptParser parser = new SmartScriptParser(docBody);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        assertEquals(document,document2);

    }

    @Test
    public void testDocument3(){
        String docBody = readExampleFromResources("document3.txt");
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testDocument4(){
        String docBody = readExampleFromResources("document4.txt");
        SmartScriptParser parser = new SmartScriptParser(docBody);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        assertEquals(document,document2);
    }

    @Test
    public void testDocument5(){
        String docBody = readExampleFromResources("document5.txt");
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testDocument6(){
        String docBody = readExampleFromResources("document6.txt");
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
            if (is == null) throw new RuntimeException("Datoteka " + filename + " je nedostupna.");
            byte[] data = is.readAllBytes();
            String text = new String(data, StandardCharsets.UTF_8);
            return text;
        } catch (IOException ex) {
            throw new RuntimeException("Greška pri čitanju datoteke.", ex);
        }
    }

}
