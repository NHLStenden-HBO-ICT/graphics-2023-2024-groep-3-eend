package proeend.hittable;

import proeend.material.Lambertian;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.misc.HitRecord;

public class BoundingBox {

    public static Vector min, max;
    Interval x, y, z;
    private double delta = 0.0001;

    public BoundingBox(){}

    // Constructor voor het behandelen van twee punten als extrema voor de bounding box
    public BoundingBox(Vector a, Vector b) {
        // Gebruik de functies fmin en fmax om de minimum- en maximumcoördinaten te bepalen
        double minX = Math.min(a.x(), b.x());
        double minY = Math.min(a.y(), b.y());
        double minZ = Math.min(a.z(), b.z());

        double maxX = Math.max(a.x(), b.x());
        double maxY = Math.max(a.y(), b.y());
        double maxZ = Math.max(a.z(), b.z());

        x = new Interval(minX, maxX);
        y = new Interval(minY, maxY);
        z = new Interval(minZ, maxZ);
    }

    // Constructor voor het combineren van twee bestaande bounding boxes
    public BoundingBox(BoundingBox box0, BoundingBox box1) {
        if (box1 != null && box0 != null){
            x = new Interval(box0.x.getMin(), box1.x.getMax());
            y = new Interval(box0.y.getMin(), box1.y.getMax());
            z = new Interval(box0.z.getMin(), box1.z.getMax());
        }
    }

    public BoundingBox(Interval ix, Interval iy, Interval iz) {
        this.x = ix;
        this.y = iy;
        this.z = iz;
    }

    public BoundingBox pad() {
        // geef een Bounding box terug die net iets groter is dan
        Interval new_x = (x.getSize() >= delta) ? x : x.expand(delta);
        Interval new_y = (y.getSize() >= delta) ? y : y.expand(delta);
        Interval new_z = (z.getSize() >= delta) ? z : z.expand(delta);

        return new BoundingBox(new_x, new_y, new_z);
    }

    public static Vector getMin() {
        return min;
    }

    public static Vector getMax() {
        return max;
    }

    public BoundingBox(Vector center, double radius) {

        min = new Vector(
                center.x() - radius,
                center.y() + radius,
                center.z() - radius
        );

        max = new Vector(
                center.x() + radius,
                center.y() - radius,
                center.z() + radius
        );
        x = new Interval(min.x(), max.x());
        y = new Interval(min.y(), max.y());
        z = new Interval(min.z(), max.z());
    }

    public boolean hit(Ray r, Interval rayT) {

        for (int axis = 0; axis < 3; axis++) {
            double invD = 1.0 / r.direction().getAll()[axis];
            double origin = r.origin().getAll()[axis];

            double t0 = (min.getAll()[axis] - origin) * invD;
            double t1 = (max.getAll()[axis] - origin) * invD;

            if (invD < 0.0) {
                double temp = t0;
                t0 = t1;
                t1 = temp;
            }

            if(t0 > rayT.getMin()) rayT.setMin(t0);
            if(t1 < rayT.getMax()) rayT.setMax(t1);

            if(rayT.getMax() <= rayT.getMin()) return false;

        }

        return true;
    }

}



