package proeend.hittable;

import proeend.math.Vector;
import proeend.misc.HitRecord;
import proeend.math.Interval;
import proeend.math.Ray;

public abstract class Hittable {
    public abstract boolean hit(Ray ray, Interval rayT, HitRecord rec);

    public double pdfValue(Vector origin, Vector direction) {
        return 0;
    }

    public Vector random(Vector origin) {
        return new Vector(1,0,0);
    }
}
