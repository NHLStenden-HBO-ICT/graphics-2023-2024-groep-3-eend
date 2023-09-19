package classes;

public abstract class Hittable {
    public abstract boolean hit(Ray ray, Interval rayT, HitRecord rec);
}
