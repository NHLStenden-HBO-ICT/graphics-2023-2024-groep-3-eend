package proeend.material.texture;

import proeend.material.Material;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.misc.HitRecord;

public abstract class Texture {
    public abstract Vector value(double u, double v, Vector p);
}
