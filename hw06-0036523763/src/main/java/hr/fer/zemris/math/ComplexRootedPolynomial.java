package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ComplexRootedPolynomial {
    private final Complex constant;
    private final List<Complex> roots;

    public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
        this.constant = constant;
        this.roots = Arrays.asList(roots);
    }

    public Complex apply(Complex z) {
        Complex fValue = constant;
        for (Complex root: roots) {
            fValue = fValue.multiply(z.sub(root));
        }
        return fValue;
    }
    // converts this representation to ComplexPolynomial type
    public ComplexPolynomial toComplexPolynom() {
        ComplexPolynomial result = new ComplexPolynomial(constant);
        for (int i = 0; i < roots.size(); i++) {
            result = result.multiply(new ComplexPolynomial(roots.get(i).negate(), Complex.ONE));
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

    public int indexOfClosestRootFor(Complex z, double treshold) {
        double squaredDistance = -1;
        int index = -1;

        for (int i = 0; i < roots.size(); i++) {
            Complex root = roots.get(i);
            double newSquaredDistance;
            newSquaredDistance = Math.pow(root.getReal() - z.getReal(), 2) + Math.pow(root.getImaginary() - z.getImaginary(), 2);
            if (newSquaredDistance < squaredDistance && newSquaredDistance <= treshold * treshold) {
                index = i;
                squaredDistance = newSquaredDistance;
            }
        }
        return index;
    }

    public Complex getConstant() {
        return constant;
    }

    public List<Complex> getRoots() {
        return Collections.unmodifiableList(roots);
    }
}
