package hr.fer.oprpp1.hw05.crypto;



public class Util {
    public static byte[] hexToByte(String hex) {
        if(hex.length() % 2 != 0) throw new IllegalArgumentException("Passed string can't be odd-sized");
        byte[] bytes = new byte[hex.length()/2];
        hex = hex.toLowerCase();
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

    public static String byteToHex(byte[] bytes) {
        int c1, c2;
        String hex = "";
        for (byte b : bytes) {
            c1 = ((b & -16) >> 4 ) & 15;
            c2 = (b & 15);
            c1 = hexToAscii(c1);
            c2 = hexToAscii(c2);
            hex = hex + (char)c1 + (char)c2;
        }
        return hex;
    }

    private static int hexValueOf(int c) {
        if((c < 'a' || c > 'f') && !Character.isDigit(c)) throw new IllegalArgumentException("Non hex digit found: " + (char) c);
        if (Character.isDigit(c)) {
            c = c - 48;
        } else {
            c = c - 55;
        }
        return c;
    }

    private static int hexToAscii(int h) {
        if(h < 0 || h > 15) throw new IllegalArgumentException();
        if (h > 9) return h + 87;
        return h + 48;
    }
}
