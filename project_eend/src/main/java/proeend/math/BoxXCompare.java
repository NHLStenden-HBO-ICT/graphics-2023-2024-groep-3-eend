package proeend.math;

import proeend.hittable.Hittable;

import java.util.Comparator;

public class BoxXCompare implements Comparator<Hittable> {
    @Override
    public int compare(Hittable a, Hittable b) {
        double x1 = a.getBoundingbox().getMin().getAll()[0];
        double x2 = b.getBoundingbox().getMin().getAll()[0];
        return Double.compare(x1, x2);
    }
}
