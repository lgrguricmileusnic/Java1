package hr.fer.zemris.math.utils;

import hr.fer.zemris.math.Complex;

/**
 * Utility class with method for parsing complex numbers of format a+ib.
 */
public class ComplexParser {
    /**
     * Parses complex number {@code String} and returns its {@code Complex} representation.
     * @param text complex number
     * @return {@code Complex} representation of passed {@code String}
     */
    public static Complex parseComplex(String text) {
        if (text.isBlank() || text.isEmpty()) throw new NumberFormatException("Invalid input");
        int i = 0;
        String a, b;
        boolean nextNegative = false;
        char c;

        text = text.replaceAll("\\s", "");
        if (text.startsWith("-")) {
            nextNegative = true;
            text = text.replaceFirst("-", "");
        }

        a = "";
        c = text.charAt(0);
        if (Character.isDigit(c)) {
            a = "";

            while (i < text.length()) {
                c = text.charAt(i);
                if (!Character.isDigit(c) && c != '.') break;
                a += c;
                i++;
            }
            if (nextNegative) {
                a = "-" + a;
                nextNegative = false;
            }
            if (i >= text.length()) { //if while loop ended naturally
                try {
                    return new Complex(Double.parseDouble(a), 0.0);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid format");
                }

            }
            if (c == 'i' && i >= text.length() - 1) {
                try {
                    return new Complex(0.0, Double.parseDouble(a));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid format");
                }
            }
            if (c != '+' && c != '-') throw new NumberFormatException("Invalid input");
            if (c == '-') {
                nextNegative = true;
            }
            i++;
        }
        try {
            c = text.charAt(i);
        } catch (IndexOutOfBoundsException e) {
            throw new NumberFormatException("Invalid input");
        }
        if (c != 'i') {
            throw new NumberFormatException("Invalid input");
        }
        b = "";
        i++;
        while (i < text.length()) {
            c = text.charAt(i);
            if (!Character.isDigit(c) && c != '.') break;
            b += c;
            i++;
        }
        if (b.isEmpty()) {
            b = "1";
        }
        if (a.isEmpty()) {
            a = "0";
        }
        if (nextNegative) {
            b = "-" + b;
        }
        try {
            return new Complex(Double.parseDouble(a), Double.parseDouble(b));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid format");
        }

    }
}
