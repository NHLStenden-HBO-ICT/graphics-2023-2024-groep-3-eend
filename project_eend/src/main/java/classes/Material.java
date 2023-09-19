package classes;

public abstract class Material {
    public abstract boolean scatter(Ray rayIn, HitRecord rec, Vec attenuation, Ray scattered);
}
