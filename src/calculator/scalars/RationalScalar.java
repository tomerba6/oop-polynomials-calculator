package calculator.scalars;

public class RationalScalar implements Scalar {

    private int numerator;
    private int denominator;

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

    public Scalar add(Scalar s) {
        return s.addRational(this);
    }

    public Scalar mul(Scalar s) {
        return s.mulRational(this);
    }

    public Scalar neg() {
        RationalScalar tempScalar = new RationalScalar(-numerator, denominator);
        return tempScalar.reduce();
    }

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

    public int sign() {
        return Integer.compare(numerator, 0);
    }

    public Scalar reduce() {
        if (this.denominator == 1) {
            return new IntegerScalar(this.numerator);
        }

        return this;
    }

    /**
     * Helper method for calculating GCD
     *
     * @param a first integer
     * @param b second integer
     * @return Greatest Common Divisor of a and b
     */
    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    public Scalar addInteger(IntegerScalar s) {
        int newNumerator = this.numerator + s.getNumber() * this.denominator;
        return new RationalScalar(newNumerator, this.denominator).reduce();
    }

    public Scalar addRational(RationalScalar s) {
        int newNumerator = this.numerator * s.denominator  + s.numerator * this.denominator;
        int newDenominator = this.denominator * s.denominator;
        RationalScalar newRational = new RationalScalar(newNumerator, newDenominator);
        return newRational.reduce();
    }

    public Scalar mulInteger(IntegerScalar s) {
        int newNumerator = this.numerator * s.getNumber();
        RationalScalar newRational = new RationalScalar(newNumerator, this.denominator);
        return newRational.reduce();
    }

    public Scalar mulRational(RationalScalar s) {
        int newNumerator = this.numerator * s.numerator;
        int newDenominator = this.denominator * s.denominator;
        RationalScalar newRational = new RationalScalar(newNumerator, newDenominator);
        return newRational.reduce();
    }

    @Override
    public Scalar addReal(RealScalar s) {
        return new RealScalar(((double) this.numerator / this.denominator) + s.getNumber());
    }

    @Override
    public Scalar mulReal(RealScalar s) {
        return new  RealScalar(((double) this.numerator / this.denominator) * s.getNumber());
    }

    @Override
    public String toString() {
        if (this.denominator == 1) {
            return String.valueOf(this.numerator);
        }
        return String.format("%d/%d", this.numerator, this.denominator);
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof Scalar other)) return false;

        Scalar negativeOther = other.neg();
        Scalar difference = this.add(negativeOther);

        return difference.sign() == 0;
    }
}
