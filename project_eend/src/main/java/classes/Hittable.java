package classes;

public abstract class Hittable {
    abstract boolean hit(Ray ray, double tMin, double tMax, HitRecord rec);
}
