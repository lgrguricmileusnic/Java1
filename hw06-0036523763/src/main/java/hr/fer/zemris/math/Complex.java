package hr.fer.zemris.math;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Complex number representation
 */
public class Complex {
    /**
     * real part of complex number
     */
    private final double re;
    /**
     * imaginary part of complex number
     */
    private final double im;

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex ONE_NEG = new Complex(-1, 0);
    public static final Complex IM = new Complex(0, 1);
    public static final Complex IM_NEG = new Complex(0, -1);

    public Complex() {
        re = 0;
        im = 0;
    }

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    // returns module of complex number
    public double module() {
        return Math.sqrt(re * re + im * im);
    }

    // returns this*c
    public Complex multiply(Complex c) {
        double im2 = c.getImaginary();
        double re2 = c.getReal();
        return new Complex(re * re2 - im * im2, im * re2 + re * im2);
    }

    // returns this/c
    public Complex divide(Complex c) {
        double im2 = c.getImaginary();
        double re2 = c.getReal();
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
        if(n < 0) throw new IllegalArgumentException("n must be a non-negative integer");
        double theta = Math.atan2(im, re);
        double moduleN = Math.pow(module(), n);
        return new Complex(moduleN * Math.cos(theta * n), moduleN * Math.sin(theta * n));
    }

    // returns n-th root of this, n is positive integer
    public List<Complex> root(int n) {
        if(n < 0) throw new IllegalArgumentException("n must be a non-negative integer");
        List<Complex> roots = new ArrayList<>();
        double moduleN = Math.pow(module(), 1.0 / n);
        double theta = Math.atan(im / re);
        for (int k = 0; k < n; k++) {
            roots.add(new Complex(moduleN * Math.cos((theta + 2 * k * Math.PI) / n), moduleN * Math.sin((theta + 2 * k * Math.PI) / n)));
        }
        return roots;
    }

    @Override
    public String toString() {
        return String.format("(%.1f %si)", re, ((Double.compare(im, 0.0)) >= 0 ? "+" : "") + im);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complex complex = (Complex) o;
        return Double.compare(complex.re, re) == 0 && Double.compare(complex.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }

    public double getReal() {
        return re;
    }

    public double getImaginary() {
        return im;
    }
}
