package proeend.material;

import proeend.ScatterRecord;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.misc.HitRecord;

public class Normal extends Material{
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, ScatterRecord scatterRecord) {
        return false;
    }
}
