package classes;

public class Mirror extends Material{
    private Vec albedo;
    private double fuzz;
    public Mirror(Vec color, double fuzz) {
        albedo = color;
        this.fuzz = Math.min(fuzz, 1.0);}
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, Vec attenuation, Ray scattered) {
        Vec reflected = Vec.reflect(Vec.unitVector(rayIn.direction()),rec.normal);
        Global.scattered = new Ray(rec.p,
                Vec.add(reflected,Vec.scale(fuzz,Vec.randomUnitVec())));
        Global.attenuation = albedo;
        return (Vec.dot(Global.scattered.direction(), rec.normal) > 0);
    }
}
