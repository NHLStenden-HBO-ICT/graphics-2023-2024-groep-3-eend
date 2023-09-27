package proeend.material.pdf;

import proeend.math.Vector;

public class spherePDF extends pdf{
    @Override
    double value(Vector direction) {
        return 1/(4*Math.PI);
    }

    @Override
    Vector generate() {
        return Vector.randomOnUnitSphere();
    }
}