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

import static hr.fer.zemris.math.utils.ComplexParser.parseComplex;

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
        private final ComplexRootedPolynomial rootedPolynomial;
        private final ComplexPolynomial derived;
        private final ComplexPolynomial polynomial;
        private final double convergenceThreshold;
        private final double rootThreshold;

        public NewtonProducer(ComplexRootedPolynomial rootedPolynom, double convergenceTreshold, double rootTreshold) {
            this.rootedPolynomial = rootedPolynom;
            this.polynomial = rootedPolynom.toComplexPolynom();
            this.derived = polynomial.derive();
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
                    double module;
                    int iter = 0;
                    do {
                        Complex numerator = rootedPolynomial.apply(zn);
                        Complex denominator = derived.apply(zn);
                        Complex znold = zn;
                        Complex fraction = numerator.divide(denominator);
                        zn = zn.sub(fraction);
                        module = znold.sub(zn).module();
                        iter++;
                    }while(module > convergenceThreshold && iter < m);
                    int index = rootedPolynomial.indexOfClosestRootFor(zn, rootThreshold);
                    data[offset++] = (short)(index + 1);
                }
            }
            observer.acceptResult(data, (short)(polynomial.order()+1), requestNo);
        }
    }
}
