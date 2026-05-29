package calculator.nomials;

import calculator.scalars.IntegerScalar;
import calculator.scalars.RationalScalar;
import calculator.scalars.RealScalar;
import calculator.scalars.Scalar;

import java.util.*;

/**
 * Represents a mathematical polynomial.
 * <p>
 * A polynomial is defined as a collection of {@link Monomial}s with distinct exponents.
 * This class provides methods to build polynomials from string inputs, perform
 * algebraic operations (addition, multiplication), evaluate the polynomial for
 * specific scalar values, and compute its derivative.
 * </p>
 */
public class Polynomial {

    /**
     * The collection of distinct monomials that make up this polynomial.
     */
    private Collection<Monomial> monomials;

    /**
     * Private constructor to initialize an empty polynomial.
     */
    private Polynomial() {
        monomials = new ArrayList<>();
    }

    /**
     * Builds a Polynomial from a space-separated string of coefficients.
     * <p>
     * The index of each coefficient in the string corresponds to its exponent
     * (e.g., "1 2 3" becomes 1 + 2x + 3x^2). The method automatically determines
     * the appropriate Scalar type (Rational, Real, or Integer) based on the presence
     * of '/' or '.' characters.
     * </p>
     *
     * @param input The string containing space-separated scalar coefficients.
     * @return A new constructed and sorted Polynomial.
     */
    public static Polynomial build(String input) {
        Polynomial poly = new Polynomial();
        String[] parts = input.trim().split("\\s+");

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.isEmpty()) continue;

            Scalar scalar;
            if (part.contains("/")) {
                String[] fraction = part.split("/");
                scalar = new RationalScalar(
                        Integer.parseInt(fraction[0]),
                        Integer.parseInt(fraction[1])
                );
            } else if (part.contains(".")) {
                scalar = new RealScalar(Double.parseDouble(part));
            } else {
                scalar = new IntegerScalar(Integer.parseInt(part));
            }

            poly.addMonomial(new Monomial(i, scalar));
        }

        poly.sortMonomials();
        return poly;
    }

    /**
     * Adds another polynomial to this polynomial.
     *
     * @param p The polynomial to add.
     * @return A new Polynomial representing the sum.
     */
    public Polynomial add(Polynomial p) {
        Polynomial result = new Polynomial();

        for (Monomial m : this.monomials) {
            result.addMonomial(m);
        }

        for (Monomial m : p.monomials) {
            result.addMonomial(m);
        }

        result.sortMonomials();
        return result;
    }

    /**
     * Multiplies this polynomial by another polynomial.
     * <p>
     * Distributes every monomial in this polynomial across every monomial
     * in the given polynomial.
     * </p>
     *
     * @param p The polynomial to multiply by.
     * @return A new Polynomial representing the product.
     */
    public Polynomial mul(Polynomial p) {
        Polynomial result = new Polynomial();

        for (Monomial m1 : p.monomials) {
            for (Monomial m2 : this.monomials) {
                result.addMonomial(m2.mul(m1));
            }
        }

        result.sortMonomials();
        return result;
    }

    /**
     * Evaluates the polynomial for a given scalar value of 'x'.
     *
     * @param s The scalar value to substitute into the polynomial.
     * @return A new Scalar representing the evaluated total.
     */
    public Scalar evaluate(Scalar s) {
        Scalar sum = new IntegerScalar(0);

        for (Monomial m : this.monomials) {
            sum = sum.add(m.evaluate(s));
        }

        return sum;
    }

    /**
     * Computes the mathematical derivative of this polynomial.
     * <p>
     * Applies the power rule to every monomial within the polynomial.
     * </p>
     *
     * @return A new Polynomial representing the derivative.
     */
    public Polynomial derivative() {
        Polynomial result = new Polynomial();

        for (Monomial m : this.monomials) {
            result.addMonomial(m.derivative());
        }

        return result;
    }

    /**
     * Helper method to safely add a new Monomial to the collection.
     * <p>
     * If a monomial with the same exponent already exists, their coefficients
     * are summed. If the resulting coefficient is zero, the term is removed entirely.
     * </p>
     *
     * @param m The new Monomial to add.
     */
    private void addMonomial(Monomial m) {
        if (m == null || m.sign() == 0) {
            return;
        }

        Iterator<Monomial> it = this.monomials.iterator();

        while (it.hasNext()) {
            Monomial existing = it.next();

            if (existing.getExponent() == m.getExponent()) {
                Monomial sum = existing.add(m);
                it.remove();

                if (sum.sign() != 0) {
                    this.monomials.add(sum);
                }
                return;
            }
        }

        this.monomials.add(m);
    }

    /**
     * Helper method to sort the collection of monomials by their exponents
     * in ascending order.
     */
    private void sortMonomials() {
        List<Monomial> tempList = new ArrayList<>(this.monomials);
        tempList.sort((m1, m2) -> Integer.compare(m1.getExponent(), m2.getExponent()));
        this.monomials.clear();
        this.monomials.addAll(tempList);
    }

    /**
     * Compares this polynomial to another object for equality.
     * <p>
     * Two polynomials are equal if their mathematical difference evaluates to zero.
     * This is verified by subtracting the other polynomial from this one and checking
     * if the resulting monomial collection is empty.
     * </p>
     *
     * @param o The object to compare with.
     * @return true if mathematically equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Polynomial other)) return false;

        Monomial minusOne = new Monomial(0, new IntegerScalar(-1));
        Polynomial negativeOther = new Polynomial();

        for (Monomial m : other.monomials) {
            negativeOther.addMonomial(m.mul(minusOne));
        }

        Polynomial difference = this.add(negativeOther);
        return difference.monomials.isEmpty();
    }

    /**
     * Returns the string representation of this polynomial.
     * <p>
     * Iterates through the sorted monomials and joins them. Automatically formats
     * addition symbols between terms, omitting the '+' if the term is negative.
     * </p>
     *
     * @return The formatted string of the polynomial, or "0" if empty.
     */
    @Override
    public String toString() {
        if (this.monomials.isEmpty()) {
            return "0";
        }

        String result = "";
        boolean isFirst = true;

        for (Monomial m : this.monomials) {
            if (m.sign() > 0 && !isFirst) {
                result = result + "+" + m;
            }
            else {
                result = result + m;
            }
            isFirst = false;
        }

        return result;
    }
}