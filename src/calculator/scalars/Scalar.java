package calculator.scalars;

public interface Scalar {
    Scalar add(Scalar s);

    Scalar mul(Scalar s);

    Scalar neg();

    Scalar power(int exponent); //exponent >=0

    int sign(); //1, -1 or 0


    //Added four methods to execute the Double Dispatch design

    Scalar addInteger(IntegerScalar s);

    Scalar addRational(RationalScalar s);

    Scalar mulInteger(IntegerScalar s);

    Scalar mulRational(RationalScalar s);

    //Added two more methods to support RealScalar
    Scalar addReal(RealScalar s);

    Scalar mulReal(RealScalar s);

    @Override
    boolean equals(Object o);

    @Override
    String toString();


}
