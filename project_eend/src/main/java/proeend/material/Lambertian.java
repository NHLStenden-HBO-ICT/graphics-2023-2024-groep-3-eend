package proeend.material;

import misc.HitRecord;
import proeend.math.Ray;
import proeend.math.Vector;

public class Lambertian extends Material{
    private final Vector albedo; // ability of light reflection
    public Lambertian(Vector albedo) {
        this.albedo = albedo;
    }

    /**
     * Lambertiaanse reflectie, gaat in ongeveer de richting van de perfecte reflectie
     * @param rayIn
     * de inkomende Ray
     * @param rec
     * de HitRecord die de informatie van de voorgaande bounces onthoud
     * @param attenuation
     * hoe de kleur wordt vervormd door de albedo van het materiaal, moet worden doorgegeven aan de roepende functie
     * @param scattered
     * de uitgaande Ray, moet worden doorgegeven aan de roepende functie
     * @return
     * altijd true
     */
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
