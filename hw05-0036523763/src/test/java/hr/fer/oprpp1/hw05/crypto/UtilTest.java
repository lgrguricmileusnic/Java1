package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void testHexToByte() {
        assertArrayEquals(new byte[] {1, -82, 34}, Util.hexToByte("01ae22"));
    }

    @Test
    void testByteToHex() {
        assertEquals(Util.byteToHex(new byte[] {1, -82, 34}), "01ae22");
    }

    @Test
    void testNonHexCharacter() {
        assertThrows(IllegalArgumentException.class, () -> Util.hexToByte("k0"));
    }

    @Test
    void testNonHexString() {
        assertThrows(IllegalArgumentException.class, () -> Util.hexToByte("aee"));
    }

    @Test
    void testEmptyHexString() {
        assertArrayEquals(new byte[]{}, Util.hexToByte(""));
    }
    @Test
    void testEmptyByteArray() {
        assertEquals("", Util.byteToHex(new byte[]{}));
    }
}