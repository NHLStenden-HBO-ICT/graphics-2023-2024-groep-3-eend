package classes;

public class Lambertian extends Material{
    private Vec albedo;
    public Lambertian(Vec albedo) {
        this.albedo = albedo;
    }
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, Vec attenuation, Ray scattered) {
        Vec scatterDirection = Vec.add(rec.normal, Vec.randomUnitVec());
        Global.scattered = new Ray(rec.p, scatterDirection);
        Global.attenuation = albedo;
        return true;
    }
}
