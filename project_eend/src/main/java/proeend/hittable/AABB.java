package proeend.hittable;

import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;

public class AABB {
    public Interval x, y, z;

    Interval aabb = new Interval();



    public AABB() {
        // The default AABB is empty, since intervals are empty by default.
    }

    public AABB(Interval ix, Interval iy, Interval iz) {
        this.x = ix;
        this.y = iy;
        this.z = iz;
    }

    public AABB(Vector a, Vector b) {
        // Treat the two points a and b as extrema for the bounding box,
        // so we don't require a particular minimum/maximum coordinate order.
        this.x = new Interval(Math.min(a.x(), b.x()), Math.max(a.x(), b.x()));
        this.y = new Interval(Math.min(a.y(), b.y()), Math.max(a.y(), b.y()));
        this.z = new Interval(Math.min(a.z(), b.z()), Math.max(a.z(), b.z()));
    }

    public AABB(AABB box0, AABB box1) {
        this.x = new Interval(box0.x.getMin(), box1.x.getMax());
        this.y = new Interval(box0.y.getMin(), box1.y.getMax());
        this.z = new Interval(box0.z.getMin(), box1.z.getMax());
    }

    public AABB pad() {
        // Return an AABB that has no side narrower than some delta, padding if necessary.
        double delta = 0.0001;
        Interval new_x = (x.size() >= delta) ? x : x.expand(delta);
        Interval new_y = (y.size() >= delta) ? y : y.expand(delta);
        Interval new_z = (z.size() >= delta) ? z : z.expand(delta);

        return new AABB(new_x, new_y, new_z);
    }

    public Interval axis(int n) {
        if (n == 1) return y;
        if (n == 2) return z;
        return x;
    }

    public boolean hit(Ray ray, Interval rayT) {
        for (int a = 0; a < 3; a++) {
            double invD = 1 / ray.direction().getAll()[a];
            double orig = ray.origin().getAll()[a];

            double t0 = (axis(a).min - orig) * invD;
            double t1 = (axis(a).max - orig) * invD;

            if (invD < 0) {
                double temp = t0;
                t0 = t1;
                t1 = temp;
            }

            if (t0 > rayT.min) rayT.min = t0;
            if (t1 < rayT.max) rayT.max = t1;

            if (rayT.max <= rayT.min)
                return false;
        }
        return true;
    }

    public static AABB add(AABB bbox, Vector offset) {
        return new AABB(bbox.x.add(offset.x()), bbox.y.add(offset.y()), bbox.z.add(offset.z()));
    }

    public static AABB add(Vector offset, AABB bbox) {
        return add(bbox, offset);
    }


}



