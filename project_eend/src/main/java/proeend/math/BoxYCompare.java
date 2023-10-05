package proeend.math;

import proeend.hittable.Hittable;

import java.util.Comparator;

public class BoxYCompare implements Comparator<Hittable> {
    @Override
    public int compare(Hittable a, Hittable b) {
        double y1 = a.getBoundingbox().getMin().getAll()[1];
        double y2 = b.getBoundingbox().getMin().getAll()[1];
        return Double.compare(y1, y2);
    }
}

