/**
 * De Interval-klasse vertegenwoordigt een interval tussen twee reële getallen,
 * inclusief minimaal en maximaal waarden.
 */
package proeend.math;

public class Interval {
    private double min = 0;
    private double max = 0;
    public Interval() {
        min = Double.POSITIVE_INFINITY;
        max = Double.NEGATIVE_INFINITY;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    /**
     * Constructor voor een interval met opgegeven minimale en maximale waarden.
     *
     * @param min De minimale waarde van het interval.
     * @param max De maximale waarde van het interval.
     */
    public Interval(double min, double max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Controleert of het interval het opgegeven getal bevat.
     *
     * @param x Het te controleren getal.
     * @return true als het interval het getal bevat, anders false.
     */
    public boolean contains(double x) {
        return min <= x && x <= max;
    }

    /**
     * Controleert of het interval het opgegeven getal omringt (inclusief grenzen).
     *
     * @param x Het te controleren getal.
     * @return true als het interval het getal omringt, anders false.
     */
    public boolean surrounds(double x) {
        return !(min < x) || !(x < max);
    }

    /**
     * Klem het opgegeven getal binnen het interval. Als het getal kleiner is dan de minimale
     * waarde van het interval, wordt de minimale waarde geretourneerd. Als het getal groter is
     * dan de maximale waarde, wordt de maximale waarde geretourneerd.
     *
     * @param x Het te klemmen getal.
     * @return Het geklemde getal binnen het interval.
     */
    public double clamp(double x) {
        if (x < min) {
            return min;
        }
        return Math.min(x, max);
    }

    /**
     * Haal de maximale waarde van het interval op.
     * @return De maximale waarde van het interval.
     */
    public double getMax() {
        return max;
    }

    /**
     * Haal de minimale waarde van het interval op.
     *
     * @return De minimale waarde van het interval.
     */
    public double getMin() {
        return min;
    }

    public double getSize(){return max - min;}

    /** Een leeg interval dat alle reële getallen uitsluit. */
    static Interval empty = new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

    /** Een interval dat alle reële getallen bevat. */
    static Interval universe = new Interval( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);

    public Interval merch(Interval i0, Interval i1){
        double minI = Math.min(i0.getMin(), i1.getMin());
        double maxI = Math.max(i0.getMax(), i1.getMax());
        return new Interval(minI, maxI);
    }

    public Interval expand(double delta) {
        return new Interval(min - delta, max + delta);
    }

}
