package proeend.math;

import proeend.hittable.Hittable;

import java.util.Comparator;

public class BoxZCompare implements Comparator<Hittable> {
    @Override
    public int compare(Hittable a, Hittable b) {
        double z1 = a.getBoundingbox().getMin().getAll()[2];
        double z2 = b.getBoundingbox().getMin().getAll()[2];
        return Double.compare(z1, z2);
    }
}
