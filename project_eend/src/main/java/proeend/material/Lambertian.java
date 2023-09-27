package proeend.material;

import proeend.Main;
import proeend.material.texture.SolidColor;
import proeend.material.texture.Texture;
import proeend.misc.HitRecord;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.misc.OrthonormalBase;
import proeend.misc.Utility;

public class Lambertian extends Material{
    private Texture albedo; // ability of light reflection
    public Lambertian(Vector albedo) {
        this.albedo = new SolidColor(albedo); {
        }
    }
    public Lambertian(Texture texture) {
        this.albedo = texture;
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

        OrthonormalBase uvw = new OrthonormalBase();
        uvw.buildFromW(rec.normal);
        Vector scatterDirection = uvw.local(Utility.randomCosineDirection());
        scattered.origin = rec.p;
        scattered.direction = Vector.unitVector(scatterDirection);
        attenuation.copy(albedo.value(rec.u, rec.v,rec.p));


        /* oude scatter zonder pdf
        Vector scatterDirection = Vector.add(rec.normal, Vector.randomUnitVec());
        scattered.origin = rec.p;
        scattered.direction = scatterDirection;
        attenuation.copy(albedo.value(rec.u, rec.v,rec.p));
         */

        return true;
    }
    @Override
    public double scatteringPDF (Ray rayIn, HitRecord rec, Ray scattered) {
        var cosTheta = Vector.dot(rec.normal, Vector.unitVector(scattered.direction()));
        return cosTheta < 0 ? 0 : cosTheta/Math.PI;
    }
}
