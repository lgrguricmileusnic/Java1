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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static hr.fer.zemris.math.utils.ComplexParser.parseComplex;


/**
 * Newton-Raphson iteration-based fractal viewer entry point.
 * Instantiates {@code FragmentViewer} window and computes image using parallelization .
 */
public class NewtonParallel {
    /**
     * Welcome message
     */
    private static final String WELCOME_MESSAGE = "Welcome to Newton-Raphson iteration-based fractal viewer.\n" +
            "Please enter at least two roots, one root per line. Enter 'done' when one.";

    /**
     * convergence threshold
     */
    static double convergenceThreshold;
    /**
     * root threshold
     */
    static double rootThreshold;
    /**
     * rooted polynomial
     */
    static ComplexRootedPolynomial rootedPolynomial;
    /**
     * derived polynomial
     */
    static ComplexPolynomial derived;
    /**
     * polynomial
     */
    static ComplexPolynomial polynomial;
    /**
     * number of workers
     */
    static int workers;
    /**
     * number of tracks
     */
    static int tracks;

    /**
     * Main function of this application.
     * Parses user input into complex polynomial and starts FractalViewer.
     * @param args no arguments
     */
    public static void main(String[] args) {
        int i = 0;
        workers = 0;
        tracks = 0;
        while(i < args.length) {
            String option = args[i];
            if(option.startsWith("-w")){
                if (workers != 0){
                    System.out.println("Workers option specified more than once.");
                    System.exit(1);
                }
                try {
                    workers = Integer.parseInt(args[++i]);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Invalid options");
                    System.exit(1);
                }

            } else if (option.startsWith("--workers=")) {
                if (workers != 0){
                    System.out.println("Workers option specified more than once.");
                    System.exit(1);
                }
                try {
                    workers = Integer.parseInt(option.substring(option.indexOf('=') + 1));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid options");
                    System.exit(1);
                }
            } else if (option.startsWith("-t")) {
                if (tracks != 0){
                    System.out.println("Tracks option specified more than once.");
                    System.exit(1);
                }
                try {
                    tracks = Integer.parseInt(args[++i]);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    System.out.println("Invalid options");
                    System.exit(1);
                }
                if (tracks < 1) {
                    System.out.println("Number of tracks must be greater than zero.");
                    System.exit(1);
                }
            } else if (option.startsWith("--tracks=")) {
                if (tracks != 0){
                    System.out.println("Tracks option specified more than once.");
                    System.exit(1);
                }
                try {
                    tracks = Integer.parseInt(option.substring(option.indexOf('=') + 1));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid options");
                    System.exit(1);
                }
            } else {
                System.out.println("Unknown option: " + option);
                System.exit(1);
            }
            i++;
        }
        if(workers == 0) workers = Runtime.getRuntime().availableProcessors();
        if(tracks ==0) tracks = Runtime.getRuntime().availableProcessors() * 4;
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
                rootNo++;
            }catch(NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        if(roots.size() < 2) {
            System.out.println("Not enough roots entered.");
            System.exit(1);
        }
        convergenceThreshold = 0.001;
        rootThreshold = 0.002;
        rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(roots.toArray(new Complex[0])));
        polynomial = rootedPolynomial.toComplexPolynomial();
        derived = polynomial.derive();
        FractalViewer.show(new NewtonProducer());
    }

    /**
     * Runnable which computes one track.
     */
    public static class PosaoIzracuna implements Runnable {
        /**
         * minimum real value
         */
        double reMin;
        /**
         * maximum real value
         */
        double reMax;
        /**
         * minimum imaginary value
         */
        double imMin;
        /**
         * maximum imaginary value
         */
        double imMax;
        /**
         * window width
         */
        int width;
        /**
         * window height
         */
        int height;
        /**
         * track start
         */
        int yMin;
        /**
         * track end
         */
        int yMax;
        /**
         * number of iterations
         */
        int m;
        /**
         * image data array
         */
        short[] data;
        /**
         * cancel flag
         */
        AtomicBoolean cancel;


        public static PosaoIzracuna NO_JOB = new PosaoIzracuna();

        /**
         * default constructor
         */
        private PosaoIzracuna() {
        }

        /**
         * Constructs {@code PosaoIzracuna} instance with passed parameters.
         * @param reMin minimum real part value
         * @param reMax maximum real part value
         * @param imMin minimum imaginary part value
         * @param imMax maximum imaginary part value
         * @param width window width
         * @param height window height
         * @param yMin track start
         * @param yMax track end
         * @param m number of tracks
         * @param data reference to array for storing image data
         * @param cancel cancel flag
         */
        public PosaoIzracuna(double reMin, double reMax, double imMin,
                             double imMax, int width, int height, int yMin, int yMax,
                             int m, short[] data, AtomicBoolean cancel) {
            super();
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.m = m;
            this.data = data;
            this.cancel = cancel;
        }

        /**
         * Runs track computation.
         */
        @Override
        public void run() {
            int offset = yMin * width;
            for(int y = yMin; y <= yMax && !cancel.get(); ++y) {
                for(int x = 0; x < width; ++x) {
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
                    } while(module > convergenceThreshold && iter < m);
                    int index = rootedPolynomial.indexOfClosestRootFor(zn, rootThreshold);
                    data[offset++] = (short)(index + 1);
                }
            }

        }
    }

    /**
     * IFractalProducer implementation.
     * Computes Newton-Rhapson fractal image and passes data to observer
     */
    public static class NewtonProducer implements IFractalProducer {

        /**
         * Produces fractal image data using multiple threads and passes it to observer.
         * @param reMin minimum real part value
         * @param reMax maximum real part value
         * @param imMin minimum imaginary part value
         * @param imMax maximum imaginary part value
         * @param width window width
         * @param height window height
         * @param requestNo request number
         * @param observer observer
         * @param cancel cancel flag
         */
        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
            int m = 16 * 16 * 16;
            short[] data = new short[width * height];
            final int brojTraka;
            brojTraka = Math.min(tracks, height);


            int brojYPoTraci = height / brojTraka;

            final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();
            System.out.println("Threads: " + workers);
            System.out.println("Jobs: " + brojTraka);
            Thread[] radnici = new Thread[workers];
            for (int i = 0; i < radnici.length; i++) {
                radnici[i] = new Thread(() -> {
                    while (true) {
                        PosaoIzracuna p = null;
                        try {
                            p = queue.take();
                            if (p == PosaoIzracuna.NO_JOB) break;
                        } catch (InterruptedException e) {
                            continue;
                        }
                        p.run();
                    }
                });
            }
            for (int i = 0; i < radnici.length; i++) {
                radnici[i].start();
            }

            for (int i = 0; i < brojTraka; i++) {
                int yMin = i * brojYPoTraci;
                int yMax = (i + 1) * brojYPoTraci - 1;
                if (i == brojTraka - 1) {
                    yMax = height - 1;
                }
                PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
                while (true) {
                    try {
                        queue.put(posao);
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }
            for (int i = 0; i < radnici.length; i++) {
                while (true) {
                    try {
                        queue.put(PosaoIzracuna.NO_JOB);
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            for (int i = 0; i < radnici.length; i++) {
                while (true) {
                    try {
                        radnici[i].join();
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);

        }
    }
}
