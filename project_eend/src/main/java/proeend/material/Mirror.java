package proeend.material;

import proeend.misc.HitRecord;
import proeend.math.Ray;
import proeend.math.Vector;

public class Mirror extends Material{
    private Vector albedo;
    private double fuzz;
    public Mirror(Vector color, double fuzz) {
        albedo = color;
        this.fuzz = Math.min(fuzz, 1.0);}

    /**
     *
     * @param rayIn
     * de inkomende Ray
     * @param rec
     * de HitRecord die de informatie van de voorgaande bounces onthoud
     * @param attenuation
     * hoe de kleur wordt vervormd door de albedo van het materiaal, moet worden gebruikt door de roepende functie
     * @param scattered
     * de uitgaande Ray, moet worden gebruikt door de roepende functie
     * @return
     * of de reflectie in dezelfde globale richting gaat als de normaal, dus niet terug het object in
     */
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, Vector attenuation, Ray scattered) {
        Vector reflected = Vector.reflect(Vector.unitVector(rayIn.direction()),rec.normal);
        Vector direction = Vector.add(reflected, Vector.scale(fuzz, Vector.randomOnUnitSphere()));
        scattered.origin = rec.p;
        scattered.direction = direction;
        attenuation.copy(albedo);
        //wat er eerst stond
        //Global.scattered = new Ray(rec.p,
        //        Vector.add(reflected, Vector.scale(fuzz, Vector.randomUnitVec())));
        //Global.attenuation = albedo;
        return (Vector.dot(scattered.direction(), rec.normal) > 0);
    }
}
