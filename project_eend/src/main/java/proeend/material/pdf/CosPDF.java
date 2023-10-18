package proeend.material.pdf;

import proeend.math.OrthonormalBase;
import proeend.math.Vector;

public class CosPDF extends PDF {
    private final OrthonormalBase uvw = new OrthonormalBase();
    public CosPDF(Vector w) {uvw.buildFromW(w);}
    @Override
    public double value(Vector direction) {
        double cosTheta = Vector.dot(Vector.unitVector(direction),uvw.w());
        return Math.max(0, cosTheta/Math.PI);
    }

    @Override
    public Vector generate() {
        return uvw.local(randomCosineDirection());
    }

    public static Vector randomCosineDirection() {
        double r1 = Math.random();
        double r2 = Math.random();

        double fi = 2*Math.PI*r1;
        double x = Math.cos(fi)*Math.sqrt(r2);
        double y = Math.sin(fi)*Math.sqrt(r2);
        double z = Math.sqrt(1-r2);

        return new Vector(x,y,z);
    }
}
