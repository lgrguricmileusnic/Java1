package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representation of a complex polynomial of format
 * f(x) = zn*zn + zn-1*zn-1 + ... + z2*z2 + z1*z + z0
 */
public class ComplexPolynomial {
    /**
     * underlying list storing polynomial factors
     */
    private final List<Complex> factors;

    /**
     * Constructs {@code ComplexPolynomial} with passed factors
     * @param factors factors of this polynomial
     * @throws NullPointerException if passed factors are null
     */
    public ComplexPolynomial(Complex... factors) {
        Objects.requireNonNull(factors);
        this.factors = Arrays.asList(factors);

    }

    /**
     * Returns polynomial order
     * e.g. For (7+2i)z^3+2z^2+5z+1 returns 3
     * @return order of this polynomial
     */
    public short order() {
        return (short) (factors.size() - 1);
    }

    /**
     * Gets factors of this polynomial.
     * @return {@code unmodifiableList} with this polynomial's factors
     */
    public List<Complex> getFactors() {
        return Collections.unmodifiableList(factors);
    }

    /**
     * Computes a new polynomial this*p.
     * @param p factor
     * @return product
     * @throws NullPointerException if passed polynomial is null
     */
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        Objects.requireNonNull(p);
        int polynomialOrder1 = order();
        int polynomialOrder2 = p.order();
        List<Complex> otherFactors = p.getFactors();
        Complex[] productFactors = new Complex[polynomialOrder1 + polynomialOrder2 + 1];

        Arrays.fill(productFactors, Complex.ZERO);

        for (int i = 0; i < factors.size(); i++) {
            for (int j = 0; j < p.order() + 1; j++) {
                productFactors[i + j] = productFactors[i + j].add(factors.get(i).multiply(otherFactors.get(j)));
            }
        }

        return new ComplexPolynomial(productFactors);
    }

    /**
     * Computes first derivative of this polynomial.
     * e.g. for (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
     * @return first derivative of this polynomial
     */
    public ComplexPolynomial derive() {
        Complex[] newFactors = new Complex[order()];
        for (int i = 1; i < factors.size(); i++) {
            newFactors[i - 1] = factors.get(i).multiply(new Complex(i, 0));
        }
        return new ComplexPolynomial(newFactors);
    }



    /**
     * Computes polynomial value at given point z.
     * @param z given point
     * @return polynomial value
     * @throws NullPointerException if z is null
     */
    public Complex apply(Complex z) {
        Objects.requireNonNull(z);
        Complex fValue = Complex.ZERO;
        for (int i = 0; i < factors.size(); i++) {
            fValue = fValue.add(z.power(i).multiply(factors.get(i)));
        }
        return fValue;
    }

    /**
     * Returns {@code String} representation of this polynomial.
     * @return {@code String} representation of this polynomial
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = factors.size() - 1; i > 0; i--) {
            result += factors.get(i).toString() + " * z^" + i + "+";
        }
        return result + factors.get(0);
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
        ComplexPolynomial that = (ComplexPolynomial) o;
        return factors.equals(that.factors);
    }

    /**
     * Calculates hashcode of this object and returns it.
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(factors);
    }
}