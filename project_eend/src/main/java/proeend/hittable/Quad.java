package proeend.hittable;

import proeend.hittable.BoundingBox;
import proeend.material.Material;
import proeend.material.Normal;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.HitRecord;
import java.lang.Math.*;

import java.util.Objects;

public class Quad extends Hittable {
    private final Vector Q; //point3D
    private final Vector u;
    private final Vector v;
    private final Material mat;
    private BoundingBox bbox;
    private Vector normal;
    private double D;
    private Vector w;

    public Quad(Vector _Q, Vector _u, Vector _v, Material m) {
        Q = _Q;
        u = _u;
        v = _v;
        mat = m;

        Vector n = Vector.cross(u,v);
        normal = Vector.unitVector(n);
        D = Vector.dot(normal, Q);
        w = n.scale(1.0 / Vector.dot(n,n));

        setBoundingBox();
    }

        public void setBoundingBox() {
        bbox = new BoundingBox(Q, Q.add(u).add(v)).pad();
    }


    @Override
    public BoundingBox getBoundingbox() {
        return bbox;
    }

    @Override
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        //return false; // To be implemented
        double denom = Vector.dot(normal,r.getDirection());

        if (Math.abs(denom) < 1e-8){
            return false;
        }

        double t = (D - Vector.dot(normal, r.origin())) / denom;
        if (t < rayT.getMin() || t > rayT.getMax()){
            return false;
        }

        Vector intersection = r.at(t);
        Vector planarHitPtVector = intersection.add(Q.invert());
        double alpha = Vector.dot(w, Vector.cross(planarHitPtVector, v));
        double beta = Vector.dot(w, Vector.cross(u, planarHitPtVector));

        if (!isInterior(alpha ,beta ,rec)){
            return false;
        }

        rec.setT(t);
        rec.setP(intersection);
        rec.setMaterial(mat);
        rec.setFaceNormal(r, normal);

        return true;

    }

    // You may want to implement hashCode() and equals() for this class

    public boolean isInterior(double a, double b, HitRecord rec){
        if (a < 0 || 1 < a || b < 0 || 1 < b){
            return false;
        }
        rec.setU(a);
        rec.setV(b);
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Q, u, v, mat, bbox);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quad quad = (Quad) obj;
        return Q.equals(quad.Q) && u.equals(quad.u) && v.equals(quad.v) &&
                mat.equals(quad.mat) && bbox.equals(quad.bbox);
    }
}
