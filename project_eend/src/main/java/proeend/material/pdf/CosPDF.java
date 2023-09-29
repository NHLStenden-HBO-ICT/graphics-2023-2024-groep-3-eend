package proeend.material.pdf;

import proeend.math.Vector;
import proeend.misc.OrthonormalBase;
import proeend.misc.Utility;

public class CosPDF extends PDF {
    private OrthonormalBase uvw = new OrthonormalBase();
    public CosPDF(Vector w) {uvw.buildFromW(w);}
    @Override
    public double value(Vector direction) {
        double cosTheta = Vector.dot(Vector.unitVector(direction),uvw.w());
        return Math.max(0, cosTheta/Math.PI);
    }

    @Override
    public Vector generate() {
        return uvw.local(Utility.randomCosineDirection());
    }
}
