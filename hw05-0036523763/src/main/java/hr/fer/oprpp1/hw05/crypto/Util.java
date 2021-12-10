package hr.fer.oprpp1.hw05.crypto;


/**
 * Class with utility methods for converting hex strings to byte arrays and byte arrays to hex strings.
 */
public class Util {
    /**
     * Parses passed hex string to a {@code byte[]} and returns it.
     * @param hex hex string which will be converted
     * @return array of bytes parsed from passed hex string
     */
    public static byte[] hextobyte(String hex) {
        if(hex.length() % 2 != 0) throw new IllegalArgumentException("Passed string can't be odd-sized");
        byte[] bytes = new byte[hex.length()/2];
        hex = hex.toUpperCase();
        int c1, c2;
        byte b;
        for (int i = 0; i < hex.length(); i = i + 2) {
            c1 = hex.charAt(i);
            c2 = hex.charAt(i + 1);
            c1 = hexValueOf(c1);
            c2 = hexValueOf(c2);

            bytes[i/2] = (byte) ((c1 << 4) | c2);
        }
        return bytes;
    }

    /**
     * Parses passed byte array into its hex string representation
     * @param bytes byte array
     * @return hex string representation of byte array
     */
    public static String bytetohex(byte[] bytes) {
        int c1, c2;
        String hex = "";
        for (byte b : bytes) {
            c1 = ((b & -16) >> 4 ) & 15;
            c2 = (b & 15);
            c1 = hexToASCII(c1);
            c2 = hexToASCII(c2);
            hex = hex + (char)c1 + (char)c2;
        }
        return hex;
    }

    /**
     * Returns decimal value of the hexadecimal digit whose ASCII code was passed.
     * @param c ASCII code of desired hexadecimal digit
     * @return decimal value
     */
    private static int hexValueOf(int c) {
        if((c < 'A' || c > 'F') && !Character.isDigit(c)) throw new IllegalArgumentException("Non hex digit found: " + (char) c);
        if (Character.isDigit(c)) {
            c = c - 48;
        } else {
            c = c - 55;
        }
        return c;
    }

    /**
     * Returns ASCII code of (lowercase if letter) hex digit whose decimal value was passed
     * @param h decimal value of desired hex digit
     * @return ASCII code of desired hex digit
     */
    private static int hexToASCII(int h) {
        if(h < 0 || h > 15) throw new IllegalArgumentException();
        if (h > 9) return h + 87;
        return h + 48;
    }
}
