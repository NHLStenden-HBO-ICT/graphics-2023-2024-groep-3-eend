package proeend.math;

import proeend.hittable.Hittable;

import java.util.Comparator;

public class BoxYCompare implements Comparator<Hittable> {
    @Override
    public int compare(Hittable a, Hittable b) {
        double y1 = a.getBoundingbox().axis(1).getMin();
        double y2 = b.getBoundingbox().axis(1).getMax();
        return Double.compare(y1, y2);
    }
}

