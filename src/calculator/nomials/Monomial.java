package calculator.nomials;

import calculator.scalars.IntegerScalar;
import calculator.scalars.Scalar;

/**
 * Represents a single polynomial term.
 * <p>
 * A monomial consists of an abstract scalar coefficient and a non-negative integer exponent.
 * It provides basic algebraic operations, evaluation, and differentiation for single terms.
 * </p>
 */
public class Monomial {

    /**
     * The non-negative integer exponent of the monomial.
     */
    private int exponent;

    /**
     * The scalar coefficient of the monomial.
     */
    private Scalar coefficient;

    /**
     * Constructs a new Monomial with the specified exponent and coefficient.
     *
     * @param exponent    The non-negative integer exponent.
     * @param coefficient The scalar coefficient.
     */
    public Monomial(int exponent, Scalar coefficient) {
        this.exponent = exponent;
        this.coefficient = coefficient;
    }

    /**
     * Adds another monomial to this one.
     * <p>
     * Note: Addition is only mathematically valid to combine into a single term
     * if both monomials share the exact same exponent.
     * </p>
     *
     * @param m The monomial to add.
     * @return A new Monomial representing the sum if exponents match, otherwise null.
     */
    public Monomial add(Monomial m) {
        if (this.exponent == m.exponent) {
            return new Monomial(this.exponent, this.coefficient.add(m.coefficient));
        }
        // Exponents do not match; cannot be combined into a single monomial
        return null;
    }

    /**
     * Multiplies this monomial by another monomial.
     * <p>
     * The resulting monomial has a coefficient equal to the product of the two
     * coefficients, and an exponent equal to the sum of the two exponents.
     * </p>
     *
     * @param m The monomial to multiply by.
     * @return A new Monomial representing the product.
     */
    public Monomial mul(Monomial m) {
        return new Monomial(this.exponent + m.exponent, this.coefficient.mul(m.coefficient));
    }

    /**
     * Evaluates this monomial for a given scalar value of 'x'.
     *
     * @param s The scalar value to substitute for the variable.
     * @return A new Scalar representing the evaluated result.
     */
    public Scalar evaluate(Scalar s) {
        if (this.exponent == 0) {
            return this.coefficient;
        }

        Scalar result = s.power(this.exponent);
        result = result.mul(this.coefficient);

        return result;
    }

    /**
     * Computes the mathematical derivative of this monomial.
     * <p>
     * Applies the standard power rule: the new coefficient becomes the original
     * coefficient multiplied by the original exponent, and the new exponent
     * is reduced by 1. Constants (exponent 0) derive to 0.
     * </p>
     *
     * @return A new Monomial representing the derivative.
     */
    public Monomial derivative() {
        if (this.exponent == 0) {
            return new Monomial(0, new IntegerScalar(0));
        }
        return new Monomial(this.exponent - 1, this.coefficient.mul(new IntegerScalar(this.exponent)));
    }

    /**
     * Returns the mathematical sign of this monomial's coefficient.
     *
     * @return 1 if positive, -1 if negative, or 0 if zero.
     */
    public int sign() {
        return this.coefficient.sign();
    }

    /**
     * Compares this monomial to another object for equality.
     * <p>
     * Two monomials are considered equal if their coefficients evaluate to zero
     * (regardless of exponent), or if both their coefficients and exponents match.
     * </p>
     *
     * @param o The object to compare with.
     * @return true if mathematically equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Monomial other)) return false;

        if (this.sign() == 0 && other.sign() == 0) {
            return true;
        }

        return this.coefficient.equals(other.coefficient) && this.exponent == other.exponent;
    }

    /**
     * Returns the string representation of this monomial.
     * <p>
     * Automatically handles formatting edge cases such as omitting the coefficient
     * when it is 1 or -1, omitting the variable when the exponent is 0, and
     * cleanly formatting terms that equal 0.
     * </p>
     *
     * @return The formatted string of the monomial.
     */
    @Override
    public String toString() {
        if (this.coefficient.sign() == 0) {
            return "0";
        }

        String coeffStr = this.coefficient.toString();

        if (this.exponent == 0) {
            return coeffStr;
        }

        String prefix = coeffStr;
        if (coeffStr.equals("1")) {
            prefix = "";
        } else if (coeffStr.equals("-1")) {
            prefix = "-";
        }

        String variable;
        if (this.exponent == 1) {
            variable = "x";
        } else {
            variable = "x^" + this.exponent;
        }

        return prefix + variable;
    }

    /**
     * Retrieves the exponent of this monomial.
     *
     * @return The non-negative integer exponent.
     */
    public int getExponent() {
        return this.exponent;
    }
}