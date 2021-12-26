package hr.fer.zemris.math;

import java.util.*;

/**
 * Representation of a complex polynomial of format
 * f(x) = z0 * (z-z1) * (z-z2) * ... * (z-zn)
 */
public class ComplexRootedPolynomial {
    /**
     * constant of this polynomial
     */
    private final Complex constant;
    /**
     * underlying list storing roots of this polynomial
     */
    private final List<Complex> roots;

    /**
     * Constructs {@code ComplexRootedPolynomial} instance with passed constant and roots
     * @param constant polynomial constant
     * @param roots polynomial roots
     * @throws NullPointerException if constant or roots are null
     */
    public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
        Objects.requireNonNull(constant);
        Objects.requireNonNull(roots);
        this.constant = constant;
        this.roots = Arrays.asList(roots);
    }
    /**
     * Computes polynomial value at given point z.
     * @param z given point
     * @return polynomial value
     * @throws NullPointerException if z is null
     */
    public Complex apply(Complex z) {
        Objects.requireNonNull(z);
        Complex fValue = constant;
        for (Complex root : roots) {
            fValue = fValue.multiply(z.sub(root));
        }
        return fValue;
    }
    //

    /**
     * Converts this representation to ComplexPolynomial type
     * @return {@code ComplexPolynomial} representation of this rooted complex polynomial
     */
    public ComplexPolynomial toComplexPolynomial() {
        ComplexPolynomial result = new ComplexPolynomial(constant);
        for (Complex root :roots) {
            result = result.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));
        }
        return result;
    }

    /**
     * Returns {@code String} representation of this polynomial.
     * @return {@code String} representation of this polynomial
     */
    @Override
    public String toString() {
        String result = constant.toString();
        for (Complex root : roots) {
            result += " * ( z - " + root.toString() + ")";
        }
        return result;
    }

    /**
     * Computes and returns index of the closest root to the passed complex number.
     * Root can be considered closest if it is inside passed threshold.
     * @param z passed complex number
     * @param threshold threshold
     * @return index
     */
    public int indexOfClosestRootFor(Complex z, double threshold) {
        Objects.requireNonNull(z);
        int index = -1;

        for (int i = 0; i < roots.size(); i++) {
            double distance = z.sub(roots.get(i)).module();
            if (distance < threshold) {
                threshold = distance;
                index = i;
            }
        }
        return index;
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
        ComplexRootedPolynomial that = (ComplexRootedPolynomial) o;
        return constant.equals(that.constant) && roots.equals(that.roots);
    }


    /**
     * Calculates hashcode of this object and returns it.
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(constant, roots);
    }

    /**
     * Returns this polynomials constant.
     * @return constant
     */
    public Complex getConstant() {
        return constant;
    }

    /**
     * Returns this polynomial's roots.
     * @return unmodifiable list of roots
     */
    public List<Complex> getRoots() {
        return Collections.unmodifiableList(roots);
    }
}
