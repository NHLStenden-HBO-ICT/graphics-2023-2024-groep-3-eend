package proeend.math;

import proeend.hittable.Hittable;

import java.util.Comparator;

public class BoxXCompare implements Comparator<Hittable> {
    @Override
    public int compare(Hittable a, Hittable b) {
        double x1 = a.getBoundingbox().axis(0).getMin();
        double x2 = b.getBoundingbox().axis(0).getMax();
        return Double.compare(x1, x2);
    }
}
