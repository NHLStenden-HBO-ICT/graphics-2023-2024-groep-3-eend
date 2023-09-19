package classes;

public class Lambertian extends Material{
    private Vector albedo;
    public Lambertian(Vector albedo) {
        this.albedo = albedo;
    }
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, Vector attenuation, Ray scattered) {
        Vector scatterDirection = Vector.add(rec.normal, Vector.randomUnitVec());
        Global.scattered = new Ray(rec.p, scatterDirection);
        Global.attenuation = albedo;
        return true;
    }
}
