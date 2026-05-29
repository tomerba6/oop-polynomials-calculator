package calculator.nomials;

import calculator.scalars.IntegerScalar;
import calculator.scalars.RationalScalar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonomialTest {
    private Monomial m2x3;     // 2x^3
    private Monomial m3x3;     // 3x^3
    private Monomial mNeg1x2;  // -1x^2
    private Monomial mZero;    // 0x^5
    private Monomial m1x1;     // 1x^1
    private Monomial mHalfX2;        // 1/2 x^2
    private Monomial mThirdX2;       // 1/3 x^2
    private Monomial mTwoThirdsX3;   // 2/3 x^3
    private Monomial mRationalZero;  // 0/5 x^4

    @BeforeEach
    void setUp() {
        m2x3 = new Monomial(3, new IntegerScalar(2));
        m3x3 = new Monomial(3, new IntegerScalar(3));
        mNeg1x2 = new Monomial(2, new IntegerScalar(-1));
        mZero = new Monomial(5, new IntegerScalar(0));
        m1x1 = new Monomial(1, new IntegerScalar(1));
        mHalfX2 = new Monomial(2, new RationalScalar(1, 2));
        mThirdX2 = new Monomial(2, new RationalScalar(1, 3));
        mTwoThirdsX3 = new Monomial(3, new RationalScalar(2, 3));
        mRationalZero = new Monomial(4, new RationalScalar(0, 5));
    }

    @Test
    void add() {
        assertAll(
                //2x^3 + 3x^3 = 5x^3
                () -> assertEquals(new Monomial(3, new IntegerScalar(5)), m2x3.add(m3x3)),
                //1/2 x^2 + 1/3 x^2 = 5/6 x^2
                () -> assertEquals(new Monomial(2, new RationalScalar(5, 6)), mHalfX2.add(mThirdX2)),
                //1/2 x^2 + (-1x^2) = -1/2 x^2
                () -> assertEquals(new Monomial(2, new RationalScalar(-1, 2)), mHalfX2.add(mNeg1x2)),
                //1/2 x^2 + 2/3 x^3 = null
                () -> assertNull(mHalfX2.add(mTwoThirdsX3), "Adding monomials with different exponents must return null")
        );
    }

    @Test
    void mul() {
        assertAll(
                //2x^3 * 3x^3 = 6x^6
                () -> assertEquals(new Monomial(6, new IntegerScalar(6)), m2x3.mul(m3x3)),
                //(1/2 x^2) * (2/3 x^3) = 1/3 x^5
                () -> assertEquals(new Monomial(5, new RationalScalar(1, 3)), mHalfX2.mul(mTwoThirdsX3)),
                //(1/2 x^2) * (-1x^2) = -1/2 x^4
                () -> assertEquals(new Monomial(4, new RationalScalar(-1, 2)), mHalfX2.mul(mNeg1x2))
        );
    }

    @Test
    void evaluate() {
        assertAll(
                //2(2)^3 = 16
                () -> assertEquals(new IntegerScalar(16), m2x3.evaluate(new IntegerScalar(2))),
                //(1/2)(4)^2 = 8
                () -> assertEquals(new IntegerScalar(8), mHalfX2.evaluate(new IntegerScalar(4))),
                //(1/2)(1/2)^2 = 1/8
                () -> assertEquals(new RationalScalar(1, 8), mHalfX2.evaluate(new RationalScalar(1, 2))),
                //Evaluating a constant should return the constant
                () -> assertEquals(new IntegerScalar(5), new Monomial(0, new IntegerScalar(5)).evaluate(new IntegerScalar(0)))
        );
    }

    @Test
    void derivative() {
        assertAll(
                //d/dx [2x^3] = 6x^2
                () -> assertEquals(new Monomial(2, new IntegerScalar(6)), m2x3.derivative()),
                //d/dx [1/2 x^2] = 1x^1
                () -> assertEquals(new Monomial(1, new IntegerScalar(1)), mHalfX2.derivative()),
                //d/dx [0/5 x^4] = 0x^3
                () -> assertEquals(new Monomial(3, new IntegerScalar(0)), mRationalZero.derivative()),
                // d/dx [5x^0] = 0x^0
        () -> assertEquals(new Monomial(0, new IntegerScalar(0)), new Monomial(0, new IntegerScalar(5)).derivative())
        );
    }

    @Test
    void sign() {
        assertAll(
                () -> assertEquals(1, mHalfX2.sign(), "1/2 x^2 is positive"),
                () -> assertEquals(-1, mNeg1x2.sign(), "-1x^2 is negative"),
                () -> assertEquals(0, mRationalZero.sign(), "0/5 x^4 is zero")
        );
    }

    @Test
    void testEquals() {
        assertAll(
                () -> assertEquals(new Monomial(2, new RationalScalar(2, 4)), mHalfX2, "1/2 x^2 should equal 2/4 x^2"),
                () -> assertEquals(mZero, mRationalZero, "Integer zero coefficient should equal Rational zero coefficient"),
                () -> assertEquals(new Monomial(3, new RationalScalar(2, 1)), m2x3, "Rational coefficient 2/1 should equal Integer coefficient 2")
        );
    }

    @Test
    void testToString() {
        assertAll(
                () -> assertEquals("0", mZero.toString(), "Integer zero should print '0'"),
                () -> assertEquals("0", mRationalZero.toString(), "Rational zero should print '0'"),
                () -> assertEquals("1/2x^2", mHalfX2.toString()),
                () -> assertEquals("1/2x", new Monomial(1, new RationalScalar(1, 2)).toString()),
                () -> assertEquals("x", m1x1.toString()),
                () -> assertEquals("-x^2", mNeg1x2.toString(), "Coefficient of -1 should print as just a minus sign"),
                () -> assertEquals("4", new Monomial(0, new IntegerScalar(4)).toString(), "Exponent of 0 should print the coefficient only")
        );
    }
}