package proeend.hittable;

import misc.HitRecord;
import proeend.math.Interval;
import proeend.math.Ray;

public abstract class Hittable {
    public abstract boolean hit(Ray ray, Interval rayT, HitRecord rec);
}
