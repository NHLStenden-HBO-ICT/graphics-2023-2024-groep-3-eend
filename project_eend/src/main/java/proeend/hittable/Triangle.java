package proeend.hittable;

import proeend.material.Material;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.misc.HitRecord;

public class Triangle extends Hittable{
    public Triangle(Vector v0, Vector v1, Vector v2, Material material) {
        this.v1 = v1;
        this.v2 = v2;
        this.v0 = v0;
        this.material = material;
    }
    public Vector v0,v1,v2;
    public Material material;
    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {
        Vector v0v1 = Vector.add(v1, Vector.inverse(v0));
        Vector v0v2 = Vector.add(v2, Vector.inverse(v0));   //deze twee kloppen
        Vector unitDir = Vector.unitVector(ray.direction());
        //Vector pvec = Vector.cross(Vector.unitVector(ray.direction()), v0v2);
        Vector pvec = Vector.cross(unitDir, v0v2);
        double det = Vector.dot(v0v1, pvec);
        if (det<0) {
            return false;
        }
        //determinant lijkt niet goed te zijn
        //System.out.println(det);
        double invDet = 1/det;

        Vector tvec = Vector.add(ray.origin(), Vector.inverse(v0));
        rec.u = Vector.dot(tvec,pvec)*invDet;
        if (rec.u < 0 || rec.u > 1) return false;

        Vector qvec = Vector.cross(tvec, v0v1);
        rec.v = Vector.dot(unitDir, qvec)*invDet;
        if (rec.v < 0 || rec.u + rec.v > 1) return false;

        rec.t = Vector.dot(v0v2, qvec)*invDet;

        //if (rec.t < 0) {return false;}
        //rec.t = Vector.dot(v0v2, qvec);
        rec.material = material;
        //ray.direction = unitDir;
        rec.p = Vector.add(ray.origin(), Vector.scale(rec.t, unitDir));
        //rec.p = ray.at(rec.t);

        //rec.normal = Vector.cross(v0v1,v0v2);
        rec.normal = Vector.unitVector(Vector.cross(v0v1,v0v2));



        //rec.setFaceNormal(ray,rec.normal);
        //System.out.println(v+u);
        //rec.normal = pvec;
        return true;
    }
}
