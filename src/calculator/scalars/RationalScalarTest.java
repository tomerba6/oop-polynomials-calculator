package calculator.scalars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RationalScalarTest {
    private Scalar half;
    private Scalar third;
    private Scalar negFourth;
    private Scalar two;

    @BeforeEach
    void setUp() {
        half = new RationalScalar(1, 2);
        third = new RationalScalar(1, 3);
        negFourth = new RationalScalar(1, -4);
        two = new IntegerScalar(2);
    }

    @Test
    void add() {
        assertAll(
                () -> assertEquals(new RationalScalar(5, 6), half.add(third), "1/2 + 1/3 should be 5/6"),
                () -> assertEquals(new IntegerScalar(1), half.add(half), "Adding 1/2 to 1/2 should result in an IntegerScalar(1)"),
                () -> assertEquals(new RationalScalar(5, 2), half.add(two), "Adding Integer 2 to 1/2 should result in 5/2")
        );
    }

    @Test
    void mul() {
        assertAll(
                () -> assertEquals(new RationalScalar(1, 6), half.mul(third), "1/2 times 1/3 should be 1/6"),
                () -> assertEquals(new IntegerScalar(1), half.mul(two), "1/2 times 2 should result in an IntegerScalar(1)"),
                () -> assertEquals(new IntegerScalar(-2), half.mul(new IntegerScalar(-4)), "1/2 times -4 should result in -2"),
                () -> assertEquals(new IntegerScalar(0), half.mul(new IntegerScalar(0)), "Multiplying by 0 should result in IntegerScalar(0)")
        );
    }

    @Test
    void neg() {
        assertAll(
                () -> assertEquals(new RationalScalar(1, 4), negFourth.neg(), "Negation of 1/-4 should be 1/4"),
                () -> assertEquals(new IntegerScalar(-5), new RationalScalar(10, 2).neg(), "Negation of 10/2 should result in IntegerScalar -5"),
                () -> assertEquals(new RationalScalar(-5, 3), new RationalScalar(-5, -3).neg(), "Negation of -5/-3 should be -5/3")
        );
    }

    @Test
    void power() {
        assertAll(
                () -> assertEquals(new RationalScalar(1, 8), half.power(3), "1/2 raised by 3 should be 1/8"),
                () -> assertEquals(new IntegerScalar(4), new RationalScalar(2, 1).power(2), "2/1 squared should result in IntegerScalar 4"),
                () -> assertEquals(new IntegerScalar(1), half.power(0), "Raising by 0 should result in IntegerScalar(1)"),
                () -> assertThrows(ArithmeticException.class, () -> new RationalScalar(0, 5).power(0), "0/5 raised to 0 should throw an exception")
        );
    }

    @Test
    void sign() {
        assertAll(
                () -> assertEquals(1, new RationalScalar(10, 2).sign(), "10/2 is positive"),
                () -> assertEquals(-1, new RationalScalar(-10, 2).sign(), "-10/2 is negative"),
                () -> assertEquals(1, new RationalScalar(-1, -10).sign(), "-1/-10 is positive"),
                () -> assertEquals(0, new RationalScalar(0, 1).sign(), "0/1 is zero")
        );
    }

    @Test
    void reduce() {
        assertAll(
                () -> assertEquals(new IntegerScalar(5), new RationalScalar(10, 2).reduce(), "10/2 should reduce to IntegerScalar 5"),
                () -> assertEquals(new IntegerScalar(2), new RationalScalar(-4, -2).reduce(), "-4/-2 should reduce to positive IntegerScalar 2"),
                () -> assertEquals(IntegerScalar.class, new RationalScalar(6, 1).reduce().getClass(), "6/1 must reduce to an IntegerScalar type")
        );
    }

    @Test
    void testToString() {
        assertAll(
                () -> assertEquals("1/2", half.toString()),
                () -> assertEquals("5", new RationalScalar(10, 2).reduce().toString(), "Reduced 10/2 should print as '5'"),
                () -> assertEquals("0", new RationalScalar(0, 100).reduce().toString(), "Reduced 0/100 should print as '0'"),
                () -> assertEquals("-1/4", negFourth.toString(), "1/-4 should automatically format as -1/4"),
                () -> assertEquals("-3/2", new RationalScalar(12, -8).toString(), "12/-8 should be reduced and formatted correctly upon creation")
        );
    }

    @Test
    void testEquals() {
        assertAll(
                () -> assertEquals(new RationalScalar(4, 2), new RationalScalar(2, 1)),
                () -> assertEquals(new RationalScalar(10, 2), new IntegerScalar(5), "Rational 10/2 should equal Integer 5"),
                () -> assertEquals(new IntegerScalar(0), new RationalScalar(0, 7), "Integer 0 should equal Rational 0/7")
        );
    }
}