package proeend.material.pdf;

import proeend.math.Vector;

public abstract class PDF {
    abstract double value(Vector direction);
    abstract Vector generate();
}
