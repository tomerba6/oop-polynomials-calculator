package calculator.scalars;

/**
 * Represents an integer-valued scalar.
 * <p>
 * This class implements the {@link Scalar} interface and provides
 * arithmetic operations specifically for integer numbers. It utilizes
 * the Double Dispatch pattern to handle operations with other scalar types.
 * </p>
 */
public class IntegerScalar implements Scalar {

    /**
     * The underlying integer value of this scalar.
     */
    private int number;

    /**
     * Constructs a new IntegerScalar with the specified integer value.
     *
     * @param number The integer value.
     */
    public IntegerScalar(int number) {
        this.number = number;
    }

    /**
     * Adds another scalar to this integer scalar.
     * Initiates the double dispatch process.
     *
     * @param s The scalar to add.
     * @return A new Scalar representing the sum.
     */
    @Override
    public Scalar add(Scalar s) {
        return s.addInteger(this);
    }

    /**
     * Multiplies this integer scalar by another scalar.
     * Initiates the double dispatch process.
     *
     * @param s The scalar to multiply by.
     * @return A new Scalar representing the product.
     */
    @Override
    public Scalar mul(Scalar s) {
        return s.mulInteger(this);
    }

    /**
     * Returns the negation of this integer scalar.
     *
     * @return A new IntegerScalar with the negated value.
     */
    @Override
    public Scalar neg() {
        return new IntegerScalar(-this.number);
    }

    /**
     * Raises this integer scalar to the power of a given exponent.
     *
     * @param exponent A non-negative integer representing the exponent.
     * @return A new Scalar representing the result of the exponentiation.
     * @throws ArithmeticException if attempting to raise zero to the power of zero.
     */
    @Override
    public Scalar power(int exponent) {
        if (exponent == 0) {
            if (this.number == 0) {
                throw new ArithmeticException("Cannot raise zero by zero");
            }
            return new IntegerScalar(1);
        }

        Scalar s = this;
        for (int i = exponent; i > 1; i--) {
            s = s.mul(this);
        }

        return s;
    }

    /**
     * Returns the mathematical sign of this integer scalar.
     *
     * @return 1 if positive, -1 if negative, or 0 if the number is zero.
     */
    @Override
    public int sign() {
        if (this.number > 0) {
            return 1;
        } else if (this.number < 0) {
            return -1;
        }
        return 0;
    }

    /**
     * Double dispatch method for adding another IntegerScalar to this one.
     *
     * @param s The IntegerScalar to add.
     * @return A new IntegerScalar representing the sum.
     */
    @Override
    public Scalar addInteger(IntegerScalar s) {
        return new IntegerScalar(this.number + s.number);
    }

    /**
     * Double dispatch method for adding a RationalScalar to this integer.
     * Delegates the addition back to the RationalScalar to handle the fractional logic.
     *
     * @param s The RationalScalar to add.
     * @return A new Scalar representing the sum.
     */
    @Override
    public Scalar addRational(RationalScalar s) {
        return s.addInteger(this);
    }

    /**
     * Double dispatch method for multiplying another IntegerScalar with this one.
     *
     * @param s The IntegerScalar to multiply by.
     * @return A new IntegerScalar representing the product.
     */
    @Override
    public Scalar mulInteger(IntegerScalar s) {
        return new IntegerScalar(this.number * s.number);
    }

    /**
     * Double dispatch method for multiplying a RationalScalar with this integer.
     * Delegates the multiplication back to the RationalScalar.
     *
     * @param s The RationalScalar to multiply by.
     * @return A new Scalar representing the product.
     */
    @Override
    public Scalar mulRational(RationalScalar s) {
        return s.mulInteger(this);
    }

    /**
     * Double dispatch method for adding a RealScalar to this integer.
     * Delegates the addition back to the RealScalar.
     *
     * @param s The RealScalar to add.
     * @return A new Scalar representing the sum.
     */
    @Override
    public Scalar addReal(RealScalar s) {
        return s.addInteger(this);
    }

    /**
     * Double dispatch method for multiplying a RealScalar with this integer.
     * Delegates the multiplication back to the RealScalar.
     *
     * @param s The RealScalar to multiply by.
     * @return A new Scalar representing the product.
     */
    @Override
    public Scalar mulReal(RealScalar s) {
        return s.mulInteger(this);
    }

    /**
     * Retrieves the underlying integer value of this scalar.
     *
     * @return The integer value.
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Returns the string representation of this integer scalar.
     *
     * @return The string format of the integer.
     */
    @Override
    public String toString() {
        return String.valueOf(this.number);
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