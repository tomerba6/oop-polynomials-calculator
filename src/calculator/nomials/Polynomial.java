package calculator.nomials;

import calculator.scalars.IntegerScalar;
import calculator.scalars.RationalScalar;
import calculator.scalars.RealScalar;
import calculator.scalars.Scalar;

import java.util.*;

public class Polynomial {
    private Collection<Monomial> monomials;

    private Polynomial() {
        monomials = new ArrayList<>();
    }

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

    public Polynomial add(Polynomial p){

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
    public Polynomial mul(Polynomial p){
        Polynomial result = new Polynomial();

        for (Monomial m1 : p.monomials) {
            for (Monomial m2 : this.monomials) {
                result.addMonomial(m2.mul(m1));
            }
        }

        result.sortMonomials();
        return result;
    }

    public Scalar evaluate(Scalar s){
        Scalar sum = new IntegerScalar(0);

        for (Monomial m : this.monomials) {
            sum = sum.add(m.evaluate(s));
        }

        return sum;
    }

    public Polynomial derivative(){
        Polynomial result = new Polynomial();

        for (Monomial m : this.monomials) {
            result.addMonomial(m.derivative());
        }

        return result;
    }


    /**
     * @param m new Monomial to add to the Collection
     * @see #build(String)
     * @see #add(Polynomial)
     * @see #mul(Polynomial)
     * @see #mul(Polynomial)
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
     * Helper function for sorting the Collection of monomials
     *
     * @see #monomials
     * @see #add(Polynomial)
     * @see #mul(Polynomial)
     */
    private void sortMonomials() {
        List<Monomial> tempList = new ArrayList<>(this.monomials);

        tempList.sort((m1, m2) -> Integer.compare(m1.getExponent(), m2.getExponent()));

        this.monomials.clear();
        this.monomials.addAll(tempList);
    }

    @Override
    public boolean equals(Object o){
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

    @Override
    public String toString(){
        if (this.monomials.isEmpty()){
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
