package calculator.scalars;

/**
 * Represents a rational numeric value (a fraction).
 * <p>
 * This class implements the {@link Scalar} interface for rational numbers,
 * maintaining a numerator and a denominator. The fraction is automatically
 * simplified (reduced) upon instantiation, and negative signs are normalized
 * to the numerator. It utilizes the Double Dispatch pattern for mixed-type arithmetic.
 * </p>
 */
public class RationalScalar implements Scalar {

    /**
     * The numerator of the rational number.
     */
    private int numerator;

    /**
     * The denominator of the rational number. Always kept positive.
     */
    private int denominator;

    /**
     * Constructs a RationalScalar and automatically reduces the fraction to its
     * simplest form. Also ensures the denominator is positive.
     *
     * @param numerator   The numerator of the fraction.
     * @param denominator The denominator of the fraction.
     */
    public RationalScalar(int numerator, int denominator) {
        int gcd = gcd(Math.abs(numerator), Math.abs(denominator));
        int n = numerator / gcd;
        int d = denominator / gcd;

        if (d < 0) {
            n = -n;
            d = -d;
        }

        this.numerator = n;
        this.denominator = d;
    }

    /**
     * Adds another scalar to this rational scalar.
     * Initiates the double dispatch process.
     *
     * @param s The scalar to add.
     * @return A new Scalar representing the sum.
     */
    @Override
    public Scalar add(Scalar s) {
        return s.addRational(this);
    }

    /**
     * Multiplies this rational scalar by another scalar.
     * Initiates the double dispatch process.
     *
     * @param s The scalar to multiply by.
     * @return A new Scalar representing the product.
     */
    @Override
    public Scalar mul(Scalar s) {
        return s.mulRational(this);
    }

    /**
     * Returns the negation of this rational scalar.
     *
     * @return A new Scalar with the negated value.
     */
    @Override
    public Scalar neg() {
        RationalScalar tempScalar = new RationalScalar(-numerator, denominator);
        return tempScalar.reduce();
    }

    /**
     * Raises this rational scalar to the power of a given exponent.
     *
     * @param exponent A non-negative integer representing the exponent.
     * @return A new Scalar representing the result of the exponentiation.
     * @throws ArithmeticException if attempting to raise zero to the power of zero.
     */
    @Override
    public Scalar power(int exponent) {
        if (exponent == 0) {
            if (this.numerator == 0) {
                throw new ArithmeticException("Cannot raise zero by zero");
            }
            return new IntegerScalar(1);
        }

        Scalar s = this.reduce();
        for (int i = exponent; i > 1; i--) {
            s = s.mul(this);
        }

        return s;
    }

    /**
     * Returns the mathematical sign of this rational scalar.
     * Because the denominator is guaranteed to be positive, this only checks the numerator.
     *
     * @return 1 if positive, -1 if negative, or 0 if zero.
     */
    @Override
    public int sign() {
        return Integer.compare(numerator, 0);
    }

    /**
     * Reduces this rational scalar to an IntegerScalar if the denominator is 1.
     *
     * @return An IntegerScalar if the fraction represents a whole number, otherwise returns this RationalScalar.
     */
    public Scalar reduce() {
        if (this.denominator == 1) {
            return new IntegerScalar(this.numerator);
        }
        return this;
    }

    /**
     * Helper method for calculating the Greatest Common Divisor (GCD).
     *
     * @param a first integer.
     * @param b second integer.
     * @return The Greatest Common Divisor of a and b.
     */
    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    /**
     * Double dispatch method for adding an IntegerScalar to this rational.
     *
     * @param s The IntegerScalar to add.
     * @return A new reduced Scalar representing the sum.
     */
    @Override
    public Scalar addInteger(IntegerScalar s) {
        int newNumerator = this.numerator + s.getNumber() * this.denominator;
        return new RationalScalar(newNumerator, this.denominator).reduce();
    }

    /**
     * Double dispatch method for adding another RationalScalar to this one.
     *
     * @param s The RationalScalar to add.
     * @return A new reduced Scalar representing the sum.
     */
    @Override
    public Scalar addRational(RationalScalar s) {
        int newNumerator = this.numerator * s.denominator + s.numerator * this.denominator;
        int newDenominator = this.denominator * s.denominator;
        RationalScalar newRational = new RationalScalar(newNumerator, newDenominator);
        return newRational.reduce();
    }

    /**
     * Double dispatch method for multiplying an IntegerScalar with this rational.
     *
     * @param s The IntegerScalar to multiply by.
     * @return A new reduced Scalar representing the product.
     */
    @Override
    public Scalar mulInteger(IntegerScalar s) {
        int newNumerator = this.numerator * s.getNumber();
        RationalScalar newRational = new RationalScalar(newNumerator, this.denominator);
        return newRational.reduce();
    }

    /**
     * Double dispatch method for multiplying another RationalScalar with this one.
     *
     * @param s The RationalScalar to multiply by.
     * @return A new reduced Scalar representing the product.
     */
    @Override
    public Scalar mulRational(RationalScalar s) {
        int newNumerator = this.numerator * s.numerator;
        int newDenominator = this.denominator * s.denominator;
        RationalScalar newRational = new RationalScalar(newNumerator, newDenominator);
        return newRational.reduce();
    }

    /**
     * Double dispatch method for adding a RealScalar to this rational.
     *
     * @param s The RealScalar to add.
     * @return A new RealScalar representing the sum.
     */
    @Override
    public Scalar addReal(RealScalar s) {
        return new RealScalar(((double) this.numerator / this.denominator) + s.getNumber());
    }

    /**
     * Double dispatch method for multiplying a RealScalar with this rational.
     *
     * @param s The RealScalar to multiply by.
     * @return A new RealScalar representing the product.
     */
    @Override
    public Scalar mulReal(RealScalar s) {
        return new RealScalar(((double) this.numerator / this.denominator) * s.getNumber());
    }

    /**
     * Returns the string representation of this rational scalar.
     * Formats as an integer if the denominator is 1, otherwise as "numerator/denominator".
     *
     * @return The formatted string of the rational value.
     */
    @Override
    public String toString() {
        if (this.denominator == 1) {
            return String.valueOf(this.numerator);
        }
        return String.format("%d/%d", this.numerator, this.denominator);
    }

    /**
     * Compares this scalar to another object for equality.
     * Two scalars are considered equal if their mathematical difference is zero.
     *
     * @param o The object to compare with.
     * @return true if the objects are mathematically equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Scalar other)) return false;

        Scalar negativeOther = other.neg();
        Scalar difference = this.add(negativeOther);

        return difference.sign() == 0;
    }
}