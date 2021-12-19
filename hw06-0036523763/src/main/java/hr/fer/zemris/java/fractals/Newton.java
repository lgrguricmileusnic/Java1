package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Newton {
    private static final String WELCOME_MESSAGE = "Welcome to Newton-Raphson iteration-based fractal viewer.\n" +
            "Please enter at least two roots, one root per line. Enter 'done' when one.";

    public static void main(String[] args) {
        System.out.println(WELCOME_MESSAGE);
        Scanner sc = new Scanner(System.in);
        int rootNo = 0;
        List<Complex> roots = new ArrayList<>();
        while (true) {
            System.out.print("Root " + rootNo + "> ");
            String line = sc.nextLine();
            if (line.strip().equalsIgnoreCase("done")) break;
            try{
                roots.add(parseComplex(line));
                System.out.println(parseComplex(line));
                rootNo++;
            }catch(NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        if(roots.size() < 2) {
            System.out.println("Not enough roots entered.");
            System.exit(1);
        }

        ComplexRootedPolynomial complexRootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(roots.toArray(new Complex[0])));
        FractalViewer.show(new NewtonProducer(complexRootedPolynomial,0.001, 0.002));
    }

    public static class NewtonProducer implements IFractalProducer {
        private final ComplexRootedPolynomial rootedPolynom;
        private final ComplexPolynomial derived;
        private final ComplexPolynomial polynom;
        private final double convergenceThreshold;
        private final double rootThreshold;

        public NewtonProducer(ComplexRootedPolynomial rootedPolynom, double convergenceTreshold, double rootTreshold) {
            this.rootedPolynom = rootedPolynom;
            this.polynom = rootedPolynom.toComplexPolynom();
            this.derived = polynom.derive();
            this.convergenceThreshold = convergenceTreshold;
            this.rootThreshold = rootTreshold;
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
            System.out.println("Zapocinjem izracun...");
            int m = 16 * 16 * 16;
            int offset = 0;
            short[] data = new short[width * height];
            for(int y = 0; y < height; y++) {
                if(cancel.get()) break;
                for(int x = 0; x < width; x++) {
                    double cre = x / (width-1.0) * (reMax - reMin) + reMin;
                    double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
                    Complex zn = new Complex(cre, cim);
                    Complex znOld;
                    double module;
                    int iter = 0;
                    do {
                        Complex numerator = rootedPolynom.apply(zn);
                        Complex denominator = derived.apply(zn);
                        Complex fraction = numerator.divide(denominator);
                        Complex zn1 = zn.sub(fraction);
                        module = zn1.sub(zn).module();
                        zn = zn1;
                        iter++;
                    }while(module > convergenceThreshold && iter < m);
                    int index = rootedPolynom.indexOfClosestRootFor(zn, rootThreshold);
                    data[offset++] = (short)(index + 1);
                }
            }
            observer.acceptResult(data, (short)(polynom.order()+1), requestNo);
        }
    }

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
