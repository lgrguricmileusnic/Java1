

import hr.fer.zemris.math.Complex;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ComplexTest {

    private static final double DELTA = 1E-7;

    @Test
    public void module() {
        Complex c = new Complex(3, 4);
        assertEquals(5, c.module(), DELTA);
    }

    @Test
    public void multiply() {
        Complex c = new Complex(3, 4).multiply(new Complex(5, 6));
        assertEquals(-9, c.getReal(), DELTA);
        assertEquals(38, c.getImaginary(), DELTA);
    }

    @Test
    public void divide() {
        Complex c = new Complex(3, 4).divide(new Complex(5, 6));
        assertEquals(39.0 / 61, c.getReal(), DELTA);
        assertEquals(2.0 / 61, c.getImaginary(), DELTA);
    }



    @Test
    public void add() {
        Complex c = new Complex(3, 4).add(new Complex(5, 6));
        assertEquals(8, c.getReal(), DELTA);
        assertEquals(10, c.getImaginary(), DELTA);
    }

    @Test
    public void sub() {
        Complex c = new Complex(3, 4).sub(new Complex(5, 6));
        assertEquals(-2, c.getReal(), DELTA);
        assertEquals(-2, c.getImaginary(), DELTA);
    }

    @Test
    public void negate() {
        Complex c = new Complex(3, 4).negate();
        assertEquals(-3, c.getReal(), DELTA);
        assertEquals(-4, c.getImaginary(), DELTA);
    }

    @Test
    public void power() {
        Complex c = new Complex(3, 4).power(6);
        assertEquals(11753, c.getReal(), DELTA);
        assertEquals(-10296, c.getImaginary(), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void powerLessThanZero() {
        Complex c = new Complex(3, 4).power(-6);
    }

    @Test
    public void getRealImMagAng() {
        Complex c = new Complex(3, 4);
        assertEquals(3, c.getReal(), DELTA);
        assertEquals(4, c.getImaginary(), DELTA);
        assertEquals(5, c.module(), DELTA);

    }

    @Test
    public void root() {
        List<Complex> roots = new Complex(3, 4).root(4);

        assertEquals(1.455346690225355, roots.get(0).getReal(), DELTA);
        assertEquals(0.34356074972251244, roots.get(0).getImaginary(), DELTA);
        assertEquals(-0.34356074972251244, roots.get(1).getReal(), DELTA);
        assertEquals(1.455346690225355, roots.get(1).getImaginary(), DELTA);
        assertEquals(-1.455346690225355, roots.get(2).getReal(), DELTA);
        assertEquals(-0.34356074972251244, roots.get(2).getImaginary(), DELTA);
        assertEquals(0.34356074972251244, roots.get(3).getReal(), DELTA);
        assertEquals(-1.455346690225355, roots.get(3).getImaginary(), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rootLessThan1() {
        List<Complex> roots = new Complex(3, 4).root(-4);

    }

}