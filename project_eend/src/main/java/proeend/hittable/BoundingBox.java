package proeend.hittable;

import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;

public class BoundingBox {
    Interval x, y, z;
    private double delta = 0.0001;
    public BoundingBox(){}

    // Constructor voor het behandelen van twee punten als extrema voor de bounding box
    public BoundingBox(Vector a, Vector b) {
        double minX = Math.min(a.getX(), b.getX());
        double minY = Math.min(a.getY(), b.getY());
        double minZ = Math.min(a.getZ(), b.getZ());

        double maxX = Math.max(a.getX(), b.getX());
        double maxY = Math.max(a.getY(), b.getY());
        double maxZ = Math.max(a.getZ(), b.getZ());

        x = new Interval(minX, maxX);
        y = new Interval(minY, maxY);
        z = new Interval(minZ, maxZ);
    }

    // Constructor voor het combineren van twee bestaande bounding boxes
    public BoundingBox(BoundingBox box0, BoundingBox box1) {
        if (box0 != null && box1 != null) {
            x = new Interval().merge(box0.x, box1.x);
            y = new Interval().merge(box0.y, box1.y);
            z = new Interval().merge(box0.z, box1.z);
        }
    }

    public BoundingBox(Interval ix, Interval iy, Interval iz) {
        this.x = ix;
        this.y = iy;
        this.z = iz;
    }

     public Interval axis(int n) {
        if (n == 1) return y;
        if (n == 2) return z;
        return x;
    }

    public BoundingBox pad() {
        // geef een Bounding box terug die net iets groter is dan
        Interval new_x = (x.getSize() >= delta) ? x : x.expand(delta);
        Interval new_y = (y.getSize() >= delta) ? y : y.expand(delta);
        Interval new_z = (z.getSize() >= delta) ? z : z.expand(delta);

        return new BoundingBox(new_x, new_y, new_z);
    }

    public BoundingBox(Vector center, double radius) {

        Vector min = new Vector(
                center.getX() - radius,
                center.getY() - radius,
                center.getZ() - radius
        );

        Vector max = new Vector(
                center.getX() + radius,
                center.getY() + radius,
                center.getZ() + radius
        );
        x = new Interval(min.getX(), max.getX());
        y = new Interval(min.getY(), max.getY());
        z = new Interval(min.getZ(), max.getZ());
    }

    public boolean hit(Ray r, Interval rayT) {

        for (int as = 0; as < 3; as++) {

            double invD = 1.0 / r.getDirection().axis(as);
            double origin = r.origin().axis(as);

            double t0 = (axis(as).getMin() - origin) * invD;
            double t1 = (axis(as).getMax() - origin) * invD;

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



