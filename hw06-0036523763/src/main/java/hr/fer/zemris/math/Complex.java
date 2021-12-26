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

    /**
     * Representation of 0 + 0i
     */
    public static final Complex ZERO = new Complex(0, 0);
    /**
     * Representation of 1 + 0i
     */
    public static final Complex ONE = new Complex(1, 0);
    /**
     * Representation of -1 + 0i
     */
    public static final Complex ONE_NEG = new Complex(-1, 0);
    /**
     * Representation of 0 + i
     */
    public static final Complex IM = new Complex(0, 1);
    /**
     * Representation of 0 -i
     */
    public static final Complex IM_NEG = new Complex(0, -1);

    /**
     * Constructs {@code Complex} instance, instantiating real and imaginary parts to zero.
     */
    public Complex() {
        re = 0;
        im = 0;
    }

    /**
     * Constructs {@code Complex} instance, instantiating real and imaginary parts to passed values;
     * @param re real part of complex number
     * @param im imaginary part of complex number
     */
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Calculates and returns module of this complex number
     * @return module of complex number
     */
    public double module() {
        return Math.sqrt(re * re + im * im);
    }

    /**
     * Multiplies this complex number with passed complex number and returns the product.
     * @param c factor
     * @return product of multiplication
     */
    public Complex multiply(Complex c) {
        Objects.requireNonNull(c);
        double im2 = c.getImaginary();
        double re2 = c.getReal();
        return new Complex(re * re2 - im * im2, im * re2 + re * im2);
    }

    /**
     * Divides this complex number with passed complex number and returns the quotient.
     * @param c divisor
     * @return result
     */
    public Complex divide(Complex c) {
        Objects.requireNonNull(c);
        double im2 = c.getImaginary();
        double re2 = c.getReal();
        return new Complex((re * re2 + im * im2) / (re2 * re2 + im2 * im2), (im * re2 - re * im2) / (re2 * re2 + im2 * im2));
    }

    /**
     * Adds this and passed complex number and returns the sum.
     * @param c summand
     * @return sum
     */
    public Complex add(Complex c) {
        Objects.requireNonNull(c);
        return new Complex(re + c.getReal(), im + c.getImaginary());
    }

    /**
     * Subtracts passed complex number from this complex number and returns the difference.
     * @param c subtrahend
     * @return difference
     */
    public Complex sub(Complex c) {
        Objects.requireNonNull(c);
        return new Complex(re - c.getReal(), im - c.getImaginary());
    }

    /**
     * Returns this complex number, negated.
     * @return negated instance of this complex number.
     */
    public Complex negate() {
        return new Complex(-re, -im);
    }

    /**
     * Calculates this complex number to the passed power and returns the result.
     * @param n power
     * @return result
     */
    public Complex power(int n) {
        if(n < 0) throw new IllegalArgumentException("n must be a non-negative integer");
        double theta = Math.atan2(im, re);
        double moduleN = Math.pow(module(), n);
        return new Complex(moduleN * Math.cos(theta * n), moduleN * Math.sin(theta * n));
    }

    /**
     * Calculates n-1 roots of this complex number and returns them in a {@code List}
     * @param n root
     * @return roots
     */
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

    /**
     * Returns {@code String} representation of this number.
     * @return {@code string} representation of this number.
     */
    @Override
    public String toString() {
        return String.format("(%.2f %s %.2fi)", re, ((Double.compare(im, 0.0)) >= 0 ? "+" : ""), im);
    }

    /**
     * Checks if this and the passed object are equal
     * @param o object whose equality is being tested
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complex complex = (Complex) o;
        return Double.compare(complex.re, re) == 0 && Double.compare(complex.im, im) == 0;
    }

    /**
     * Calculates hashcode of this object and returns it.
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }

    /**
     * Gets real part of this complex number
     * @return real part of this complex number
     */
    public double getReal() {
        return re;
    }
    /**
     * Gets imaginary part of this complex number
     * @return imaginary part of this complex number
     */
    public double getImaginary() {
        return im;
    }
}
