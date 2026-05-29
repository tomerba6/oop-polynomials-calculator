    package calculator.nomials;

    import calculator.scalars.IntegerScalar;
    import calculator.scalars.Scalar;

    public class Monomial {
        private int exponent;
        private Scalar coefficient;

        public Monomial(int exponent, Scalar coefficient) {
            this.exponent = exponent;
            this.coefficient = coefficient;
        }

         public Monomial add (Monomial m){
            if (this.exponent == m.exponent) {
                return new Monomial(this.exponent, this.coefficient.add(m.coefficient));
            }

            //same exponent, otherwise null
            return null;
         }

         public Monomial mul (Monomial m){
            return new Monomial(this.exponent + m.exponent, this.coefficient.mul(m.coefficient));
         }

         public Scalar evaluate (Scalar s){
            if (this.exponent == 0) {
                return this.coefficient;
            }

            Scalar result = s.power(this.exponent);
            result = result.mul(this.coefficient);

            return result;
         }

         public Monomial derivative(){
             if (this.exponent == 0) {
                 return new Monomial(0, new IntegerScalar(0));
             }
             return new Monomial(this.exponent - 1, this.coefficient.mul(new IntegerScalar(this.exponent)));
         }

         public int sign(){
            return this.coefficient.sign();
         }

         @Override
         public boolean equals(Object o){
             if (this == o) return true;

             if (!(o instanceof Monomial other)) return false;

             if (this.sign() == 0 && other.sign() == 0) {
                 return true;
             }

             return this.coefficient.equals(other.coefficient) && this.exponent == other.exponent;
         }

         @Override
         public String toString(){

            if (this.coefficient.sign() == 0) {
                return "0";
            }

             String coeffStr = this.coefficient.toString();

            if (this.exponent == 0){
                return coeffStr;
            }

             String prefix = coeffStr;
             if (coeffStr.equals("1")) {
                 prefix = "";
             } else if (coeffStr.equals("-1")) {
                 prefix = "-";
             }

             String variable;
             if (this.exponent == 1){
                 variable = "x";
             }
             else {
                 variable = "x^" + this.exponent;
             }

             return prefix + variable;
         }


        public int getExponent() {
            return this.exponent;
        }
    }
