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
    private Vector v0,v1,v2;
    private Material material;
    private double area;
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
        double u = Vector.dot(tvec,pvec)*invDet;
        if (u < 0 || u > 1) return false;

        Vector qvec = Vector.cross(tvec, v0v1);
        double v = Vector.dot(unitDir, qvec)*invDet;
        if (v < 0 || u + v > 1) return false;

        double t = Vector.dot(v0v2, qvec)*invDet;
        if (t < 0)
            return false;

        //if (rec.t < 0) {return false;}
        //rec.t = Vector.dot(v0v2, qvec);
        //ray.direction = unitDir;
        //rec.p = ray.at(rec.t);

        //rec.normal = Vector.cross(v0v1,v0v2);
        rec.u = u;
        rec.v = v;
        rec.t = t;
        rec.material = material;
        rec.p = Vector.add(ray.origin(), Vector.scale(rec.t, unitDir));
        rec.normal = Vector.unitVector(Vector.cross(v0v1,v0v2));
        rec.setFaceNormal(ray, rec.normal);



        //rec.setFaceNormal(ray,rec.normal);
        //System.out.println(v+u);
        //rec.normal = pvec;
        return true;
    }

    /**
     * deze static method bestaat eigenlijk alleen voor de TriangleMesh hit method, als het nodig is, zou je via
     * deze ook de andere kunnen doen
     * @return of ie raakt of niet
     */
    public static boolean MThit(Ray ray, Interval rayT, HitRecord rec, Vector v0, Vector v1, Vector v2, Material material) {
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
        double u = Vector.dot(tvec,pvec)*invDet;
        if (u < 0 || u > 1) return false;

        Vector qvec = Vector.cross(tvec, v0v1);
        double v = Vector.dot(unitDir, qvec)*invDet;
        if (v < 0 || u + v > 1) return false;

        double t = Vector.dot(v0v2, qvec)*invDet;
        if (t < 0)
            return false;

        //if (rec.t < 0) {return false;}
        //rec.t = Vector.dot(v0v2, qvec);
        //ray.direction = unitDir;
        //rec.p = ray.at(rec.t);

        //rec.normal = Vector.cross(v0v1,v0v2);
        rec.u = u;
        rec.v = v;
        rec.t = t;
        rec.material = material;
        rec.p = Vector.add(ray.origin(), Vector.scale(rec.t, unitDir));
        rec.normal = Vector.unitVector(Vector.cross(v0v1,v0v2));
        rec.setFaceNormal(ray, rec.normal);



        //rec.setFaceNormal(ray,rec.normal);
        //System.out.println(v+u);
        //rec.normal = pvec;
        return true;
    }
    /*
    @Override
    public double pdfValue(Vector origin, Vector direction) {
        //kopie van Quad code, weet nog niet of het werkt
        HitRecord rec = new HitRecord();
        if(!this.hit(new Ray(origin, direction), new Interval(0.001, Double.POSITIVE_INFINITY), rec)); {
            return 0;
        }
        double v = Vector.lengthSquared(direction);
        double distanceSquared = rec.t * rec.t;

        return 0;
    }
    @Override
    public Vector random(Vector origin) {
        return new Vector(1,0,0);
    }
     */

}
