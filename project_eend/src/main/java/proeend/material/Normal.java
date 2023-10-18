package proeend.material;

import proeend.records.ScatterRecord;
import proeend.math.Ray;
import proeend.records.HitRecord;

/**
 * Bij het materiaal Normaal wordt scatter op false gezet.
 */
public class Normal extends Material{
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, ScatterRecord scatterRecord) {
        return false;
    }
}
