package proeend.hittable;

import proeend.math.Interval;
import proeend.math.Ray;
import proeend.misc.HitRecord;

public class Quad extends Hittable{
    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {
        return false;
    }
}
