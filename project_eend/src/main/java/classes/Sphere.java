package classes;

public class Sphere extends Hittable{

    private double radius;
    private Vec center;
    private Material mat;
    public Sphere(Vec center, double radius, Material mat) {
        this.center = center;
        this. radius = radius;
        this.mat = mat;
    }
    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {

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
        if (!rayT.surrounds(root)) {
            root = ((-halfb - sqrtD) / a);
            if (!rayT.surrounds(root)) {
                return false;
            }
        }
        rec.t = root;
        rec.p = ray.at(rec.t);
        rec.mat = mat;
        Vec outwardNormal = Vec.scale((1.0 / radius), Vec.add(rec.p, Vec.inverse(center)));
        rec.setFaceNormal(ray, outwardNormal);
        return true;
    }
}
