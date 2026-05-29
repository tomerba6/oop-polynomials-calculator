package calculator.nomials;

import calculator.scalars.IntegerScalar;
import calculator.scalars.RationalScalar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {
    private Polynomial pInt1;  // 1 + 2x + 3x^2
    private Polynomial pInt2;  // -2x
    private Polynomial pInt3;  // 1 + 2x (with trailing zeros)
    private Polynomial pZero;  // 0
    private Polynomial pRat1;  // 1/2 + 3/2x^2
    private Polynomial pRat2;  // 1/4x - 1/2x^2

    @BeforeEach
    void setUp() {
        pInt1 = Polynomial.build("1 2 3");
        pInt2 = Polynomial.build("0 -2");
        pInt3 = Polynomial.build("1 2 0 0");
        pZero = Polynomial.build("0 0 0");
        pRat1 = Polynomial.build("1/2 0 3/2");
        pRat2 = Polynomial.build("0 1/4 -1/2");

    }

    @Test
    void testBuildAndToString() {
        assertAll(
                () -> assertEquals("1+2x+3x^2", pInt1.toString()),
                () -> assertEquals("-2x", pInt2.toString()),
                () -> assertEquals("1+2x", pInt3.toString()),
                () -> assertEquals("0", pZero.toString(), "Zero polynomial should just print 0"),
                () -> assertEquals("1/2+3/2x^2", pRat1.toString()),
                () -> assertEquals("1/4x-1/2x^2", pRat2.toString()),
                () -> assertEquals("1/2x+3x^2-5/3x^3", Polynomial.build("0 1/2 3 -5/3").toString(), "Mixed integers and fractions")

        );
    }

    @Test
    void testAdd() {
        assertAll(
                //(1 + 2x + 3x^2) + (-2x) = 1 + 3x^2
                () -> assertEquals("1+3x^2", pInt1.add(pInt2).toString()),
                //(1/2 + 3/2x^2) + (1/4x - 1/2x^2) = 1/2 + 1/4x + 1x^2
                () -> assertEquals("1/2+1/4x+x^2", pRat1.add(pRat2).toString()),
                //(1 + 2x + 3x^2) + (1/2 + 3/2x^2) = 3/2 + 2x + 9/2x^2
                () -> assertEquals("3/2+2x+9/2x^2", pInt1.add(pRat1).toString()),
                // Addition with Zero
                () -> assertEquals(pInt1, pInt1.add(pZero), "Adding zero should not change the polynomial")
        );
    }

    @Test
    void testMul() {
        assertAll(
                //(-2x) * (-2x) = 4x^2
                () -> assertEquals("4x^2", pInt2.mul(pInt2).toString()),
                //(1/2 + 3/2x^2) * (-2x) = -1x - 3x^3
                () -> assertEquals("-x-3x^3", pRat1.mul(pInt2).toString()),
                //(1/2 + 3/2x^2) * (1/4x - 1/2x^2) = 1/8x - 1/4x^2 + 3/8x^3 - 3/4x^4
                () -> assertEquals("1/8x-1/4x^2+3/8x^3-3/4x^4", pRat1.mul(pRat2).toString()),
                // Multiplication by Zero
                () -> assertEquals("0", pInt1.mul(pZero).toString())
        );
    }

    @Test
    void testEvaluate() {
        assertAll(
                //x=2: 1 + 2(2) + 3(2^2) = 1 + 4 + 12 = 17
                () -> assertEquals(new IntegerScalar(17), pInt1.evaluate(new IntegerScalar(2))),
                //x=2: 1/2 + 3/2(2^2) = 1/2 + 6 = 13/2
                () -> assertEquals(new RationalScalar(13, 2), pRat1.evaluate(new IntegerScalar(2))),
                //x=1/2: -2(1/2) = -1
                () -> assertEquals(new IntegerScalar(-1), pInt2.evaluate(new RationalScalar(1, 2))),
                // Evaluate at 0
                () -> assertEquals(new IntegerScalar(1), pInt1.evaluate(new IntegerScalar(0))),
                () -> assertEquals(new IntegerScalar(0), pInt2.evaluate(new IntegerScalar(0)))
        );
    }

    @Test
    void testDerivative() {
        assertAll("Polynomial derivative logic",
                // Derivative of 1 + 2x + 3x^2 = 2 + 6x
                () -> assertEquals("2+6x", pInt1.derivative().toString()),
                // Derivative of 1/2 + 3/2x^2 = 3x
                () -> assertEquals("3x", pRat1.derivative().toString()),
                // Derivative of -2x = -2
                () -> assertEquals("-2", pInt2.derivative().toString()),
                () -> assertEquals("0", pZero.derivative().toString(), "Derivative of zero is zero")
        );
    }

    @Test
    void testEquals() {
        Polynomial pInt1Copy = Polynomial.build("1 2 3");
        Polynomial unreducedRational = Polynomial.build("2/2 4/2 6/2"); // equals 1 2 3

        assertAll(
                () -> assertEquals(pInt1, pInt1Copy, "Structurally identical polynomials should be equal"),
                () -> assertEquals(pInt1, pInt1),
                () -> assertEquals(pInt1, unreducedRational),
                () -> assertEquals(pZero, Polynomial.build("0")),
                () -> assertNotEquals(pInt1, pInt2, "Different polynomials should not be equal"),
                () -> assertNotEquals(pInt1, null, "Should not equal null")
        );
    }
}