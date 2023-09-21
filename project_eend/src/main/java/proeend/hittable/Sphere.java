package proeend.hittable;

import misc.HitRecord;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.material.Material;

public class Sphere extends Hittable{

    private final double radius;
    private final Vector center;
    private final Material material;
    public Sphere(Vector center, double radius, Material material) {
        this.center = center;
        this. radius = radius;
        this.material = material;
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
        rec.t = root;
        rec.p = ray.at(rec.t);
        rec.material = material;
        Vector outwardNormal = Vector.scale((1.0 / radius), Vector.add(rec.p, Vector.inverse(center)));
        rec.setFaceNormal(ray, outwardNormal);
        return true;
    }
}
