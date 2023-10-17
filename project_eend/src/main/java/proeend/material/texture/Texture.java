package proeend.material.texture;

import proeend.math.Vector;

public abstract class Texture {
    public abstract Vector value(double u, double v, Vector p);
}
