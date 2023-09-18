package classes;

public class Sphere extends Hittable{

    private double radius;
    private Vec center;
    public Sphere(Vec center, double radius) {
        this.center = center;
        this. radius = radius;
    }
    @Override
    boolean hit(Ray ray, double tMin, double tMax, HitRecord rec) {

        Vec OC = Vec.add(ray.origin(), Vec.inverse(center));
        double a = Vec.lengthSquared(ray.direction());
        double halfb = Vec.dot(OC, ray.direction());
        double c = Vec.lengthSquared(OC) - radius * radius;
        double D = halfb * halfb - a * c;
        if (D < 0) {
            return false;
        }

        double sqrtD = Math.sqrt(D);
        double root = ((-halfb - sqrtD) / a);
        if (root <= tMin || root >= tMax) {
            root = ((-halfb - sqrtD) / a);
            if (root <= tMin || root >= tMax) {
                return false;
            }
        }
        rec.t = root;
        rec.p = ray.at(rec.t);
        Vec outwardNormal = Vec.scale((1.0 / radius), Vec.add(rec.p, Vec.inverse(center)));
        rec.setFaceNormal(ray, outwardNormal);
        return true;
    }
}
