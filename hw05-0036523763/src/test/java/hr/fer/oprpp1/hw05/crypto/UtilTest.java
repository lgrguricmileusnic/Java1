package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void testHexToByte() {
        assertArrayEquals(new byte[] {1, -82, 34}, Util.hextobyte("01ae22"));
    }

    @Test
    void testByteToHex() {
        assertEquals(Util.bytetohex(new byte[] {1, -82, 34}), "01ae22");
    }

    @Test
    void testNonHexCharacter() {
        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("k0"));
    }

    @Test
    void testNonHexString() {
        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("aee"));
    }

    @Test
    void testEmptyHexString() {
        assertArrayEquals(new byte[]{}, Util.hextobyte(""));
    }
    @Test
    void testEmptyByteArray() {
        assertEquals("", Util.bytetohex(new byte[]{}));
    }
}