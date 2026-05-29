package calculator.scalars;

public class RealScalar implements Scalar{

    private double number;

    public RealScalar(double number){
        this.number = number;
    }

    @Override
    public Scalar add(Scalar s) {
        return s.addReal(this);
    }

    @Override
    public Scalar mul(Scalar s) {
        return s.mulReal(this);
    }

    @Override
    public Scalar neg() {
        return new RealScalar(-number);
    }

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

    @Override
    public Scalar addInteger(IntegerScalar s) {
        return new RealScalar(s.getNumber() + number);
    }

    @Override
    public Scalar addRational(RationalScalar s) {
        return s.addReal(this);
    }

    @Override
    public Scalar mulInteger(IntegerScalar s) {
        return new RealScalar(s.getNumber() * number);
    }

    @Override
    public Scalar mulRational(RationalScalar s) {
        return s.mulReal(this);
    }

    @Override
    public Scalar addReal(RealScalar s) {
        return new RealScalar(this.number + s.number);
    }

    @Override
    public Scalar mulReal(RealScalar s) {
        return new RealScalar(this.number * s.number);
    }

    public double getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "" + number;
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
