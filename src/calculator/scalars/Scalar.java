package calculator.scalars;

/**
 * Represents an abstract numeric value.
 * <p>
 * This interface defines the core arithmetic operations for scalars.
 * It also utilizes the Double Dispatch design pattern to safely resolve
 * operations between different underlying scalar implementations
 * (Integer, Rational, and Real).
 * </p>
 */
public interface Scalar {

    /**
     * Adds another scalar to this scalar.
     *
     * @param s The scalar to add.
     * @return A new Scalar representing the sum.
     */
    Scalar add(Scalar s);

    /**
     * Multiplies this scalar by another scalar.
     *
     * @param s The scalar to multiply by.
     * @return A new Scalar representing the product.
     */
    Scalar mul(Scalar s);

    /**
     * Returns the negation of this scalar.
     *
     * @return A new Scalar representing the negated value.
     */
    Scalar neg();

    /**
     * Raises this scalar to the power of a given exponent.
     *
     * @param exponent A non-negative integer representing the exponent.
     * @return A new Scalar representing the result of the exponentiation.
     */
    Scalar power(int exponent);

    /**
     * Returns the mathematical sign of this scalar.
     *
     * @return 1 if positive, -1 if negative, or 0 if zero.
     */
    int sign();

    // --- Double Dispatch Methods for Addition ---

    /**
     * Double dispatch method for adding an IntegerScalar.
     *
     * @param s The IntegerScalar to add.
     * @return A new Scalar representing the sum.
     */
    Scalar addInteger(IntegerScalar s);

    /**
     * Double dispatch method for adding a RationalScalar.
     *
     * @param s The RationalScalar to add.
     * @return A new Scalar representing the sum.
     */
    Scalar addRational(RationalScalar s);

    /**
     * Double dispatch method for adding a RealScalar.
     *
     * @param s The RealScalar to add.
     * @return A new Scalar representing the sum.
     */
    Scalar addReal(RealScalar s);

    // --- Double Dispatch Methods for Multiplication ---

    /**
     * Double dispatch method for multiplying by an IntegerScalar.
     *
     * @param s The IntegerScalar to multiply by.
     * @return A new Scalar representing the product.
     */
    Scalar mulInteger(IntegerScalar s);

    /**
     * Double dispatch method for multiplying by a RationalScalar.
     *
     * @param s The RationalScalar to multiply by.
     * @return A new Scalar representing the product.
     */
    Scalar mulRational(RationalScalar s);

    /**
     * Double dispatch method for multiplying by a RealScalar.
     *
     * @param s The RealScalar to multiply by.
     * @return A new Scalar representing the product.
     */
    Scalar mulReal(RealScalar s);

    // --- Standard Overrides ---

    /**
     * Compares this scalar to another object for equality.
     *
     * @param o The object to compare with.
     * @return true if the objects are mathematically equal, false otherwise.
     */
    @Override
    boolean equals(Object o);

    /**
     * Returns the string representation of this scalar.
     *
     * @return The formatted string of the scalar value.
     */
    @Override
    String toString();
}