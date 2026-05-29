package calculator.scalars;

public class IntegerScalar implements Scalar {
    private int number;

    public IntegerScalar(int number) {
        this.number = number;
    }



    public Scalar add(Scalar s) {
        return s.addInteger(this);
    }

    public Scalar mul(Scalar s) {
        return s.mulInteger(this);
    }

    public Scalar neg() {
        return new IntegerScalar(-this.number);
    }

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

    public int sign() {

        if (this.number > 0) {
            return 1;
        }
        else if (this.number < 0) {
            return -1;
        }

        return 0;
    }

     public Scalar addInteger(IntegerScalar s) {
        return new IntegerScalar(this.number + s.number);
    }

    public Scalar addRational(RationalScalar s) {
        return s.addInteger(this);
    }

    public Scalar mulInteger(IntegerScalar s) {
        return new IntegerScalar(this.number * s.number);
    }

    public Scalar mulRational(RationalScalar s) {
        return s.mulInteger(this);
    }

    @Override
    public Scalar addReal(RealScalar s) {
        return s.addInteger(this);
    }

    @Override
    public Scalar mulReal(RealScalar s) {
        return s.mulInteger(this);
    }

    public int getNumber() {
        return this.number;
    }

    @Override
    public String toString() {
        return String.valueOf(this.number);
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
