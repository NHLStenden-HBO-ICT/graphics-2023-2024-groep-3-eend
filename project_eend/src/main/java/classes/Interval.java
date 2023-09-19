package classes;

public class Interval {
    public double min;
    public double max;
    public Interval() {min = Double.NEGATIVE_INFINITY; max = Double.POSITIVE_INFINITY;}
    public Interval(double min, double max){this.min=min;this.max=max;}

    public boolean contains(double x) {
        return min <= x && x <= max;    }
    public boolean surrounds(double x) {
        return min < x && x < max;    }


    public double clamp(double x) {
        if (x < min) {return min;}
        if (x > max) {return max;}
        return x;
    }
    static Interval empty = new Interval(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
    static Interval universe = new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
}
