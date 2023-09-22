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
        Vector pvec = Vector.cross(Vector.unitVector(ray.direction()), v0v2);
        double det = Vector.dot(v0v1, pvec);
        if (det<0) {
            return false;
        }
        //determinant lijkt niet goed te zijn
        //System.out.println(det);
        double invDet = 1/det;

        Vector tvec = Vector.add(ray.origin(), Vector.inverse(v0));
        double u = Vector.dot(tvec,pvec)*invDet;
        if (u < 0 || u > 1) return false;

        Vector qvec = Vector.cross(tvec, v0v1);
        double v = Vector.dot(ray.direction(), qvec)*invDet;
        if (v < 0 || u + v > 1) return false;

        rec.t = Vector.dot(v0v2, qvec)*invDet;
        rec.material = material;
        rec.p = Vector.add(ray.at(rec.t), ray.origin());
        //System.out.println(rec.p.x()+" "+rec.p.y() + " " + rec.p.z());
        rec.normal = Vector.unitVector(Vector.cross(v0v1,v0v2));
        //System.out.println(v+u);
        //rec.normal = pvec;
        return true;
    }
}
