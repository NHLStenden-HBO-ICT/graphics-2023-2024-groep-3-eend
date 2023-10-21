package proeend.material;

import proeend.records.ScatterRecord;
import proeend.material.pdf.CosPDF;
import proeend.material.texture.SolidColor;
import proeend.material.texture.Texture;
import proeend.records.HitRecord;
import proeend.math.Ray;
import proeend.math.Vector;

/**
 * Maakt het Lambertian materiaal aan.
 */
public class Lambertian extends Material{
    private Texture albedo; // hoeveelheid lichtreflectie

    /**
     * Maakt een Lambertian materiaal aan.
     * @param albedo Hoeveelheid weerkaatsing.
     */
    public Lambertian(Vector albedo) {
        this.albedo = new SolidColor(albedo); {
        }
    }

    /**
     * Stelt de textuur van het materiaal in.
     * @param texture De textuur.
     */
    public Lambertian(Texture texture) {
        this.albedo = texture;
    }

    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, ScatterRecord scatterRecord) {

        scatterRecord.attenuation = albedo.value(rec.u, rec.v, rec.p);
        scatterRecord.pdf = new CosPDF(rec.normal);
        scatterRecord.skipPDF = false;


        /*
        OrthonormalBase uvw = new OrthonormalBase();
        uvw.buildFromW(rec.normal);
        Vector scatterDirection = uvw.local(Utility.randomCosineDirection());
        scattered.origin = rec.p;
        scattered.direction = Vector.unitVector(scatterDirection);
        attenuation.copy(albedo.value(rec.u, rec.v,rec.p));
        rec.pdf = Vector.dot(uvw.w(), scattered.direction())/Math.PI;
         */

        /* oude scatter zonder pdf
        Vector scatterDirection = Vector.add(rec.normal, Vector.randomUnitVec());
        scattered.origin = rec.p;
    public boolean scatter(Ray rayIn, HitRecord rec, Vector attenuation, Ray scattered) {
        Vector scatterDirection = Vector.add(rec.getNormal(), Vector.randomUnitVec());
        scattered.origin = rec.getP();
        scattered.direction = scatterDirection;
        attenuation.copy(albedo.value(rec.getU(), rec.getV(),rec.getP()));
        //wat er eerst stond
        //Global.scattered = new Ray(rec.p, scatterDirection);
        //Global.attenuation = albedo;
        attenuation.copy(albedo.value(rec.u, rec.v,rec.p));
         */

        return true;
    }
    @Override
    public double scatteringPDF (Ray rayIn, HitRecord rec, Ray scattered) {
        var cosTheta = Vector.dot(rec.normal, scattered.getDirection().toUnitVector());
        return cosTheta < 0 ? 0 : cosTheta/Math.PI;
    }
}
