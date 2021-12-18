package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComplexPolynomial {
    private final List<Complex> factors;

    // constructor
    public ComplexPolynomial(Complex... factors) {
        this.factors = Arrays.asList(factors);

    }

    // returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
    public short order() {
        return (short) factors.size();
    }

    public List<Complex> getFactors() {
        return Collections.unmodifiableList(factors);
    }

    // computes a new polynomial this*p
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        int polynomialOrder1 = factors.size();
        int polynomialOrder2 = p.order();
        List<Complex> otherFactors = p.getFactors();
        Complex[] productFactors = new Complex[polynomialOrder1 + polynomialOrder2 - 1];


        for (int i = 0; i < polynomialOrder1 + polynomialOrder2 - 1; i++) {
            productFactors[i] = Complex.ZERO;
        }

        for (int i = 0; i < polynomialOrder1; i++) {
            for (int j = 0; j < polynomialOrder2; j++) {
                productFactors[i + j] = productFactors[i + j].add(factors.get(i).multiply(otherFactors.get(j)));
            }
        }

        return new ComplexPolynomial(productFactors);
    }

    // computes first derivative of this polynomial; for example, for
    // (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
    public ComplexPolynomial derive() {
        Complex[] newFactors = new Complex[factors.size() - 1];
        for (int i = 1; i < factors.size(); i++) {
            newFactors[i - 1] = factors.get(i).multiply(new Complex(i, 0));
        }
        return new ComplexPolynomial(newFactors);
    }

    // computes polynomial value at given point z
    public Complex apply(Complex z) {
        Complex fValue = Complex.ZERO;
        for (int i = 0; i < factors.size(); i++) {
            fValue.add(factors.get(0).multiply(z.power(i)));
        }
        return fValue;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = factors.size() - 1; i > 0; i--) {
            result += factors.get(i).toString() + " * z^" + i + "+";
        }
        return result + factors.get(0);
    }
}