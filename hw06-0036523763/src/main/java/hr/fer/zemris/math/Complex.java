package hr.fer.zemris.math;


import java.util.ArrayList;
import java.util.List;

public class Complex {
    private final double re;
    private final double im;
    private final double module;
    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex ONE_NEG = new Complex(-1, 0);
    public static final Complex IM = new Complex(0, 1);
    public static final Complex IM_NEG = new Complex(0, -1);

    public Complex() {
        re = 0;
        im = 0;
        module = 0;
    }

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
        this.module = Math.sqrt(re * re + im * im);
    }

    // returns module of complex number
    public double module() {
        return module;
    }

    // returns this*c
    public Complex multiply(Complex c) {
        return new Complex(re * c.getReal() - im * c.getImaginary(), im * c.getReal() + re * c.getImaginary());
    }

    // returns this/c
    public Complex divide(Complex c) {
        Double im2 = c.getImaginary();
        Double re2 = c.getReal();
        return new Complex((re * re2 + im * im2) / (re2 * re2 + im2 * im2), (im * re2 - re * im2) / (re2 * re2 + im2 * im2));
    }

    // returns this+c
    public Complex add(Complex c) {
        return new Complex(re + c.getReal(), im + c.getImaginary());
    }

    // returns this-c
    public Complex sub(Complex c) {
        return new Complex(re - c.getReal(), im - c.getImaginary());
    }

    // returns -this
    public Complex negate() {
        return new Complex(-re, -im);
    }

    // returns this^n, n is non-negative integer
    public Complex power(int n) {
        double theta = Math.atan(im/re);
        double moduleN = Math.pow(module, n);
        return new Complex(moduleN * Math.cos(theta * n), moduleN * Math.sin(theta*n));
    }

    // returns n-th root of this, n is positive integer
    public List<Complex> root(int n) {
        List roots = new ArrayList(n-1);
        double moduleN = Math.pow(module, 1/n);
        double theta = Math.atan(im/re);
        for (int k = 0; k < n; k++) {
            roots.add(new Complex(Math.cos((theta + 2 * k * Math.PI)/n), Math.sin((theta + 2 * k * Math.PI)/n)));
        }
        return roots;
    }

    @Override
    public String toString() {
        return String.format("(%.1f %si)",re, (im >= 0 ? "+" : "") + im);
    }

    public double getReal() {
        return re;
    }

    public double getImaginary() {
        return im;
    }
}
