package proeend.hittable;

import proeend.misc.HitRecord;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.material.Material;

public class Sphere extends Hittable{

    private final double radius;
    private final Vector center;
    public Sphere(Vector center, double radius, Material material) {
        this.center = center;
        this. radius = radius;
        super.setMaterial(material);
    }
    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {

        Vector OC = Vector.add(ray.origin(), Vector.inverse(center));
        double a = Vector.lengthSquared(ray.direction());
        double halfb = Vector.dot(OC, ray.direction());
        double c = Vector.lengthSquared(OC) - radius * radius;
        double D = halfb * halfb - a * c;
        if (D < 0) {
            return false;
        }

        double sqrtD = Math.sqrt(D);
        double root = ((-halfb - sqrtD) / a);
        if (!rayT.surrounds(root)) {
            root = ((-halfb - sqrtD) / a);
            if (!rayT.surrounds(root)) {
                return false;
            }
        }
        rec.setT(root);
        rec.setP(ray.at(rec.getT()));
        rec.setMaterial(getMaterial());
        Vector outwardNormal = Vector.scale((1.0 / radius), Vector.add(rec.getP(), Vector.inverse(center)));
        rec.setFaceNormal(ray, outwardNormal);
        return true;
    }
}
