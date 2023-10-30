/**
 * De Interval-klasse vertegenwoordigt een interval tussen twee reële getallen,
 * inclusief minimaal en maximaal waarden.
 */
package proeend.math;

public class Interval {
    private double min;
    private double max;
    public Interval() {
        min = Double.POSITIVE_INFINITY;
        max = Double.NEGATIVE_INFINITY;
    }

    public void copy(Interval ray) {
        this.min = ray.getMin();
        this.max = ray.getMax();
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    /**
     * Constructor voor een interval met opgegeven minimale en maximale waarden.
     * @param min De minimale waarde van het interval.
     * @param max De maximale waarde van het interval.
     */
    public Interval(double min, double max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Controleert of het interval het opgegeven getal omringt (inclusief grenzen).
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
     * @return De minimale waarde van het interval.
     */
    public double getMin() {
        return min;
    }

    public double getSize(){return max - min;}

    /**
     * Voegt twee intervallen samen.
     * @param i0 Het eerste interval.
     * @param i1 Het tweede interval.
     * @return Het interval dat beide intervallen omvat.
     */
    public Interval merge(Interval i0, Interval i1){
        double minI = Math.min(i0.getMin(), i1.getMin());
        double maxI = Math.max(i0.getMax(), i1.getMax());
        return new Interval(minI, maxI);
    }

    /**
     * Vergroot het interval met behulp van delta.
     * @param delta Hoeveel het interval vergroot moet worden.
     * @return Het vergrootte interval.
     */
    public Interval expand(double delta) {
        return new Interval(min - delta, max + delta);
    }
}
