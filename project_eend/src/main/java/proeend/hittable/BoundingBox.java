package proeend.hittable;

import proeend.material.Lambertian;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.misc.HitRecord;

public class BoundingBox extends Hittable{

    public static Vector min, max, center;
    Interval x, y, z;
    double radius;

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
        if (!(box1 == null && box0 == null)){
            x = new Interval(box0.x.getMin(), box1.x.getMax());
            y = new Interval(box0.y.getMin(), box1.y.getMax());
            z = new Interval(box0.z.getMin(), box1.z.getMax());
        }
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

        this.radius = radius;
        this.center = center;


        this.setMaterial(new Lambertian(new Vector(100,100,100)));
    }


    //TODO axis wordt niet gebruikt?
     public Interval axis(int n) {
        if (n == 1) return y;
        if (n == 2) return z;
        return x;
    }


    @Override
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        double tMax = rayT.getMax();
        double tMin = rayT.getMin();

        for (int axis = 0; axis < 3; axis++) {
            double invD = 1.0 / r.direction().getAll()[axis];
            double t0 = (this.min.getAll()[axis] - r.origin().getAll()[axis]) * invD;
            double t1 = (this.max.getAll()[axis] - r.origin().getAll()[axis]) * invD;

            if (invD < 0.0) {
                double temp = t0;
                t0 = t1;
                t1 = temp;
            }

            tMin = Math.max(t0, tMin);
            tMax = Math.min(t1, tMax);

            if (tMax <= tMin) {
                return false;
            }
        }

        rec.setT(tMin);
        rec.setP(r.at(tMin));

        // Berekent de normaalvector van de hit.
        for (int axis = 0; axis < 3; axis++) {
            if (rec.getP().getAll()[axis] == this.min.getAll()[axis]) {
                Vector normal = new Vector();
                normal.getNormal(axis, -1.0);
                rec.setNormal(normal);
                break;
            }
            if (rec.getP().getAll()[axis] == this.max.getAll()[axis]) {
                Vector normal = new Vector();
                rec.setNormal(normal.getNormal(axis, 1.0));
                break;
            }
        }

        // Stel het materiaal in als dat nodig is.
        rec.setMaterial(getMaterial());

        return true;
    }

}



