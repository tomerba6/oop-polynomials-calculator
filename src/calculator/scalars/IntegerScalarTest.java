package calculator.scalars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerScalarTest {
    private Scalar posFive;
    private Scalar negThree;

    private Scalar zero;
    private Scalar one;

    private Scalar twoByThree;


    @BeforeEach
    void setUp() {
         posFive = new IntegerScalar(5);
         negThree = new IntegerScalar(-3);
         zero = new IntegerScalar(0);
         one = new IntegerScalar(1);
         twoByThree = new RationalScalar(2,3);
    }

    @Test
    void add() {
        assertAll(
                () -> assertEquals(new IntegerScalar(2), posFive.add(negThree), "5 + (-3) should be 2"),
                () -> assertEquals(new RationalScalar(17, 3), posFive.add(twoByThree), "5 + 2/3 should result in RationalScalar(17/3)"),
                () -> assertEquals(posFive, posFive.add(zero), "5 + 0 should be 5"),
                () -> assertEquals(zero, posFive.add(new IntegerScalar(-5)), "5 + (-5) should be 0")
        );
    }

    @Test
    void mul() {
        assertAll(
                () -> assertEquals(new IntegerScalar(-15), posFive.mul(negThree), "5 times -3 should be -15"),
                () -> assertEquals(new RationalScalar(10, 3), posFive.mul(twoByThree), "5 times 2/3 should be 10/3"),
                () -> assertEquals(zero, posFive.mul(zero), "Multiplying by 0 should result in 0"),
                () -> assertEquals(posFive, posFive.mul(one), "5 times 1 should be 5")

        );
    }

    @Test
    void neg() {
        assertAll(
                () -> assertEquals(new IntegerScalar(-5), posFive.neg(), "Negation of 5 should be -5"),
                () -> assertEquals(zero, zero.neg(), "Negation of 0 should stay 0"),
                () -> assertEquals(new IntegerScalar(3), negThree.neg(), "Negation of -3 should be 3")
        );
    }

    @Test
    void power() {
        assertAll(
                () -> assertEquals(new IntegerScalar(125), posFive.power(3), "5 raised by 3 should be 125"),
                () -> assertThrows(ArithmeticException.class, () -> zero.power(0), "0 raised by 0 should throw ArithmeticException"),
                () -> assertEquals(new IntegerScalar(9), negThree.power(2),  "(-3) squared should be 9"),
                () -> assertEquals(new IntegerScalar(-27), negThree.power(3),  "(-3) raised by 3 should be -27"),
                () -> assertEquals(new IntegerScalar(1), posFive.power(0), "5 raised by 0 should be 1"),
                () -> assertEquals(new IntegerScalar(5), posFive.power(1), "5 raised by 1 should be 5")
        );
    }

    @Test
    void sign() {
        assertAll(
                () -> assertEquals(1, posFive.sign(), "Sign of 5 should be 1"),
                () -> assertEquals(-1, negThree.sign(), "Sign of -3 be -1"),
                () -> assertEquals(0, zero.sign(), "Sign of 0 should be 0")
        );
    }


    @Test
    void testToString() {
        assertAll(
                () -> assertEquals("5", posFive.toString(), "Positive IntegerScalar should return its numeric value as a string"),
                () -> assertEquals("-3", negThree.toString(), "Negative IntegerScalar should include the minus sign in its string representation"),
                () -> assertEquals("0", zero.toString(), "Zero IntegerScalar should be represented by the string '0'")
        );
    }

    @Test
    void testEquals() {
        IntegerScalar anotherFive = new IntegerScalar(5);
        RationalScalar rationalFive = new RationalScalar(5, 1);

        assertAll(
                () -> assertEquals(posFive, posFive, "IntegerScalar should equal itself"),
                () -> assertTrue(posFive.equals(anotherFive) && anotherFive.equals(posFive), "Two different objects with the value 5 should equal each other"),
                () -> assertNotEquals(posFive, negThree, "IntegerScalar(5) should not equal IntegerScalar(-3)"),
                () -> assertEquals(posFive, rationalFive, "IntegerScalar 5 should equal RationalScalar 5/1"),
                () -> assertNotEquals(null, posFive, "IntegerScalar should not equal null"),
                () -> assertNotEquals("5", posFive, "IntegerScalar should not equal a String or other non-Scalar types")
        );
    }
}