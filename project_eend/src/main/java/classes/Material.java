package classes;

public abstract class Material {
    public abstract boolean scatter(Ray rayIn, HitRecord rec, Vector attenuation, Ray scattered);
}
