package proeend.math;

import proeend.hittable.Hittable;

import java.util.Comparator;

public class BoxZCompare implements Comparator<Hittable> {
    @Override
    public int compare(Hittable a, Hittable b) {
        double z1 = a.getBoundingbox().axis(2).getMin();
        double z2 = b.getBoundingbox().axis(2).getMax();
        return Double.compare(z1, z2);
    }
}
