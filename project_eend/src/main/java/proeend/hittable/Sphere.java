package proeend.hittable;

import proeend.Main;
import proeend.misc.HitRecord;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.material.Material;
import proeend.misc.OrthonormalBase;

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
    @Override
    public double pdfValue(Vector origin, Vector direction) {
        HitRecord hitRecord = new HitRecord();
        if (!hit(new Ray(origin,direction),new Interval(0.001, Double.POSITIVE_INFINITY), hitRecord)) {
            return 0;
        }
        double cosThetaMax = Math.sqrt(1-radius*radius/Vector.lengthSquared(Vector.add(center, Vector.inverse(origin))));
        double solidAngle = 2*Math.PI*(1-cosThetaMax);

        return 1.0/solidAngle;
    }
    @Override
    public Vector random(Vector origin) {
        Vector direction = Vector.add(center, Vector.inverse(origin));
        double distanceSquared = Vector.lengthSquared(direction);
        OrthonormalBase uvw = new OrthonormalBase();
        uvw.buildFromW(direction);
        return uvw.local(randomToSphere(radius, distanceSquared));
    }
    private Vector randomToSphere(double radius, double distanceSquared) {
        double r1 = Math.random();
        double r2 = Math.random();
        double z = 1 + r2 * (Math.sqrt(1-radius*radius/distanceSquared)-1);

        double fi = 2*Math.PI*r1;
        double x = Math.cos(fi)*Math.sqrt(1-z*z);
        double y = Math.sin(fi)*Math.sqrt(1-z*z);

        return new Vector(x,y,z);
    }
}
