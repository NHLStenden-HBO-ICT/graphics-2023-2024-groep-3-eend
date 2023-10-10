package proeend.material;

import proeend.math.Ray;
import proeend.math.Vector;
import proeend.misc.HitRecord;

public class Normal extends Material{
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, Vector attenuation, Ray scattered) {
        return false;
    }
}
