package hr.fer.zemris.math;

import java.util.*;

public class ComplexRootedPolynomial {
    private final Complex constant;
    private final List<Complex> roots;

    public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
        this.constant = constant;
        this.roots = Arrays.asList(roots);
    }

    public Complex apply(Complex z) {
        Complex fValue = constant;
        for (Complex root : roots) {
            fValue = fValue.multiply(z.sub(root));
        }
        return fValue;
    }
    // converts this representation to ComplexPolynomial type
    public ComplexPolynomial toComplexPolynom() {
        ComplexPolynomial result = new ComplexPolynomial(constant);
        for (Complex root :roots) {
            result = result.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));
        }
        return result;
    }

    @Override
    public String toString() {
        String result = constant.toString();
        for (Complex root : roots) {
            result += " * ( z - " + root.toString() + ")";
        }
        return result;
    }

    public int indexOfClosestRootFor(Complex z, double threshold) {
        double squaredDistance = -1;
        int index = -1;

        for (int i = 0; i < roots.size(); i++) {
            if (z.sub(roots.get(i)).module() < threshold) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexRootedPolynomial that = (ComplexRootedPolynomial) o;
        return Objects.equals(constant, that.constant) && Objects.equals(roots, that.roots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(constant, roots);
    }

    public Complex getConstant() {
        return constant;
    }

    public List<Complex> getRoots() {
        return Collections.unmodifiableList(roots);
    }
}
