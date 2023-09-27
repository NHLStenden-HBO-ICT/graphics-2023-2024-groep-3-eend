package proeend.material.pdf;

import proeend.math.Vector;

public abstract class pdf {
    abstract double value(Vector direction);
    abstract Vector generate();
}
