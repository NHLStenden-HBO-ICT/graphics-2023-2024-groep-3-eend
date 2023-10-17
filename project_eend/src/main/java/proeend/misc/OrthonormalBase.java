package proeend.misc;

import proeend.math.Vector;

public class OrthonormalBase {
    public OrthonormalBase(){};
    private Vector[] axis = new Vector[3];
    public Vector atIndex(int index) {
        return axis[index];
    }
    public Vector u() {return axis[0];}
    public Vector v() {return axis[1];}
    public Vector w() {return axis[2];}
    public Vector local(double a, double b, double c) {
        return Vector.add(Vector.add(Vector.scale(a, u()),
                Vector.scale(b, v())), Vector.scale(c, w()));
    }
    public Vector local(Vector a) {
        return Vector.add(Vector.add(Vector.scale(a.getX(), u()),
                Vector.scale(a.getY(), v())), Vector.scale(a.getZ(), w()));
    }
    public void buildFromW(Vector w) {
        Vector unitW = Vector.unitVector(w);
        Vector a = (Math.abs(unitW.getX()) > .9) ? new Vector(0,1,0) : new Vector(1,0,0);
        Vector v = Vector.unitVector(Vector.cross(unitW,a));
        Vector u = Vector.cross(unitW,v);
        axis[0] = u;
        axis[1] = v;
        axis[2] = w;
    }

}
