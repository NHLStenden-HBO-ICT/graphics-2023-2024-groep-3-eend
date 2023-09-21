package classes;

public class Lambertian extends Material{
    private final Vector albedo; // ability of light reflection 
    public Lambertian(Vector albedo) {
        this.albedo = albedo;
    }
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, Vector attenuation, Ray scattered) {
        Vector scatterDirection = Vector.add(rec.normal, Vector.randomUnitVec());
        scattered.origin = rec.p;
        scattered.direction = scatterDirection;
        attenuation.copy(albedo);
        //wat er eerst stond
        //Global.scattered = new Ray(rec.p, scatterDirection);
        //Global.attenuation = albedo;
        return true;
    }
}
