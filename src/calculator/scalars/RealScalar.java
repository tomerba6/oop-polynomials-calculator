package calculator.scalars;

/**
 * Represents a real-valued scalar.
 * <p>
 * This class implements the {@link Scalar} interface for real numbers using the
 * primitive {@code double} type. It utilizes the Double Dispatch pattern for mixed-type
 * arithmetic and includes specific rounding logic to handle common floating-point
 * imprecision errors during sign and equality checks.
 * </p>
 */
public class RealScalar implements Scalar {

    /**
     * The underlying double-precision value of this scalar.
     */
    private double number;

    /**
     * Constructs a new RealScalar with the specified double value.
     *
     * @param number The double value.
     */
    public RealScalar(double number) {
        this.number = number;
    }

    /**
     * Adds another scalar to this real scalar.
     * Initiates the double dispatch process.
     *
     * @param s The scalar to add.
     * @return A new Scalar representing the sum.
     */
    @Override
    public Scalar add(Scalar s) {
        return s.addReal(this);
    }

    /**
     * Multiplies this real scalar by another scalar.
     * Initiates the double dispatch process.
     *
     * @param s The scalar to multiply by.
     * @return A new Scalar representing the product.
     */
    @Override
    public Scalar mul(Scalar s) {
        return s.mulReal(this);
    }

    /**
     * Returns the negation of this real scalar.
     *
     * @return A new RealScalar with the negated value.
     */
    @Override
    public Scalar neg() {
        return new RealScalar(-number);
    }

    /**
     * Raises this real scalar to the power of a given exponent.
     *
     * @param exponent A non-negative integer representing the exponent.
     * @return A new Scalar representing the result of the exponentiation.
     * @throws ArithmeticException if attempting to raise zero to the power of zero.
     */
    @Override
    public Scalar power(int exponent) {
        if (exponent == 0) {
            if (this.sign() == 0) {
                throw new ArithmeticException("Cannot raise zero by zero");
            }
            return new RealScalar(1.0);
        }

        Scalar s = this;
        for (int i = exponent; i > 1; i--) {
            s = s.mul(this);
        }

        return s;
    }

    /**
     * Returns the mathematical sign of this real scalar.
     * <p>
     * To mitigate standard floating-point imprecision, the value is scaled
     * by 1,000,000 and rounded to the nearest integer before evaluating the sign.
     * This effectively considers values extremely close to zero as exactly zero.
     * </p>
     *
     * @return 1 if positive, -1 if negative, or 0 if practically zero.
     */
    @Override
    public int sign() {
        long roundedValue = Math.round(this.number * 1_000_000.0);

        if (roundedValue == 0) {
            return 0;
        } else if (roundedValue > 0) {
            return 1;
        }
        return -1;
    }

    /**
     * Double dispatch method for adding an IntegerScalar to this real scalar.
     *
     * @param s The IntegerScalar to add.
     * @return A new RealScalar representing the sum.
     */
    @Override
    public Scalar addInteger(IntegerScalar s) {
        return new RealScalar(s.getNumber() + number);
    }

    /**
     * Double dispatch method for adding a RationalScalar to this real scalar.
     * Delegates the addition back to the RationalScalar to handle the conversion.
     *
     * @param s The RationalScalar to add.
     * @return A new RealScalar representing the sum.
     */
    @Override
    public Scalar addRational(RationalScalar s) {
        return s.addReal(this);
    }

    /**
     * Double dispatch method for multiplying an IntegerScalar with this real scalar.
     *
     * @param s The IntegerScalar to multiply by.
     * @return A new RealScalar representing the product.
     */
    @Override
    public Scalar mulInteger(IntegerScalar s) {
        return new RealScalar(s.getNumber() * number);
    }

    /**
     * Double dispatch method for multiplying a RationalScalar with this real scalar.
     * Delegates the multiplication back to the RationalScalar to handle the conversion.
     *
     * @param s The RationalScalar to multiply by.
     * @return A new RealScalar representing the product.
     */
    @Override
    public Scalar mulRational(RationalScalar s) {
        return s.mulReal(this);
    }

    /**
     * Double dispatch method for adding another RealScalar to this one.
     *
     * @param s The RealScalar to add.
     * @return A new RealScalar representing the sum.
     */
    @Override
    public Scalar addReal(RealScalar s) {
        return new RealScalar(this.number + s.number);
    }

    /**
     * Double dispatch method for multiplying another RealScalar with this one.
     *
     * @param s The RealScalar to multiply by.
     * @return A new RealScalar representing the product.
     */
    @Override
    public Scalar mulReal(RealScalar s) {
        return new RealScalar(this.number * s.number);
    }

    /**
     * Retrieves the underlying double-precision value of this scalar.
     *
     * @return The double value.
     */
    public double getNumber() {
        return number;
    }

    /**
     * Returns the string representation of this real scalar.
     *
     * @return The string format of the double value.
     */
    @Override
    public String toString() {
        return "" + number;
    }

    /**
     * Compares this scalar to another object for equality.
     * Two scalars are considered equal if their mathematical difference is zero
     * (subject to the precision limits defined in the {@link #sign()} method).
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